/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.banner;

import ipc2_vaqueras.vaquerasdipc2.dtos.banner.BannerRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.banner.BannerResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.banner.BannerUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.services.banner.BannerService;
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
@Path("banner")
public class BannerResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarVideojuegoABaner(BannerRequest bannerRequest) {
        try {
            BannerService bannerService = new BannerService();
            bannerService.insertarBanner(bannerRequest);
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
    @Path("/admin_sistema")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosBanner() {
        try {
            BannerService bannerService = new BannerService();
            List<BannerResponse> banners = bannerService.seleccionarTodos()
                    .stream()
                    .map(BannerResponse::new)
                    .toList();
            return Response.ok(banners).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosBannerParaUsuario() {
        try {
            BannerService bannerService = new BannerService();
            List<BannerResponse> banners = bannerService.seleccionarTodosHabilitados()
                    .stream()
                    .map(BannerResponse::new)
                    .toList();
            return Response.ok(banners).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{banner_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarBanner(BannerUpdate bannerUpdate, @PathParam("banner_id") int banner_id) {
        try {
            BannerService bannerService = new BannerService();
            bannerService.actualizarBanner(bannerUpdate, banner_id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @DELETE
    @Path("{banner_id}")
    public Response eliminarBanner(@PathParam("banner_id") int banner_id) {
        try {
            BannerService bannerService = new BannerService();
            bannerService.eliminarBanner(banner_id);
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
