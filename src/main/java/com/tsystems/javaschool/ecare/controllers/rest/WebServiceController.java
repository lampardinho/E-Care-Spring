package com.tsystems.javaschool.ecare.controllers.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by Kolia on 30.07.2015.
 */
@RestController
public class WebServiceController
{
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return name;
    }
}
