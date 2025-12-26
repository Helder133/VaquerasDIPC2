/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.resources.bibliotecaVideojuego;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author helder
 */
@Path("bibliotecaVideojuego")
public class BibliotecaVideojuegoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BibliotecaVideojuegoResource
     */
    public BibliotecaVideojuegoResource() {
    }

    /**
     * Retrieves representation of an instance of ipc2_vaqueras.vaquerasdipc2.resources.bibliotecaVideojuego.BibliotecaVideojuegoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of BibliotecaVideojuegoResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
