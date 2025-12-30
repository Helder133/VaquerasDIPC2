/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.empresa;

import ipc2_vaqueras.vaquerasdipc2.dtos.empresa.EmpresaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.empresa.EmpresaResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.empresa.EmpresaUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.videojuego.VideojuegoResponse;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.empresa.Empresa;
import ipc2_vaqueras.vaquerasdipc2.services.empresa.EmpresaService;
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
import java.util.List;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("empresa")
public class EmpresaResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevaEmpresa(EmpresaRequest empresaRequest) {
        try {
            EmpresaService empresaService = new EmpresaService();
            empresaService.crearEmpresa(empresaRequest);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodasLasEmpresas() {
        try {
            EmpresaService empresaService = new EmpresaService();
            List<EmpresaResponse> empresas = empresaService.seleccionarTodasLasEmpresas()
                    .stream()
                    .map(EmpresaResponse::new)
                    .toList();
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/videojuego/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojuegoEmpresa(@PathParam("code") int code) {
        try {
            EmpresaService empresaService = new EmpresaService();
            List<VideojuegoResponse> videojuegos = empresaService.obteneVideojuegosDeLaEmpresa(code)
                    .stream()
                    .map(VideojuegoResponse::new)
                    .toList();
            return Response.ok(videojuegos).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpresaPorParametroInt(@PathParam("code") String code) {
        EmpresaService empresaService = new EmpresaService();
        try {
            int code1 = Integer.parseInt(code);

            Empresa existingEmpresa = empresaService.seleccionarEmpresaPorParametro(code1);
            return Response.ok(new EmpresaResponse(existingEmpresa)).build();
        } catch (NumberFormatException e) {
            return obtenerEmpresaPorParametroString(code);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response obtenerEmpresaPorParametroString(String code) {
        List<EmpresaResponse> empresas;
        EmpresaService empresaService = new EmpresaService();
        try {
            empresas = empresaService.seleccionarEmpresaPorParametro(code)
                    .stream()
                    .map(EmpresaResponse::new)
                    .toList();
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarEmpresa(@PathParam("code") int code, EmpresaUpdate empresaUpdate) {
        try {
            EmpresaService empresaService = new EmpresaService();
            empresaService.editarEmpresa(code, empresaUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    /*
    @DELETE
    @Path("{code}")
    public Response eliminarEmpresa(@PathParam("code") int code){
        try {
            EmpresaService empresaService = new EmpresaService();
            empresaService.eliminarEmpresa(code);
            return Response.ok().build();
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }*/

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
