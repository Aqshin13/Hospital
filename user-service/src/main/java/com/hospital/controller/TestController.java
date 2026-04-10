package com.hospital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class TestController {




    @GetMapping("/doctor")
    public String doctor(@RequestHeader("user-id") String userId){
        System.out.println(userId);

        return "doctor";
    }



    @GetMapping("/patient")
    public String patient(@RequestHeader("user-id") String userId){
        System.out.println(userId);
        return "patient";
    }

}
