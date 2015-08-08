package com.tsystems.javaschool.ecare.controllers;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kolia on 06.07.2015.
 */
@Controller
@RequestMapping(value = "/client_lobby")
public class ClientLobbyController
{
    @Autowired
    ContractService contractService;

    @Autowired
    TariffService tariffService;


    @RequestMapping(value = "/select_contract/{phoneNumber}", method = RequestMethod.GET)
    protected String selectContract(@PathVariable int phoneNumber, HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");
        Contract selectedContract = null;
        for (Contract contract : contracts)
        {
            if (contract.getPhoneNumber() == phoneNumber)
            {
                selectedContract = contract;
            }
        }

        session.setAttribute("currentContract", selectedContract);

        Tariff currentTariff = selectedContract.getTariff();
        session.setAttribute("currentTariff", currentTariff);

        session.setAttribute("options", currentTariff.getAvailableOptions());

        List<Option> disabledOptions = new LinkedList<>();
        for (Option option : selectedContract.getSelectedOptions())
        {
            disabledOptions.addAll(option.getLockedOptions());
        }
        session.setAttribute("disabledOptions", disabledOptions);

        session.setAttribute("balance", selectedContract.getBalance());

        return "client_lobby";


    }


    @RequestMapping(value = "/change_tariff", method = RequestMethod.GET)
    protected String changeTariff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        String tariffName = request.getParameter("tariffName");

        Contract contract = (Contract) session.getAttribute("currentContract");
        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        for (Tariff tariff : tariffs)
            if (tariff.getName().equals(tariffName))
            {
                contract.setTariff(tariff);
                contract.setBalance(contract.getBalance() - tariff.getPrice());
                session.setAttribute("currentTariff", tariff);
                contract.getSelectedOptions().clear();

                session.setAttribute("options", tariff.getAvailableOptions());
            }

        actionsHistory.add("Change tariff to " + contract.getTariff().getName());
        session.setAttribute("actionsHistory", actionsHistory);

        session.setAttribute("currentContract", contract);


        List<Option> disabledOptions = new LinkedList<>();
        session.setAttribute("disabledOptions", disabledOptions);

        session.setAttribute("balance", contract.getBalance());
        return "client_lobby";
    }


    @RequestMapping(value = "/disable_option", method = RequestMethod.GET)
    protected String disableOption(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        String optionName = request.getParameter("optionName");

        Contract contract = (Contract) session.getAttribute("currentContract");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        Set<Option> selectedOptions = contract.getSelectedOptions();

        for (Option option : contract.getSelectedOptions())
        {
            if (option.getName().equals(optionName))
            {
                selectedOptions.remove(option);
                actionsHistory.add("Disable option " + optionName);
                break;
            }
        }

        List<Option> disabledOptions = new LinkedList<>();
        for (Option option : contract.getSelectedOptions())
        {
            disabledOptions.addAll(option.getLockedOptions());
        }
        session.setAttribute("disabledOptions", disabledOptions);

        session.setAttribute("currentContract", contract);
        return "client_lobby";
    }


    @RequestMapping(value = "/add_option", method = RequestMethod.GET)
    protected String addOption(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        String optionName = request.getParameter("optionName");

        Contract contract = (Contract) session.getAttribute("currentContract");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        for (Option option : contract.getTariff().getAvailableOptions())
        {
            if (option.getName().equals(optionName))
            {
                contract.getSelectedOptions().add(option);
                contract.setBalance(contract.getBalance() - option.getConnectionPrice());
                session.setAttribute("balance", contract.getBalance());
                actionsHistory.add("Add option " + optionName);
            }
        }


        List<Option> disabledOptions = new LinkedList<>();
        for (Option option : contract.getSelectedOptions())
        {
            disabledOptions.addAll(option.getLockedOptions());
        }
        session.setAttribute("disabledOptions", disabledOptions);

        session.setAttribute("currentContract", contract);
        return "client_lobby";
    }


    @RequestMapping(value = "/block", method = RequestMethod.GET)
    protected String block(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        Contract contract = (Contract) session.getAttribute("currentContract");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        Set<User> blockers = contract.getLockedByUsers();
        User user = (User) session.getAttribute("user");
        blockers.add(user);
        contract.setLockedByUsers(blockers);

        actionsHistory.add("Block contact " + contract.getPhoneNumber());
        session.setAttribute("currentContract", contract);
        return "client_lobby";
    }


    @RequestMapping(value = "/unblock", method = RequestMethod.GET)
    protected String unblock(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        Contract contract = (Contract) session.getAttribute("currentContract");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        Set<User> blockers = contract.getLockedByUsers();
        User user = (User) session.getAttribute("user");
        blockers.remove(user);
        contract.setLockedByUsers(blockers);

        actionsHistory.add("Unblock contact " + contract.getPhoneNumber());
        session.setAttribute("isBlocked", !contract.getLockedByUsers().isEmpty());
        session.setAttribute("currentContract", contract);

        return "client_lobby";
    }

    @RequestMapping(value = "/apply_changes", method = RequestMethod.GET)
    protected String applyChanges(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");
        List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

        for (Contract contract : contracts)
        {
            contractService.saveOrUpdateContract(contract);
        }
        actionsHistory.clear();

        return "client_lobby";
    }

    @RequestMapping(value = "/discard_changes", method = RequestMethod.GET)
    protected String discardChanges(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        
        User user = (User) session.getAttribute("user");
        List<Contract> contracts = contractService.getUserContracts(user);
        session.setAttribute("contracts", contracts);

        Contract currentContract = null;
        Contract contract = (Contract) session.getAttribute("currentContract");
        for (Contract c : contracts)
        {
            if (c.getPhoneNumber() == contract.getPhoneNumber())
            {
                currentContract = c;
                session.setAttribute("currentContract", currentContract);
            }
        }

        Tariff currentTariff = currentContract.getTariff();
        session.setAttribute("currentTariff", currentTariff);


        session.setAttribute("options", currentTariff.getAvailableOptions());


        List<Option> disabledOptions = new LinkedList<>();
        Set<Option> selectedOptions = currentContract.getSelectedOptions();
        for (Option option : selectedOptions)
        {
            Collection<Option> lockedOptions = option.getLockedOptions();
            for (Option lockedOption : lockedOptions)
            {
                if (disabledOptions.contains(lockedOption)) continue;
                disabledOptions.add(lockedOption);
            }
        }
        session.setAttribute("disabledOptions", disabledOptions);


        List<String> actionsHistory = new LinkedList<>();
        session.setAttribute("actionsHistory", actionsHistory);

        session.setAttribute("balance", currentContract.getBalance());


        return "client_lobby";
    }

}
