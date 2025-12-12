/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaquerad.vaquerasdipc2.resources;

import ipc2_vaquerad.vaquerasdipc2.dtos.usuario.UsuarioRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("nuevoUsuario")
public class NuevoUsuarioResource {

    @Context
    UriInfo context;
    
    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of NuevoUsuarioResource
     * @param content representation for the resource
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoUsuario(UsuarioRequest usuarioRequest) {
        try {
            return Response.ok(usuarioRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
