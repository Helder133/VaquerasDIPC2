/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.calificacion;

import ipc2_vaqueras.vaquerasdipc2.dtos.calificacionVideojuego.CalificacionVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.calificacionVideojuego.CalificacionVideojuegoResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.calificacionVideojuego.CalificacionVideojuegoUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.clasificacionVideojuego.CalificacionVideojuego;
import ipc2_vaqueras.vaquerasdipc2.services.calificacion.CalificacionVideojuegoService;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Optional;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("calificacion")
public class CalificacionResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calificarVideojuego(CalificacionVideojuegoRequest calificacionVideojuegoRequest) {
        try {
            CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
            calificacionVideojuegoService.calificarVideojuego(calificacionVideojuegoRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @GET
    @Path("/usuario/{usuario_id}/videojuego/{videojuego_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCalificacionDeUnUsuarioAUnVideojuego(@PathParam("usuario_id") int usuario_id, @PathParam("videojuego_id") int videojuego_id) {
        try {
            CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
            Optional<CalificacionVideojuego> calificacionOptional = calificacionVideojuegoService.obtenerCalificacionDeUsuarioAVideojuego(usuario_id, videojuego_id);
            if (calificacionOptional.isEmpty()) {
                return Response.ok().build();
            }
            return Response.ok(new CalificacionVideojuegoResponse(calificacionOptional.get())).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarCalificacionDeUnUsuarioAUnVideojuego(@PathParam("id") int id, CalificacionVideojuegoUpdate calificacionVideojuegoUpdate) {
        try {
            CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
            calificacionVideojuegoService.actualizarCalificacion(calificacionVideojuegoUpdate, id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    private Response errorEjecucion(String mensaje, int tipo) {
        switch (tipo) {
            case 1 -> {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            case 2 -> {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            case 3 -> {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        }
        return null;
    }
}
