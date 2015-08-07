package com.tsystems.javaschool.ecare.controllers.rest;


import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kolia on 30.07.2015.
 */
@RestController
@RequestMapping("/rest")
public class WebServiceController
{
    @Autowired
    TariffService tariffService;

    @Autowired
    UserService userService;

    @RequestMapping("/tariffs")
    public List<Tariff> getTariffs() {

        return tariffService.getAllTariffs();
    }

    @RequestMapping("/users")
    public List<User> getUsers(@RequestParam(value="tariffName") String tariffName)
    {
        List<User> allUsers = userService.getAllClients();

        return allUsers;
    }
}
