package com.flipfit.resources;

import com.flipfit.entity.GymCenter;
import com.flipfit.entity.User;
import com.flipfit.service.AdminService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    private final AdminService adminService;

    public AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }

    @PUT
    @Path("gyms/approve/{centerId}")
    public Response approveGymCenter(@PathParam("centerId") int centerId) {
        try {
            adminService.approveGymCenter(centerId);
            return Response.ok("Gym center approved successfully").build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to approve gym center: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("gyms/decline/{centerId}")
    public Response declineGymCenter(@PathParam("centerId") int centerId) {
        try {
            adminService.declineGymCenter(centerId);
            return Response.ok("Gym center declined successfully").build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to decline gym center: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/gyms/pending")
    public List<GymCenter> getPendingGymCenters() {
        return adminService.getPendingGymCenters();
    }

    @GET
    @Path("/gyms/all")
    public List<GymCenter> getAllGymCenters() {
        return adminService.getAllGymCenters();
    }

    @GET
    @Path("/users/all")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GET
    @Path("/report")
    public String getReport() {
        return adminService.getReport();
    }
}
