package com.tsystems.javaschool.ecare.controllers;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
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
            List<User> users = (List<User>) session.getAttribute("users");
            List<User> lockedUsers = (List<User>) session.getAttribute("lockedUsers");
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

        return "/WEB-INF/jsp/admin_lobby.jsp";
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

        byte byteAdmin;
        if (isAdmin.equals("on")) byteAdmin = 1;
        else byteAdmin = 0;

        User newUser = new User(firstName, lastName, date, passportData, address,
                email, password, byteAdmin);

        try
        {
            userService.saveOrUpdateClient(newUser);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        List<User> users = null;
        try
        {
            users = userService.getAllClients();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        session.setAttribute("users", users);

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }



    @RequestMapping(value = "/add_contract", method = RequestMethod.GET)
    protected String addContract(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String owner = request.getParameter("owner");
        String phoneNumber = request.getParameter("phoneNumber");
        String balance = request.getParameter("balance");
        String tariffName = request.getParameter("tariff");

        List<User> users = null;
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

            List<Tariff> tariffs = tariffService.getAllTariffs();


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

            List<Contract> contracts = contractService.getAllContracts();
            session.setAttribute("contracts", contracts);

            return "/WEB-INF/jsp/admin_lobby.jsp";

        } catch (Exception e)
        {
            e.printStackTrace();
            return "/WEB-INF/jsp/admin_lobby.jsp";
        }
    }



    @RequestMapping(value = "/find_user", method = RequestMethod.GET)
    protected String findUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String phoneNumber = request.getParameter("phoneNumber");
        List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");


        for (Contract contract : contracts)
        {
            if (contract.getPhoneNumber() == Integer.parseInt(phoneNumber))
            {
                session.setAttribute("foundUser", contract.getUser());
            }
        }

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }



    @RequestMapping(value = "/select_tariff", method = RequestMethod.GET)
    protected String selectTariff(HttpServletRequest request)
    {
        String tariffName = request.getParameter("tariff");

        List<Tariff> tariffs = null;
        try
        {
            tariffs = tariffService.getAllTariffs();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/get_avail_options", method = RequestMethod.GET)
    protected String getAvailableOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");

        List<Tariff> tariffs = null;
        try
        {
            tariffs = tariffService.getAllTariffs();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for (Tariff tariff : tariffs)
        {
            if (tariff.getName().equals(tariffName))
            {
                session.setAttribute("availableOptions", tariff.getAvailableOptions());
            }
        }
        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/unlock_user", method = RequestMethod.GET)
    protected String unlockUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        try
        {
            List<User> users = (List<User>) session.getAttribute("users");
            List<User> lockedUsers = (List<User>) session.getAttribute("lockedUsers");
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

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/save_sel_options", method = RequestMethod.GET)
    protected String saveSelectedOptions(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String options = request.getParameter("options");
        System.out.println(options);

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/change_tariff", method = RequestMethod.GET)
    protected String changeTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariff");
        String phoneNumber = request.getParameter("phoneNumber");

        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");
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
            List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");
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

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/add_tariff", method = RequestMethod.GET)
    protected String addTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        String tariffPrice = request.getParameter("tariffPrice");
        String[] optionNames = request.getParameterValues("options[]");
        List<Option> options = (List<Option>) session.getAttribute("options");
        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");

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

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/edit_tariff", method = RequestMethod.GET)
    protected String editTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        String[] optionNames = request.getParameterValues("options[]");
        List<Option> options = (List<Option>) session.getAttribute("options");
        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");

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

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }



    @RequestMapping(value = "/delete_tariff", method = RequestMethod.GET)
    protected String deleteTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String tariffName = request.getParameter("tariffName");
        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");

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

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }



    @RequestMapping(value = "/edit_option", method = RequestMethod.GET)
    protected String editOption(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String optionName = request.getParameter("optionName");
        String[] optionNames = request.getParameterValues("options[]");
        List<Option> options = (List<Option>) session.getAttribute("options");

        Set<Option> lockedOptions = new HashSet<>();
        for (String lockedName : optionNames)
        {
            for (Option option : options)
            {
                if (option.getName().equals(lockedName))
                    lockedOptions.add(option);
            }

        }

        try
        {
            for (Option option : options)
            {
                if (option.getName().equals(optionName))
                {
                    option.setLockedOptions(lockedOptions);
                    optionService.saveOrUpdateOption(option);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    @RequestMapping(value = "/sign_out", method = RequestMethod.GET)
    protected String signOut(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        session.invalidate();
        return "login.jsp";
    }
}
