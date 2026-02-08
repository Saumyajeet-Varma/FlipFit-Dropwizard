package com.flipfit.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldResourse {

    @GET
    public String getHelloWorld() {
        return "Hello World";
    }
}
