package presentation.controller;

import business.exceptions.InvalidJsonInputException;
import business.exceptions.WrongRoomIdException;
import org.glassfish.jersey.process.internal.RequestScoped;
import service.RetrieveObjectService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/")
public class MainController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path("/roomsMap")
    public Response roomsMap(String json) {
        try {
            return Response.ok(RetrieveObjectService.retrieve(json)).build();
        } catch (InvalidJsonInputException | WrongRoomIdException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

    }


}
