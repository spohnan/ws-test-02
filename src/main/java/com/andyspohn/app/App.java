package com.andyspohn.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.AbstractMap;

@Controller
@EnableAutoConfiguration
@PropertySource("classpath:/application.properties")
public class App {

    @Autowired
    private Environment env;

    // Given an environment variable name return the value
    @RequestMapping(value={"/env/{name}"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry getValue(@PathVariable(value="name") String key) {
        return new AbstractMap.SimpleEntry<String, String>(key, env.getProperty(key));
    }

    // Return a 200/OK by default but uncomment the ResponseStatus to trigger an error response
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oh Snap!")
    @RequestMapping(value={"/health"}, method=RequestMethod.GET)
    @ResponseBody
    String health() {
        return "OK";
    }

    // Return the hostname, useful for demonstration of clustered deployments
    @RequestMapping(value={"/host"}, method=RequestMethod.GET)
    @ResponseBody
    String host() {
        String hostname = "Unknown Host";
        try { hostname = InetAddress.getLocalHost().getHostName(); } catch (UnknownHostException e) { /* use default */ }
        return hostname;
    }

    // Simulate work using a sleep timeout
    @RequestMapping(value={"/sleep/{millis}"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry sleep(@PathVariable(value="millis") int num) {
        try { Thread.sleep(num); } catch (InterruptedException e) { /* yep */ }
        return new AbstractMap.SimpleEntry<String, String>("sleep", String.valueOf(num));
    }

    // Return the version information we included during the build
    @RequestMapping(value={"/", "/version"}, method=RequestMethod.GET)
    public @ResponseBody Meta version() {
        return new Meta(env);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

}
