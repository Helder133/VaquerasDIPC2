/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.videojuego;

import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.videojuego.CategoriaVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.videojuego.CategoriaVideojuegoResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaResponse;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.videojuego.VideojuegoResponse;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
import ipc2_vaqueras.vaquerasdipc2.services.videojuego.VideojuegoService;
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
@Path("videojuego")
public class VideojuegoResource {

    @Context
    UriInfo context;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response crearVideojuego(
            @FormDataParam("empresa_id") int empresa_id,
            @FormDataParam("nombre") String nombre,
            @FormDataParam("precio") float precio,
            @FormDataParam("recurso_minimo") String recurso_minimo,
            @FormDataParam("edad_minima") int edad_minima,
            @FormDataParam("estado") boolean estado,
            @FormDataParam("fecha") String fecha,
            @FormDataParam("imagen") InputStream imagenInput,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail,
            @FormDataParam("descripcion") String descripcion
    ) {
        VideojuegoService videojuegoService = new VideojuegoService();
        byte[] imagen = null;
        try {
            if (imagenInput != null && fileDetail != null) {
                imagen = imagenInput.readAllBytes();
            }
            Videojuego videojuego = new Videojuego(empresa_id, nombre, precio, recurso_minimo, edad_minima, estado, LocalDate.parse(fecha), imagen, descripcion);
            videojuegoService.crearVideojuego(videojuego);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosJuegos() {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            List<VideojuegoResponse> videojuegos = videojuegoService.obtenerTodosLosVideojuegos()
                    .stream()
                    .map(VideojuegoResponse::new)
                    .toList();
            return Response.ok(videojuegos).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @GET
    @Path("/noComprado/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojuegoNoComprado(@PathParam("id") int id) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            List<VideojuegoResponse> videojuegos = videojuegoService.obtenerTodosLosVideojuegosNoComprado(id)
                    .stream()
                    .map(VideojuegoResponse::new)
                    .toList();
            return Response.ok(videojuegos).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVideojuegosPorParametro(@PathParam("code") String code) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            int id = Integer.parseInt(code);
            Videojuego videojuego = videojuegoService.obtenerVideojuegosPorParametro(id);
            return Response.ok(new VideojuegoResponse(videojuego)).build();
        } catch (NumberFormatException e) {
            return obtenerUsuariosPorString(code);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response obtenerUsuariosPorString(String code) {
        List<VideojuegoResponse> videojuegos;
        VideojuegoService videojuegoService = new VideojuegoService();
        try {
            videojuegos = videojuegoService.obtenerVideojuegosPorParametro(code)
                    .stream()
                    .map(VideojuegoResponse::new)
                    .toList();
            return Response.ok(videojuegos).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response actualizarVideojuego(@PathParam("code") int code,
            @FormDataParam("nombre") String nombre,
            @FormDataParam("precio") float precio,
            @FormDataParam("recurso_minimo") String recurso_minimo,
            @FormDataParam("edad_minima") int edad_minima,
            @FormDataParam("estado") boolean estado,
            @FormDataParam("imagen") InputStream imagenInput,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail,
            @FormDataParam("descripcion") String descripcion
    ) {
        VideojuegoService videojuegoService = new VideojuegoService();
        byte[] imagen = null;
        try {
            if (imagenInput != null && fileDetail != null) {
                imagen = imagenInput.readAllBytes();
            }
            Videojuego videojuego = new Videojuego(0, nombre, precio, recurso_minimo, edad_minima, estado, null, imagen, descripcion);
            videojuego.setVideojuego_id(code);
            videojuegoService.actualizarVideojuego(videojuego);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    //Multimedia
    @POST
    @Path("/multimedia")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response crearMultimedia(@FormDataParam("videojuego_id") int videojuego_id,
            @FormDataParam("imagen") InputStream imagenInput,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail
    ) {
        VideojuegoService videojuegoService = new VideojuegoService();
        byte[] imagen = null;
        try {
            if (imagenInput != null && fileDetail != null) {
                imagen = imagenInput.readAllBytes();
            }
            MultimediaRequest multimediaRequest = new MultimediaRequest();
            multimediaRequest.setVideojuego_id(videojuego_id);
            multimediaRequest.setImagen(imagen);
            videojuegoService.crearMultimedia(multimediaRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("/multimedia/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response editarMultimedia(@PathParam("id") int id,
            @FormDataParam("videojuego_id") int videojuego_id,
            @FormDataParam("imagen") InputStream imagenInput,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail
    ) {
        VideojuegoService videojuegoService = new VideojuegoService();
        byte[] imagen = null;
        try {
            if (imagenInput != null && fileDetail != null) {
                imagen = imagenInput.readAllBytes();
            }
            MultimediaUpdate multimediaUpdate = new MultimediaUpdate();
            multimediaUpdate.setVideojuego_id(videojuego_id);
            multimediaUpdate.setImagen(imagen);
            videojuegoService.editarMultimedia(multimediaUpdate, id);
            return Response.ok().build();
        } catch (UserDataInvalidException | IOException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @GET
    @Path("/multimedia/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMultimedia(@PathParam("id") int id) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            Multimedia multimedia = videojuegoService.obtenerMultimediaPorId(id);
            return Response.ok(new MultimediaResponse(multimedia)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @GET
    @Path("/multimedias/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMultimediasSe(@PathParam("id") int id) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            List<MultimediaResponse> multimedias = videojuegoService.obtenerMultimediasDeUnVideojuego(id)
                    .stream()
                    .map(MultimediaResponse::new)
                    .toList();
            return Response.ok(multimedias).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);

        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @DELETE
    @Path("/multimedia/{id}")
    public Response eliminarMultimedia(@PathParam("id") int id) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            videojuegoService.eliminarMultimedia(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    //Categoria
    @POST
    @Path("/categoria")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearVideojuegoCategoria(CategoriaVideojuegoRequest categoriaVideojuegoRequest) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            videojuegoService.crearVideojuegoCategoria(categoriaVideojuegoRequest);
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
    @Path("/categoria/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategoriasDeUnVideojuego(@PathParam("id") int id){
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            List<CategoriaVideojuegoResponse> categoriasDeVideojuego = videojuegoService.obtenerCategoriasDeUnVideojuego(id)
                    .stream()
                    .map(CategoriaVideojuegoResponse::new)
                    .toList();
            return Response.ok(categoriasDeVideojuego).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }
    
    @DELETE
    @Path("{videojuego_id}/categoria/{categoria_id}")
    public Response eleiminarCategoriaDeUnVideojuego(@PathParam("videojuego_id") int videojuego_id, @PathParam("categoria_id") int categoria_id) {
        try {
            VideojuegoService videojuegoService = new VideojuegoService();
            videojuegoService.eliminarCategoriaDeUnVideojuego(videojuego_id, categoria_id);
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
