/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego.BibliotecaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuegoResponse {
    
    private int biblioteca_id;
    private int videojuego_id;
    private int usuario_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;
    private boolean estado_instalacion;
    private String nombreVideojuego;
    private String nombreEmpresa;
    private String descripcion;
    private byte[] imagen;
    private double puntaje;
    private List<CategoriaVideojuego> categorias;
    
    public BibliotecaVideojuegoResponse(BibliotecaVideojuego bibliotecaVideojuego) {
        this.biblioteca_id = bibliotecaVideojuego.getBiblioteca_id();
        this.videojuego_id = bibliotecaVideojuego.getVideojuego_id();
        this.usuario_id = bibliotecaVideojuego.getUsuario_id();
        this.fecha = bibliotecaVideojuego.getFecha();
        this.estado_instalacion = bibliotecaVideojuego.isEstado_instalacion();
        this.nombreVideojuego = bibliotecaVideojuego.getNombreVideojuego();
        this.nombreEmpresa = bibliotecaVideojuego.getNombreEmpresa();
        this.descripcion = bibliotecaVideojuego.getDescripcion();
        this.imagen = bibliotecaVideojuego.getImagen();;
        this.puntaje = bibliotecaVideojuego.getPuntaje();
        this.categorias = bibliotecaVideojuego.getCategorias();
    }

    public int getBiblioteca_id() {
        return biblioteca_id;
    }

    public void setBiblioteca_id(int biblioteca_id) {
        this.biblioteca_id = biblioteca_id;
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado_instalacion() {
        return estado_instalacion;
    }

    public void setEstado_instalacion(boolean estado_instalacion) {
        this.estado_instalacion = estado_instalacion;
    }

    public String getNombreVideojuego() {
        return nombreVideojuego;
    }

    public void setNombreVideojuego(String nombreVideojuego) {
        this.nombreVideojuego = nombreVideojuego;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public List<CategoriaVideojuego> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaVideojuego> categorias) {
        this.categorias = categorias;
    }
    
}
