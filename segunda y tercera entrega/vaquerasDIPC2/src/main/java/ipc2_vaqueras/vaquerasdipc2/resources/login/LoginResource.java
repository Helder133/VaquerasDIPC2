/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.login;

import ipc2_vaqueras.vaquerasdipc2.dtos.login.LoginRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioResponse;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.UsuarioService;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("login")
public class LoginResource {

    @Context
    UriInfo context;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesion(LoginRequest loginRequest) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.login(loginRequest);
            return Response.ok(new UsuarioResponse(usuario)).build();
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
