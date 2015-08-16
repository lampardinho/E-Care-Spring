package com.tsystems.javaschool.ecare.controllers;

import com.tsystems.javaschool.ecare.entities.*;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.OptionService;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kolia on 11.07.2015.
 */
@Controller
@RequestMapping(value = "/admin_lobby")
public class AdminLobbyController
{
    @Autowired
    UserService userService;
    
    @Autowired
    ContractService contractService;

    @Autowired
    TariffService tariffService;

    @Autowired
    OptionService optionService;



    @RequestMapping(value = "/lock_user", method = RequestMethod.GET)
    protected String lockUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        try
        {
            Set<User> users = (Set<User>) session.getAttribute("users");
            Set<User> lockedUsers = (Set<User>) session.getAttribute("lockedUsers");
            User admin = (User) session.getAttribute("user");
            for (User user : users)
            {
                if (user.getEmail().equals(email))
                {
                    for (Contract contract : contractService.getUserContracts(user))
                    {
                        contract.getLockedByUsers().add(admin);
                        contractService.saveOrUpdateContract(contract);
                    }
                    lockedUsers.add(user);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/add_user", method = RequestMethod.GET)
    protected String addUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String passportData = request.getParameter("passportData");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String isAdmin = request.getParameter("isAdmin");

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;
        try
        {
            date = format.parse(birthDate);

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        String role;
        if (isAdmin.equals("on")) role = "ROLE_ADMIN";
        else role = "ROLE_USER";

        User newUser = new User(firstName, lastName, date, passportData, address,
                email, password, new Role(role));

        try
        {
            userService.saveOrUpdateClient(newUser);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Set<User> users = null;
        try
        {
            users = userService.getAllClients();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        session.setAttribute("users", users);

        return "admin_lobby";
    }



    @RequestMapping(value = "/add_contract", method = RequestMethod.GET)
    protected String addContract(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String owner = request.getParameter("owner");
        String phoneNumber = request.getParameter("phoneNumber");
        String balance = request.getParameter("balance");
        String tariffName = request.getParameter("tariff");

        Set<User> users = null;
        try
        {
            users = userService.getAllClients();

            User user = null;
            for (User u : users)
            {
                if ((u.getName() + " " + u.getSurname()).equals(owner))
                {
                    user = u;
                }
            }

            Set<Tariff> tariffs = tariffService.getAllTariffs();


            Tariff tariff = null;
            for (Tariff t : tariffs)
            {
                if (t.getName().equals(tariffName))
                {
                    tariff = t;
                }
            }

            Contract contract = new Contract(user, tariff, Integer.parseInt(phoneNumber), Integer.parseInt(balance));

            contractService.saveOrUpdateContract(contract);

            Set<Contract> contracts = contractService.getAllContracts();
            session.setAttribute("contracts", contracts);

            return "admin_lobby";

        } catch (Exception e)
        {
            e.printStackTrace();
            return "admin_lobby";
        }
    }



    @RequestMapping(value = "/find_user", method = RequestMethod.GET)
    protected String findUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");
        Set<Contract> contracts = (Set<Contract>) session.getAttribute("contracts");


        for (Contract contract : contracts)
        {
            if (contract.getPhoneNumber() == Integer.parseInt(phoneNumber))
            {
                session.setAttribute("foundUser", contract.getUser());
            }
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/select_tariff", method = RequestMethod.GET)
    protected String selectTariff(HttpServletRequest request)
    {
        String tariffName = request.getParameter("tariff");

        Set<Tariff> tariffs = null;
        try
        {
            tariffs = tariffService.getAllTariffs();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "admin_lobby";
    }


    @RequestMapping(value = "/contract_edit_options", method = RequestMethod.GET)
    protected String getAvailableOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");

        Contract contract = contractService.getContractByPhoneNumber(Integer.parseInt(phoneNumber));


        Set<Option> disabledOptions = new HashSet<>();
        Set<Option> selectedOptions = contract.getSelectedOptions();
        Set<Option> availableOptions = contract.getTariff().getAvailableOptions();
        Set<Option> cantDisableOptions = new HashSet<>();

        for (Option option : selectedOptions)
        {
            Collection<Option> lockedOptions = option.getLockedOptions();
            for (Option lockedOption : lockedOptions)
            {
                if (disabledOptions.contains(lockedOption)) continue;
                disabledOptions.add(lockedOption);
            }
            cantDisableOptions.addAll(option.getNeededOptions());
        }

        for (Option option : availableOptions)
        {
            for (Option neededOption: option.getNeededOptions())
            {
                if (!selectedOptions.contains(neededOption))
                    disabledOptions.add(option);
            }
        }

        session.setAttribute("availableOptions", availableOptions);
        session.setAttribute("contract_cantDisableOptions", cantDisableOptions);
        session.setAttribute("contract_selectedOptions", selectedOptions);
        session.setAttribute("contract_disabledOptions", disabledOptions);

        return "admin_lobby";
    }


    @RequestMapping(value = "/unlock_user", method = RequestMethod.GET)
    protected String unlockUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        try
        {
            Set<User> users = (Set<User>) session.getAttribute("users");
            Set<User> lockedUsers = (Set<User>) session.getAttribute("lockedUsers");
            for (User user : users)
            {
                if (user.getEmail().equals(email))
                {
                    for (Contract contract : contractService.getUserContracts(user))
                    {
                        contract.getLockedByUsers().clear();
                        contractService.saveOrUpdateContract(contract);
                    }
                    lockedUsers.remove(user);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }


    @RequestMapping(value = "/sel_option", method = RequestMethod.GET)
    protected String selectOption(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");
        String selectedOption = request.getParameter("optionName");

        Contract contract = contractService.getContractByPhoneNumber(Integer.parseInt(phoneNumber));

        Option changedOption = optionService.getOptionByName(selectedOption);

        Set<Option> disabledOptions = new HashSet<>();
        Set<Option> selectedOptions = (Set<Option>) session.getAttribute("contract_selectedOptions");
        Set<Option> availableOptions = contract.getTariff().getAvailableOptions();
        Set<Option> cantDisableOptions = new HashSet<>();

        if (selectedOptions.contains(changedOption))
        {
            selectedOptions.remove(changedOption);
        }
        else
        {
            selectedOptions.add(changedOption);
        }


        for (Option option : selectedOptions)
        {
            Collection<Option> lockedOptions = option.getLockedOptions();
            for (Option lockedOption : lockedOptions)
            {
                if (disabledOptions.contains(lockedOption)) continue;
                disabledOptions.add(lockedOption);
            }
            cantDisableOptions.addAll(option.getNeededOptions());
        }

        for (Option option : availableOptions)
        {
            for (Option neededOption: option.getNeededOptions())
            {
                if (!selectedOptions.contains(neededOption))
                    disabledOptions.add(option);
            }
        }

        session.setAttribute("availableOptions", availableOptions);
        session.setAttribute("contract_cantDisableOptions", cantDisableOptions);
        session.setAttribute("contract_selectedOptions", selectedOptions);
        session.setAttribute("contract_disabledOptions", disabledOptions);

        return "admin_lobby";
    }


    @RequestMapping(value = "/save_sel_options", method = RequestMethod.GET)
    protected String saveSelectedOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");

        Contract contract = contractService.getContractByPhoneNumber(Integer.parseInt(phoneNumber));
        Set<Option> selectedOptions = (Set<Option>) session.getAttribute("contract_selectedOptions");
        contract.setSelectedOptions(selectedOptions);
        contractService.saveOrUpdateContract(contract);

        Set<Contract> contracts = contractService.getAllContracts();
        session.setAttribute("contracts", contracts);

        return "admin_lobby";
    }


    @RequestMapping(value = "/change_tariff", method = RequestMethod.GET)
    protected String changeTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariff");
        String phoneNumber = request.getParameter("phoneNumber");

        Set<Tariff> tariffs = (Set<Tariff>) session.getAttribute("tariffs");
        Tariff newTariff = null;
        for (Tariff tariff : tariffs)
        {
            if (tariff.getName().equals(tariffName))
            {
                newTariff = tariff;
            }
        }
        try
        {
            Set<Contract> contracts = (Set<Contract>) session.getAttribute("contracts");
            for (Contract contract : contracts)
            {
                if (contract.getPhoneNumber() == Integer.parseInt(phoneNumber))
                {
                    contract.setTariff(newTariff);
                    contract.getSelectedOptions().clear();
                    contractService.saveOrUpdateContract(contract);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }


    @RequestMapping(value = "/add_tariff", method = RequestMethod.GET)
    protected String addTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        String tariffPrice = request.getParameter("tariffPrice");
        String[] optionNames = request.getParameterValues("options[]");
        Set<Option> options = (Set<Option>) session.getAttribute("options");
        Set<Tariff> tariffs = (Set<Tariff>) session.getAttribute("tariffs");

        Set<Option> tariffOptions = new HashSet<>();
        for (String optionName : optionNames)
        {
            for (Option option : options)
            {
                if (option.getName().equals(optionName))
                    tariffOptions.add(option);
            }

        }
        Tariff newTariff = new Tariff(tariffName, Integer.parseInt(tariffPrice), tariffOptions);
        try
        {
            tariffService.saveOrUpdateTariff(newTariff);
            tariffs.add(newTariff);

            tariffs = tariffService.getAllTariffs();
            session.setAttribute("tariffs", tariffs);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }


    @RequestMapping(value = "/edit_tariff", method = RequestMethod.GET)
    protected String editTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        String[] optionNames = request.getParameterValues("options[]");
        Set<Option> options = (Set<Option>) session.getAttribute("options");
        Set<Tariff> tariffs = (Set<Tariff>) session.getAttribute("tariffs");

        Set<Option> tariffOptions = new HashSet<>();
        for (String optionName : optionNames)
        {
            for (Option option : options)
            {
                if (option.getName().equals(optionName))
                    tariffOptions.add(option);
            }

        }

        try
        {
            for (Tariff tariff : tariffs)
            {
                if (tariff.getName().equals(tariffName))
                {
                    tariff.setAvailableOptions(tariffOptions);
                    tariffService.saveOrUpdateTariff(tariff);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/delete_tariff", method = RequestMethod.GET)
    protected String deleteTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        Set<Tariff> tariffs = (Set<Tariff>) session.getAttribute("tariffs");

        try
        {
            Tariff removedTariff = null;
            for (Tariff tariff : tariffs)
            {
                if (tariff.getName().equals(tariffName))
                {
                    removedTariff = tariff;
                    break;
                }
            }
            tariffService.deleteTariff(removedTariff.getTariffId());

            tariffs = tariffService.getAllTariffs();
            session.setAttribute("tariffs", tariffs);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/edit_locked_options", method = RequestMethod.GET)
    protected String editLockedOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String optionName = request.getParameter("optionName");
        Set<Option> options = (Set<Option>) session.getAttribute("options");

        for (Option option : options)
        {
            if (option.getName().equals(optionName))
            {
                Set<Option> disabledOptions = option.getNeededOptions();

                session.setAttribute("option_disabledOptions", disabledOptions);
                session.setAttribute("option_editOption", option);
                break;
            }
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/save_edit_locked_options", method = RequestMethod.GET)
    protected String saveEditLockedOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String optionName = request.getParameter("optionName");
        String[] optionNames = request.getParameterValues("options[]");
        Set<Option> options = (Set<Option>) session.getAttribute("options");

        Set<Option> lockedOptions = new HashSet<>();

        if (optionNames != null)
        {
            for (String lockedName : optionNames)
            {
                for (Option option : options)
                {
                    if (option.getName().equals(lockedName))
                        lockedOptions.add(option);
                }

            }
        }

        for (Option option : options)
        {
            if (option.getName().equals(optionName))
            {
                option.setLockedOptions(lockedOptions);
                optionService.saveOrUpdateOption(option);
            }
        }

        return "admin_lobby";
    }


    @RequestMapping(value = "/edit_needed_options", method = RequestMethod.GET)
    protected String editNeededOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String optionName = request.getParameter("optionName");
        Set<Option> options = (Set<Option>) session.getAttribute("options");

        for (Option option : options)
        {
            if (option.getName().equals(optionName))
            {
                Set<Option> disabledOptions = option.getLockedOptions();

                session.setAttribute("option_disabledOptions", disabledOptions);
                session.setAttribute("option_editOption", option);
                break;
            }
        }

        return "admin_lobby";
    }



    @RequestMapping(value = "/save_edit_needed_options", method = RequestMethod.GET)
    protected String saveEditNeededOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String optionName = request.getParameter("optionName");
        String[] optionNames = request.getParameterValues("options[]");
        Set<Option> options = (Set<Option>) session.getAttribute("options");

        Set<Option> neededOptions = new HashSet<>();

        if (optionNames != null)
        {
            for (String neededName : optionNames)
            {
                for (Option option : options)
                {
                    if (option.getName().equals(neededName))
                        neededOptions.add(option);
                }

            }
        }

        for (Option option : options)
        {
            if (option.getName().equals(optionName))
            {
                option.setNeededOptions(neededOptions);
                optionService.saveOrUpdateOption(option);
            }
        }

        return "admin_lobby";
    }
}
