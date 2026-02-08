package com.flipfit;

import com.flipfit.resources.HelloWorldResourse;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FlipFitApplication extends Application<FlipFitConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FlipFitApplication().run(args);
    }

    @Override
    public String getName() {
        return "FlipFit";
    }

    @Override
    public void initialize(final Bootstrap<FlipFitConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final FlipFitConfiguration configuration, final Environment environment) {
        environment.jersey().register(new HelloWorldResourse());
    }

}
