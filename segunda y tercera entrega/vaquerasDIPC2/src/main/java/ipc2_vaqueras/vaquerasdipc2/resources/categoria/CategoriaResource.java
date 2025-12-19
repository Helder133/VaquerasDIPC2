/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.categoria;

import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.CategoriaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.CategoriaResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.CategoriaUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;
import ipc2_vaqueras.vaquerasdipc2.services.categoria.CategoriaService;
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
@Path("categoria")
public class CategoriaResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevaCategoria(CategoriaRequest categoriaRequest) {
        try {
            CategoriaService categoriaService = new CategoriaService();
            categoriaService.crearCategoria(categoriaRequest);
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
    public Response obtenerTodasLasCategorias() {
        try {
            CategoriaService categoriaService = new CategoriaService();
            List<CategoriaResponse> categorias = categoriaService.seleccionarTodosLasCategorias()
                    .stream()
                    .map(CategoriaResponse::new)
                    .toList();
            return Response.ok(categorias).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategoriaPorParametroInt(@PathParam("code") String code) {
        CategoriaService CategoriaService = new CategoriaService();
        try {
            int code1 = Integer.parseInt(code);

            Categoria categoria = CategoriaService.seleccionarCategoriaPorParametro(code1);
            return Response.ok(new CategoriaResponse(categoria)).build();
        } catch (NumberFormatException e) {
            return obtenerCategoriaPorParametroString(code);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response obtenerCategoriaPorParametroString(String code) {
        List<CategoriaResponse> categorias;
        CategoriaService CategoriaService = new CategoriaService();
        try {
            categorias = CategoriaService.seleccionarCategoriaPorParametro(code)
                    .stream()
                    .map(CategoriaResponse::new)
                    .toList();
            return Response.ok(categorias).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarCategoria(@PathParam("code") int code, CategoriaUpdate categoriaUpdate) {
        try {
            CategoriaService categoriaService = new CategoriaService();
            categoriaService.editarCategoria(categoriaUpdate, code);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    
    @DELETE
    @Path("{code}")
    public Response eliminarCategoria(@PathParam("code") int code){
        try {
            CategoriaService categoriaService = new CategoriaService();
            categoriaService.eliminarCategoria(code);
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
