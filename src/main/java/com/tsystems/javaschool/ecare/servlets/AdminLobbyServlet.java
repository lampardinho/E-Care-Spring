package com.tsystems.javaschool.ecare.servlets;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.OptionService;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kolia on 11.07.2015.
 */
@WebServlet(name = "AdminLobbyServlet")
public class AdminLobbyServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            HttpSession session = request.getSession();

            List<User> users = UserService.getInstance().getAllClients();
            session.setAttribute("users", users);

            List<Contract> contracts = ContractService.getInstance().getAllContracts();
            session.setAttribute("contracts", contracts);

            List<Tariff> tariffs = TariffService.getInstance().getAllTariffs();
            session.setAttribute("tariffs", tariffs);

            List<Option> options = OptionService.getInstance().getAllOptions();
            session.setAttribute("options", options);

            List<User> lockedUsers = new LinkedList<>();
            for (User user : users)
            {
                boolean isUserLocked = true;
                List<Contract> userContracts = ContractService.getInstance().getUserContracts(user);
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

        request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        System.out.println(action);

        HttpSession session = request.getSession();

        switch (action)
        {
            case "add_user":
            {
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
                    UserService.getInstance().saveOrUpdateClient(newUser);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                List<User> users = null;
                try
                {
                    users = UserService.getInstance().getAllClients();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                session.setAttribute("users", users);

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "add_contract":
            {
                String owner = request.getParameter("owner");
                String phoneNumber = request.getParameter("phoneNumber");
                String balance = request.getParameter("balance");
                String tariffName = request.getParameter("tariff");

                List<User> users = null;
                try
                {
                    users = UserService.getInstance().getAllClients();

                    User user = null;
                    for (User u : users)
                    {
                        if ((u.getName() + " " + u.getSurname()).equals(owner))
                        {
                            user = u;
                        }
                    }

                    List<Tariff> tariffs = TariffService.getInstance().getAllTariffs();


                    Tariff tariff = null;
                    for (Tariff t : tariffs)
                    {
                        if (t.getName().equals(tariffName))
                        {
                            tariff = t;
                        }
                    }

                    Contract contract = new Contract(user, tariff, Integer.parseInt(phoneNumber), Integer.parseInt(balance));

                    ContractService.getInstance().saveOrUpdateContract(contract);

                    List<Contract> contracts = ContractService.getInstance().getAllContracts();
                    session.setAttribute("contracts", contracts);

                    request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            }
            case "find_user":
            {
                String phoneNumber = request.getParameter("phoneNumber");
                List<Contract> contracts = (List<Contract>) session.getAttribute("contracts");


                for (Contract contract : contracts)
                {
                    if (contract.getPhoneNumber() == Integer.parseInt(phoneNumber))
                    {
                        session.setAttribute("foundUser", contract.getUser());
                    }
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "select_tariff":
            {
                String tariffName = request.getParameter("tariff");

                List<Tariff> tariffs = null;
                try
                {
                    tariffs = TariffService.getInstance().getAllTariffs();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "get_avail_options":
            {
                String tariffName = request.getParameter("tariffName");

                List<Tariff> tariffs = null;
                try
                {
                    tariffs = TariffService.getInstance().getAllTariffs();
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
                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "lock_user":
            {
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
                            for (Contract contract : ContractService.getInstance().getUserContracts(user))
                            {
                                contract.getLockedByUsers().add(admin);
                                ContractService.getInstance().saveOrUpdateContract(contract);
                            }
                            lockedUsers.add(user);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "unlock_user":
            {
                String email = request.getParameter("email");
                try
                {
                    List<User> users = (List<User>) session.getAttribute("users");
                    List<User> lockedUsers = (List<User>) session.getAttribute("lockedUsers");
                    for (User user : users)
                    {
                        if (user.getEmail().equals(email))
                        {
                            for (Contract contract : ContractService.getInstance().getUserContracts(user))
                            {
                                contract.getLockedByUsers().clear();
                                ContractService.getInstance().saveOrUpdateContract(contract);
                            }
                            lockedUsers.remove(user);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "save_sel_options":
            {
                String options = request.getParameter("options");
                System.out.println(options);

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "change_tariff":
            {
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
                            ContractService.getInstance().saveOrUpdateContract(contract);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "add_tariff":
            {
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
                    TariffService.getInstance().saveOrUpdateTariff(newTariff);
                    tariffs.add(newTariff);

                    tariffs = TariffService.getInstance().getAllTariffs();
                    session.setAttribute("tariffs", tariffs);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "edit_tariff":
            {
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
                            TariffService.getInstance().saveOrUpdateTariff(tariff);
                        }
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "delete_tariff":
            {
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
                    TariffService.getInstance().deleteTariff(removedTariff.getTariffId());

                    tariffs = TariffService.getInstance().getAllTariffs();
                    session.setAttribute("tariffs", tariffs);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

                break;
            }
            case "edit_option":
            {
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
                            OptionService.getInstance().saveOrUpdateOption(option);
                        }
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                request.getRequestDispatcher("/WEB-INF/jsp/admin_lobby.jsp").include(request, response);

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
