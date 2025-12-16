/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.EnumUsuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.UsuarioService;
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
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("usuario")
public class UsuarioResource {

    @Context
    UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosUsuarios() {
        try {
            UsuarioService usuarioService = new UsuarioService();
            List<UsuarioResponse> usuarios = usuarioService.seleccionarTodosLosUsuarios()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByInt(@PathParam("code") String code) {
        UsuarioService usuarioService = new UsuarioService();
        try {
            int code1 = Integer.parseInt(code);

            Usuario existingUser = usuarioService.seleccionarUsuarioPorParametro(code1);
            return Response.ok(new UsuarioResponse(existingUser)).build();
        } catch (NumberFormatException e) {
            return getUserString(code);
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (UserDataInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    private Response getUserString(String code) {
        List<UsuarioResponse> usuarios;
        UsuarioService usuarioService = new UsuarioService();
        try {
            usuarios = usuarioService.seleccionarUsuarioPorParametro(code)
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
            return Response.ok(usuarios).build();
        } catch (SQLException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (UserDataInvalidException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response nuevoUsuario(@FormDataParam("nombre") String nombre,
            @FormDataParam("email") String email,
            @FormDataParam("contraseña") String contraseña,
            @JsonFormat(pattern = "yyyy-MM-dd")
            @JsonDeserialize(using = LocalDateDeserializer.class)
            @FormDataParam("fecha_nacimiento") LocalDate fecha_nacimiento,
            @FormDataParam("rol") EnumUsuario rol,
            @FormDataParam("telefono") String telefono,
            @FormDataParam("avatar") InputStream avatarInput,
            @FormDataParam("avatar") FormDataContentDisposition fileDetai,
            @FormDataParam("pais") String pais) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            UsuarioRequest usuarioRequest = new UsuarioRequest();
            
            usuarioRequest.setNombre(nombre);
            usuarioRequest.setEmail(email);
            usuarioRequest.setContraseña(contraseña);
            usuarioRequest.setFecha_nacimiento(fecha_nacimiento);
            usuarioRequest.setRol(rol);
            usuarioRequest.setTelefono(telefono);
            usuarioRequest.setAvatar("avatar");
            usuarioRequest.setPais(pais);
            
            usuarioService.crearUsuario(usuarioRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (EntityAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("code") int code, UsuarioUpdate usuarioUpdate) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.editarUsuario(code, usuarioUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (EntityAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @DELETE
    @Path("{code}")
    public Response deleteUser(@PathParam("code") int code) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.eliminarUsuario(code);
            return Response.ok().build();
        } catch (EntityAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
