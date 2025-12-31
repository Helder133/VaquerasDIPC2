/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.EnumClasificacion;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuego {

    private int biblioteca_id;
    private int videojuego_id;
    private int usuario_id;
    private LocalDate fecha;
    private boolean estado_instalacion;
    private String nombreVideojuego;
    private String nombreEmpresa;
    private String descripcion;
    private EnumClasificacion clasificacion;
    private byte[] imagen;
    private double rating_promedio;
    private int total;
    private double puntaje;
    private List<CategoriaVideojuego> categorias;
    private List<Multimedia> multimedias;
    
    public BibliotecaVideojuego(int videojuego_id, int usuario_id, LocalDate fecha, boolean estado) {
        this.videojuego_id = videojuego_id;
        this.usuario_id = usuario_id;
        this.fecha = fecha;
        this.estado_instalacion = estado;
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

    public double getRating_promedio() {
        return rating_promedio;
    }

    public void setRating_promedio(double rating_promedio) {
        this.rating_promedio = rating_promedio;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<Multimedia> getMultimedias() {
        return multimedias;
    }

    public void setMultimedias(List<Multimedia> multimedias) {
        this.multimedias = multimedias;
    }

    public EnumClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(EnumClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    public boolean isValid() {
        return videojuego_id > 0
                && usuario_id > 0
                && fecha != null;
    }

}
