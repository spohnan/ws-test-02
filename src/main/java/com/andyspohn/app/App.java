package com.andyspohn.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@EnableAutoConfiguration
public class App {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/health")
    @ResponseBody
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oh Snap!")
    String health() {
        return "OK";
    }


    @RequestMapping("/host")
    @ResponseBody
    String host() {
        String hostname = "Unknown Host";

        try {

            hostname = InetAddress.getLocalHost().getHostName();

        }catch (UnknownHostException e) { /* Use default value */ }

        return hostname;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
