/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.usuario;

import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera.CarteraResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera.CarteraUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.EnumUsuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;
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
import java.io.IOException;
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
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("code") String code) {
        UsuarioService usuarioService = new UsuarioService();
        try {
            int code1 = Integer.parseInt(code);

            Usuario existingUser = usuarioService.seleccionarUsuarioPorParametro(code1);
            return Response.ok(new UsuarioResponse(existingUser)).build();
        } catch (NumberFormatException e) {
            return obtenerUsuariosPorString(code);
            
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        } 
    }
    
    @GET
    @Path("/cartera/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCartera(@PathParam("id") int id) {
        UsuarioService usuarioService = new UsuarioService();
        try {
            Cartera cartera = usuarioService.obtenerCartera(id);
            return Response.ok(new CarteraResponse(cartera)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    private Response obtenerUsuariosPorString(String code) {
        List<UsuarioResponse> usuarios;
        UsuarioService usuarioService = new UsuarioService();
        try {
            usuarios = usuarioService.seleccionarUsuarioPorParametro(code)
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
            return Response.ok(usuarios).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        } 
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response nuevoUsuario(@FormDataParam("nombre") String nombre,
            @FormDataParam("email") String email,
            @FormDataParam("password") String contraseña,
            @FormDataParam("fecha_nacimiento") String fecha_nacimiento,
            @FormDataParam("rol") EnumUsuario rol,
            @FormDataParam("telefono") String telefono,
            @FormDataParam("avatar") InputStream avatarInput,
            @FormDataParam("avatar") FormDataContentDisposition fileDetail,
            @FormDataParam("pais") String pais) {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        byte[] foto = null;
        try {
            if (avatarInput != null && fileDetail != null) {
                foto = avatarInput.readAllBytes();
            }

            usuarioRequest.setNombre(nombre);
            usuarioRequest.setEmail(email);
            usuarioRequest.setContraseña(contraseña);
            usuarioRequest.setFecha_nacimiento(LocalDate.parse(fecha_nacimiento));
            usuarioRequest.setRol(rol);
            usuarioRequest.setTelefono(telefono);
            usuarioRequest.setAvatar(foto);
            usuarioRequest.setPais(pais);

            usuarioService.crearUsuario(usuarioRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }

    }
            
    @PUT
    @Path("{code}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateUser(@PathParam("code") int code,
            @FormDataParam("nombre") String nombre,
            @FormDataParam("password") String contraseña,
            @FormDataParam("fecha_nacimiento") String fecha_nacimiento,
            @FormDataParam("telefono") String telefono,
            @FormDataParam("avatar") InputStream avatarInput,
            @FormDataParam("avatar") FormDataContentDisposition fileDetail,
            @FormDataParam("pais") String pais) {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioUpdate usuarioUpdate = new UsuarioUpdate();
        byte[] foto = null;
        try {
            if (avatarInput != null && fileDetail != null) {
                foto = avatarInput.readAllBytes();
            }
            
            usuarioUpdate.setNombre(nombre);
            usuarioUpdate.setContraseña(contraseña);
            usuarioUpdate.setFecha_nacimiento(LocalDate.parse(fecha_nacimiento));
            usuarioUpdate.setTelefono(telefono);
            usuarioUpdate.setAvatar(foto);
            usuarioUpdate.setPais(pais);
            
            usuarioService.editarUsuario(code, usuarioUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        } 
    }
    
    @PUT
    @Path("/cartera")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recargarCartera(CarteraUpdate carteraUpdate) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.depositarEnCartera(carteraUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
            
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
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
            return errorEjecucion(e.getMessage(), 2);
            
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
