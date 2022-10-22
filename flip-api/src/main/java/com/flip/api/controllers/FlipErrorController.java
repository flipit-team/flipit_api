package com.flip.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Charles on 19/10/2022
 */
@Log4j2
@RestController
public class FlipErrorController implements ErrorController {

    @GetMapping({"/api/v1/error", "/error"})
    public String errorPage(){
        return "Error...";
    }
}
