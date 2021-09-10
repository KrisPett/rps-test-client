package com.example.rpstestclient;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpsclient")
@AllArgsConstructor
public class TestRpsClientController {

    TestRpsClientService testRpsClientService;

    @GetMapping()
    public void startTest(){
        testRpsClientService.startTest();
    }
}
