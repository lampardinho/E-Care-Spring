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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/lobby", method = RequestMethod.GET)
    protected String login(ModelMap model, Authentication authentication)
    {
        //String name = principal.getName();

        User user = userService.findClient("1lampard@mail.ru", "qwerty");
        model.addAttribute("user", user);

        String role = null;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            role = authority.getAuthority();
        }

        if (role.equals("ROLE_ADMIN"))
        {
            initAdmin(model);
            //return new ModelAndView("admin_lobby", model);
            return "admin_lobby";
        }
        else
        {
            initClient(model);
            return "client_lobby";
        }

    }

    private void initAdmin(ModelMap model)
    {
        try
        {
            List<User> users = userService.getAllClients();
            model.addAttribute("users", users);

            List<Contract> contracts = contractService.getAllContracts();
            model.addAttribute("contracts", contracts);

            List<Tariff> tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffs", tariffs);

            List<Option> options = optionService.getAllOptions();
            model.addAttribute("options", options);

            List<User> lockedUsers = new LinkedList<>();
            for (User user : users)
            {
                boolean isUserLocked = true;
                List<Contract> userContracts = contractService.getUserContracts(user);
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
            model.addAttribute("lockedUsers", lockedUsers);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void initClient(ModelMap model)
    {
        User user = (User) model.get("user");

        try
        {
            List<Contract> contracts = contractService.getUserContracts(user);
            model.addAttribute("contracts", contracts);

            List<Tariff> tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffs", tariffs);

            Contract currentContract = contracts.get(0);
            model.addAttribute("currentContract", currentContract);

            Tariff currentTariff = currentContract.getTariff();
            model.addAttribute("currentTariff", currentTariff);


            model.addAttribute("options", currentTariff.getAvailableOptions());


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
            model.addAttribute("disabledOptions", disabledOptions);


            List<String> actionsHistory = new LinkedList<>();
            model.addAttribute("actionsHistory", actionsHistory);

            model.addAttribute("balance", currentContract.getBalance());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
