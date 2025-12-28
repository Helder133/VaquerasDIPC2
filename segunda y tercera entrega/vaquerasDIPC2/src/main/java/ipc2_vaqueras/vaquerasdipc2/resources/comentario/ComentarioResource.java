/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.comentario;

import ipc2_vaqueras.vaquerasdipc2.dtos.comentario.ComentarioRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.comentario.ComentarioResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.comentario.ComentarioUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.comentario.Comentario;
import ipc2_vaqueras.vaquerasdipc2.services.comentario.ComentarioService;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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
@Path("comentario")
public class ComentarioResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarcomentario(ComentarioRequest comentarioRequest) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            comentarioService.ComentarioVideojuego(comentarioRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLosComentariosPorParteDelUsuario(@PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            List<ComentarioResponse> comentarios = comentarioService.seleccionarTodosLosComentariosVisibles(id)
                    .stream()
                    .map(ComentarioResponse::new)
                    .toList();
            return Response.ok(comentarios).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/empresa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLosComentariosPorParteDelEmpresa(@PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            List<ComentarioResponse> comentarios = comentarioService.seleccionarTodosLosComentarios(id)
                    .stream()
                    .map(ComentarioResponse::new)
                    .toList();
            return Response.ok(comentarios).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/usuario/unico/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentarioPorParteDelUsuario(@PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            Comentario comentario = comentarioService.seleccionarUnComentarioVisible(id);
            return Response.ok(new ComentarioResponse(comentario)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/empresa/unico/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerComentarioPorParteDelEmpresa(@PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            Comentario comentario = comentarioService.seleccionarUnComentario(id);
            return Response.ok(new ComentarioResponse(comentario)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarComentario(ComentarioUpdate comentarioUpdate, @PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            comentarioService.actualizarComentario(comentarioUpdate, id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminarComentario(@PathParam("id") int id) {
        try {
            ComentarioService comentarioService = new ComentarioService();
            comentarioService.eliminarComentario(id);
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
