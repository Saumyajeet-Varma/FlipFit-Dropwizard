package com.flipfit.resources;

import com.flipfit.entity.GymCenter;
import com.flipfit.entity.Slot;
import com.flipfit.service.OwnerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnerResource {

    private final OwnerService ownerService;

    public OwnerResource(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @POST
    @Path("/add-gym")
    public Response addGymCenter(GymCenter gymCenter) {

        try {
            ownerService.addGymCenter(gymCenter.getOwnerId(), gymCenter.getName(), gymCenter.getLocation());
            return Response.status(Response.Status.CREATED)
                    .entity("Gym center added successfully and is pending approval")
                    .build();
        }
        catch(RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to add gym center: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("request-approval/{centerId}")
    public Response requestGymCenterApproval(@PathParam("centerId") int centerId) {
        try {
            ownerService.requestGymCenterApproval(centerId);
            return Response.ok("Gym center approval requested successfully").build();
        }
        catch(RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to request approval: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/my-gyms/{ownerId}")
    public List<GymCenter> getMyGymCenters(@PathParam("ownerId") int ownerId) {
        return ownerService.getMyGymCenters(ownerId);
    }

    @POST
    @Path("/add-slot")
    public Response addSlot(Slot slot) {
        try {
            ownerService.addSlot(slot.getCenterId(), slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getTotalSeats());
            return Response.status(Response.Status.CREATED)
                    .entity("Slot added successfully")
                    .build();
        }
        catch(RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to add slot: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete-slot/{slotId}")
    public  Response removeSlot(@PathParam("slotId") int slotId) {
        try {
            ownerService.removeSlot(slotId);
            return Response.ok("Slot deleted successfully").build();
        }
        catch(RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to delete slot: " + e.getMessage())
                    .build();
        }
    }
}
