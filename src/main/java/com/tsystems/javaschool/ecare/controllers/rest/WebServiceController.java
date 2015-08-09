package com.tsystems.javaschool.ecare.controllers.rest;


import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    public Set<String> getTariffs() {

        Set<Tariff> tariffs = tariffService.getAllTariffs();
        Set<String> names = new HashSet<>();
        for (Iterator<Tariff> it = tariffs.iterator(); it.hasNext(); )
        {
            names.add(it.next().getName());
        }
        return names;
    }

    @RequestMapping("/users")
    public Set<String> getUsers(@RequestParam(value="tariff") String tariff)
    {
        Set<User> users = userService.getUsersByTariff(tariff);
        Set<String> names = new HashSet<>();
        for (Iterator<User> it = users.iterator(); it.hasNext(); )
        {
            names.add(it.next().getName());
        }
        return names;
    }
}
