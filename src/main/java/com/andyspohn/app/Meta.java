package com.andyspohn.app;

import org.springframework.core.env.Environment;

/*
 *  A class to hold the values we want to display from the application.properties file
 */
public class Meta {

    public final String name;
    public final String version;
    public final String hash;
    public final String timestamp;

    Meta(Environment env) {
        name = env.getProperty("project.name");
        version = env.getProperty("project.version");
        hash = env.getProperty("project.scm.revision");
        timestamp = env.getProperty("build.timestamp");
    }

}
