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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kolia on 01.07.2015.
 */
@Controller
@RequestMapping(value = "/lobby", method = RequestMethod.POST)
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

    //@RequestMapping(value = "/lobby", method = RequestMethod.POST)
    protected ModelAndView login(HttpServletRequest req)
    {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String isAdmin = req.getParameter("isAdmin");
        try
        {
            HttpSession session = req.getSession();

            User user = userService.findClient(email, password);

            session.setAttribute("user", user);


            if (user.getIsAdmin() || isAdmin != null)
            {
                initAdmin(req);
                return new ModelAndView("admin_lobby");
            } else
            {
                initClient(req);
                return new ModelAndView("client_lobby");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ModelAndView("/login.jsp");
        }

    }

    private void initAdmin(HttpServletRequest request)
    {
        try
        {
            HttpSession session = request.getSession();

            List<User> users = userService.getAllClients();
            session.setAttribute("users", users);

            List<Contract> contracts = contractService.getAllContracts();
            session.setAttribute("contracts", contracts);

            List<Tariff> tariffs = tariffService.getAllTariffs();
            session.setAttribute("tariffs", tariffs);

            List<Option> options = optionService.getAllOptions();
            session.setAttribute("options", options);

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
            session.setAttribute("lockedUsers", lockedUsers);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //return "/WEB-INF/jsp/admin_lobby.jsp";
    }


    private void initClient(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        try
        {
            List<Contract> contracts = contractService.getUserContracts(user);
            session.setAttribute("contracts", contracts);

            List<Tariff> tariffs = tariffService.getAllTariffs();
            session.setAttribute("tariffs", tariffs);

            Contract currentContract = contracts.get(0);
            session.setAttribute("currentContract", currentContract);

            Tariff currentTariff = currentContract.getTariff();
            session.setAttribute("currentTariff", currentTariff);


            session.setAttribute("options", currentTariff.getAvailableOptions());

            /*for (Tariff tariff : tariffs)
            {
                if (tariff.getName().equals(currentTariff.getName()))
                {
                    session.setAttribute("options", currentTariff.getAvailableOptions());
                }
            }*/


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
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //return "/WEB-INF/jsp/client_lobby.jsp";
    }
}
