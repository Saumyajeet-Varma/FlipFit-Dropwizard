package com.flipfit;

import com.flipfit.dao.*;
import com.flipfit.resources.AdminResource;
import com.flipfit.resources.CustomerResource;
import com.flipfit.resources.OwnerResource;
import com.flipfit.service.*;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        AdminDAO adminDAO = new AdminDAOImpl(jdbi);
        BookingDAO bookingDAO = new BookingDAOImpl(jdbi);
        CustomerDAO customerDAO = new CustomerDAOImpl(jdbi);
        GymCenterDAO gymCenterDAO = new GymCenterDAOImpl(jdbi);
        OwnerDAO ownerDAO = new OwnerDAOImpl(jdbi);
        SlotDAO slotDAO = new SlotDAOImpl(jdbi);

        AdminService adminService = new AdminServiceImpl(adminDAO);
        CustomerService customerService = new CustomerServiceImpl(customerDAO, gymCenterDAO, slotDAO, bookingDAO);
        OwnerService ownerService = new OwnerServiceImpl(ownerDAO, slotDAO, gymCenterDAO);

        environment.jersey().register(new AdminResource(adminService));
        environment.jersey().register(new CustomerResource(customerService));
        environment.jersey().register(new OwnerResource(ownerService));
    }

}
