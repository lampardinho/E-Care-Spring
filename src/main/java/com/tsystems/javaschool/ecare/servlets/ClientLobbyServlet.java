package com.tsystems.javaschool.ecare.servlets;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.TariffService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kolia on 06.07.2015.
 */
@WebServlet(name = "ClientLobbyServlet")
public class ClientLobbyServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        try
        {
            List<Contract> contracts = ContractService.getInstance().getUserContracts(user);
            session.setAttribute("contracts", contracts);

            List<Tariff> tariffs = TariffService.getInstance().getAllTariffs();
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

        request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        System.out.println(action);

        HttpSession session = request.getSession();


        switch (action)
        {
            case "select_contract":
            {
                int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
                try
                {
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

                    /*List<Tariff> tariffs = (List<Tariff>) session.getAttribute("tariffs");
                    for (Tariff tariff : tariffs)
                    {
                        if (tariff.getName().equals(currentTariff.getName()))
                        {
                            session.setAttribute("options", currentTariff.getAvailableOptions());
                        }
                    }*/

                    List<Option> disabledOptions = new LinkedList<>();
                    for (Option option : selectedContract.getSelectedOptions())
                    {
                        disabledOptions.addAll(option.getLockedOptions());
                    }
                    session.setAttribute("disabledOptions", disabledOptions);

                    session.setAttribute("balance", selectedContract.getBalance());

                    request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            }
            case "change_tariff":
            {
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

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);
                break;
            }
            case "disable_option":
            {
                String optionName = request.getParameter("optionName");

                Contract contract = (Contract) session.getAttribute("currentContract");
                List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

                Set<Option> selectedOptions = contract.getSelectedOptions();
                //System.out.println(selectedOptions.size());

                for (Option option : contract.getSelectedOptions())
                {
                    if (option.getName().equals(optionName))
                    {
                        //System.out.println(contract.getSelectedOptions().size());
                        selectedOptions.remove(option);
                        actionsHistory.add("Disable option " + optionName);
                        //System.out.println(contract.getSelectedOptions().size());
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

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);
                break;
            }
            case "add_option":
            {
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

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);
                break;
            }
            case "block":
            {
                Contract contract = (Contract) session.getAttribute("currentContract");
                List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

                Set<User> blockers = contract.getLockedByUsers();
                User user = (User) session.getAttribute("user");
                blockers.add(user);
                contract.setLockedByUsers(blockers);

                actionsHistory.add("Block contact " + contract.getPhoneNumber());
                session.setAttribute("currentContract", contract);

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);

                break;
            }
            case "unblock":
            {
                Contract contract = (Contract) session.getAttribute("currentContract");
                List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");

                Set<User> blockers = contract.getLockedByUsers();
                User user = (User) session.getAttribute("user");
                blockers.remove(user);
                contract.setLockedByUsers(blockers);

                actionsHistory.add("Unblock contact " + contract.getPhoneNumber());
                session.setAttribute("isBlocked", !contract.getLockedByUsers().isEmpty());
                session.setAttribute("currentContract", contract);

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);

                break;
            }
            case "apply_changes":
            {
                List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");
                List<String> actionsHistory = (List<String>) session.getAttribute("actionsHistory");
                try
                {
                    for (Contract contract : contracts)
                    {
                        ContractService.getInstance().saveOrUpdateContract(contract);
                    }
                    actionsHistory.clear();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);

                break;
            }
            case "discard_changes":
            {
                try
                {
                    User user = (User) session.getAttribute("user");
                    List<Contract> contracts = ContractService.getInstance().getUserContracts(user);
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
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/client_lobby.jsp").include(request, response);

                break;
            }
            case "sign_out":
            {
                session.invalidate();
                break;
            }
        }
    }
}
