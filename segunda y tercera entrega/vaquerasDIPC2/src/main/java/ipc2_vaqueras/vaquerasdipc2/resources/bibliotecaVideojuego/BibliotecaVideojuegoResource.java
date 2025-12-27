/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.bibliotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.BibliotecaVideojuegoResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.BibliotecaVideojuegoUpdate;
import ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego.BibliotecaVideojuegoService;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("bibliotecaVideojuego")
public class BibliotecaVideojuegoResource {

    @Context
    UriInfo context;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojuegosComprados(@PathParam("id") int id) {
        try {
            BibliotecaVideojuegoService  bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
            List<BibliotecaVideojuegoResponse> bibliotecaVideojuegoResponses = bibliotecaVideojuegoService.obtenerLaBibliotecaDeUnUsuario(id)
                    .stream()
                    .map(BibliotecaVideojuegoResponse::new)
                    .toList();
            return Response.ok(bibliotecaVideojuegoResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{usuario_id}/filtrar/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojeugosCompradosYFiltadoPorNombre(@PathParam("usuario_id") int usuario_id, @PathParam("nombre") String nombre) {
        try {
            BibliotecaVideojuegoService bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
            List<BibliotecaVideojuegoResponse> bibliotecaVideojuegoResponses = bibliotecaVideojuegoService.obtenerLaBibliotecaDeUnUsuarioFiltrandoPorNombre(usuario_id, nombre)
                    .stream()
                    .map(BibliotecaVideojuegoResponse::new)
                    .toList();
            return Response.ok(bibliotecaVideojuegoResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
   
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarEstadoDeVideojuego(BibliotecaVideojuegoUpdate bibliotecaVideojuegoUpdate) {
        try {
            BibliotecaVideojuegoService bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
            bibliotecaVideojuegoService.actualizarEstadoDeActualizacion(bibliotecaVideojuegoUpdate);
            return Response.ok().build();
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
