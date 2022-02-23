package com.flip.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Charles on 26/11/2021
 */
@Log4j2
@RestController
@RequestMapping
public class PingController {

    @GetMapping({"/"})
    public String ping(){
        return "Responding...";
    }

    @GetMapping({"/hello"})
    public String hello(){
        return "Responding with authorization...";
    }

}
