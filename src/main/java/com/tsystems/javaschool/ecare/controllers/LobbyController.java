package com.tsystems.javaschool.ecare.controllers;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.OptionService;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import org.hibernate.metamodel.source.annotations.xml.mocker.MockHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

/**
 * Created by Kolia on 01.07.2015.
 */
@Controller
public class LobbyController
{
    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    TariffService tariffService;

    @Autowired
    OptionService optionService;

    @RequestMapping(value = "/login")
    protected String enterLobby(HttpServletRequest request)
    {
        return "login";
    }

    @RequestMapping(value = "/sign_out", method = RequestMethod.GET)
    protected String signOut(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        session.invalidate();
        return "login";
    }

    @RequestMapping(value = "/lobby", method = RequestMethod.GET)
    protected String enterLobby(HttpServletRequest request, Authentication authentication)
    {
        HttpSession session = request.getSession();
        
        String name = authentication.getName();

        User user = userService.findClient(name);
        session.setAttribute("user", user);

        String role = null;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            role = authority.getAuthority();
        }

        if (role.equals("ROLE_ADMIN"))
        {
            initAdmin(request);
            return "admin_lobby";
        }
        else
        {
            initClient(request);
            return "client_lobby";
        }

    }

    private void initAdmin(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        Set<User> users = userService.getAllClients();
        session.setAttribute("users", users);

        Set<Contract> contracts = contractService.getAllContracts();
        session.setAttribute("contracts", contracts);

        Set<Tariff> tariffs = tariffService.getAllTariffs();
        session.setAttribute("tariffs", tariffs);

        Set<Option> options = optionService.getAllOptions();
        session.setAttribute("options", options);

        Set<User> lockedUsers = new HashSet<>();
        for (User user : users)
        {
            boolean isUserLocked = true;
            Set<Contract> userContracts = contractService.getUserContracts(user);
            for (Contract contract : userContracts)
            {
                if (contract.getLockedByUsers().isEmpty())
                {
                    isUserLocked = false;
                    break;
                }
            }
            if (isUserLocked && !userContracts.isEmpty())
                lockedUsers.add(user);
        }
        session.setAttribute("lockedUsers", lockedUsers);



    }


    private void initClient(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        try
        {
            Set<Contract> contracts = contractService.getUserContracts(user);
            session.setAttribute("contracts", contracts);

            Set<Tariff> tariffs = tariffService.getAllTariffs();
            session.setAttribute("tariffs", tariffs);

            Iterator<Contract> it = contracts.iterator();
            Contract currentContract = it.next();
            session.setAttribute("currentContract", currentContract);

            Tariff currentTariff = currentContract.getTariff();
            session.setAttribute("currentTariff", currentTariff);


            session.setAttribute("options", currentTariff.getAvailableOptions());


            Set<Option> disabledOptions = new HashSet<>();
            Set<Option> cantDisableOptions = new HashSet<>();
            Set<Option> selectedOptions = currentContract.getSelectedOptions();
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

            for (Option option : currentTariff.getAvailableOptions())
            {
                for (Option neededOption: option.getNeededOptions())
                {
                    if (!selectedOptions.contains(neededOption))
                        disabledOptions.add(option);
                }
            }

            session.setAttribute("disabledOptions", disabledOptions);
            session.setAttribute("cantDisableOptions", cantDisableOptions);


            List<String> actionsHistory = new LinkedList<>();
            session.setAttribute("actionsHistory", actionsHistory);

            session.setAttribute("balance", currentContract.getBalance());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
