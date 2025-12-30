/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.configuracionSistema;

import ipc2_vaqueras.vaquerasdipc2.dtos.configuracionSistema.ConfiguracionResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.configuracionSistema.ConfiguracionUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.configuracionSistema.Configuracion;
import ipc2_vaqueras.vaquerasdipc2.services.configuracionSistema.ConfiguracionService;
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

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("configuracionSistema")
public class ConfiguracionSistemaResource {

    @Context
    UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConfiguracion() {
        try {
            ConfiguracionService configuracionService = new ConfiguracionService();
            Configuracion configuracion = configuracionService.obtenerLaConfiguracionDelSistema();
            return Response.ok(new ConfiguracionResponse(configuracion)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarConfiguracion(@PathParam("id") int id, ConfiguracionUpdate configuracionUpdate) {
        try {
            ConfiguracionService configuracionService = new ConfiguracionService();
            configuracionService.actualizarConfiguracionSistema(configuracionUpdate, id);
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
