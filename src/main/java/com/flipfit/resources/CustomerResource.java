package com.flipfit.resources;

import com.flipfit.entity.Booking;
import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;
import com.flipfit.service.CustomerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Path("/gyms")
    public List<GymCenter> getAllGymCenters() {
        return customerService.getAllGymCenters();
    }

    @GET
    @Path("gym/{centerId}/slots")
    public List<Slot> getAvailableSlots(@PathParam("centerId") int centerId) {
        return customerService.getAvailableSlots(centerId);
    }

    @GET
    @Path("/bookings/{customerId}")
    public List<Booking> getMyBookings(@PathParam("customerId") int customerId) {
        return customerService.getMyBookings(customerId);
    }

    @POST
    @Path("/book")
    public Response bookSlot(@QueryParam("customerId") int customerId, @QueryParam("slotId") int slotId) {

        boolean isBooked = customerService.bookSlot(customerId, slotId);

        if(isBooked) {
            return Response.status(Response.Status.CREATED)
                    .entity("Booking confirmed successfully")
                    .build();
        }
        else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Failed to book slot. Slot may not be available.")
                    .build();
        }
    }

    @PUT
    @Path("/cancel/{bookingId}")
    public Response cancelBooking(@PathParam("bookingId") int bookingId) {
        boolean isCancelled = customerService.cancelBooking(bookingId);

        if(isCancelled) {
            return Response.ok("Booking cancelled successfully").build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to cancel booking. Please try again later.")
                    .build();
        }
    }
}
