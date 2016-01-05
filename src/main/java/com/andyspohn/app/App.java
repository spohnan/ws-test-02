package com.andyspohn.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
@EnableAutoConfiguration
public class App {

    private Properties props;

    // Given an environment variable name return the value
    @RequestMapping(value={"/env/{name}"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry getValue(@PathVariable(value="name") String key) {
        return new AbstractMap.SimpleEntry<String, String>(key, System.getenv(key));
    }

    // Return a 200/OK by default but uncomment the ResponseStatus to trigger an error response
    // @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oh Snap!")
    @RequestMapping(value={"/health"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry health() {
        return new AbstractMap.SimpleEntry<String, String>("health", "OK");
    }

    // Return a 500 error
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oh Snap!")
    @RequestMapping(value={"/badhealth"}, method=RequestMethod.GET)
    public void badHealth() { }

    // Return the hostname, useful for demonstration of clustered deployments
    @RequestMapping(value={"/host"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry host() {
        String hostname = "Unknown Host";
        try { hostname = InetAddress.getLocalHost().getHostName(); } catch (UnknownHostException e) { /* use default */ }
        return new AbstractMap.SimpleEntry<String, String>("host", hostname);
    }

    // Simulate work using a sleep timeout
    @RequestMapping(value={"/sleep/{millis}"}, method=RequestMethod.GET)
    public @ResponseBody AbstractMap.SimpleEntry sleep(@PathVariable(value="millis") int num) {
        try { Thread.sleep(num); } catch (InterruptedException e) { /* yep */ }
        return new AbstractMap.SimpleEntry<String, String>("sleep", String.valueOf(num));
    }

    // Return the version information we included during the build
    @RequestMapping(value={"/", "/version"}, method=RequestMethod.GET)
    public @ResponseBody Map version() {
        if (props == null) { loadProps(); }
        return new HashMap<String, String>() {{
            put("name", props.getProperty("project.name"));
            put("version", props.getProperty("project.version"));
            put("branch", props.getProperty("project.branch"));
            put("commit", props.getProperty("project.scm.revision"));
            put("timestamp", props.getProperty("build.timestamp"));
        }};
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    private synchronized void loadProps() {
        if (props == null) {
            try {
                props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
            } catch (IOException e) { /* yep */ }
        }
    }
}
