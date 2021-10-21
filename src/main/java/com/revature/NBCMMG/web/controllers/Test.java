package com.revature.NBCMMG.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping
    public String testGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(201);
        return "Test Works!";
    }

}
