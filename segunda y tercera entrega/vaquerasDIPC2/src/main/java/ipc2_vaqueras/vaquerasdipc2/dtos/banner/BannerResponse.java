/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.banner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import ipc2_vaqueras.vaquerasdipc2.models.banner.Banner;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.EnumClasificacion;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author helder
 */
public class BannerResponse {

    private int banner_id;
    private int videojuego_id;
    private boolean estado;
    private int empresa_id;
    private String nombreVideojuego;
    private float precio;
    private String recursoMinimo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;
    private byte[] imagen;
    private String descripcion;
    private EnumClasificacion clasificacion;
    private String nombreEmpresa;
    private double puntaje;
    private List<CategoriaVideojuego> categorias;
    
    public BannerResponse(Banner banner) {
        this.banner_id = banner.getBanner_id();
        this.videojuego_id = banner.getVideojuego_id();
        this.estado = banner.isEstado();
        this.empresa_id = banner.getEmpresa_id();
        this.nombreVideojuego = banner.getNombreVideojuego();
        this.precio = banner.getPrecio();
        this.recursoMinimo = banner.getRecursoMinimo();
        this.fecha = banner.getFecha();
        this.imagen = banner.getImagen();
        this.descripcion = banner.getDescripcion();
        this.clasificacion = banner.getClasificacion();
        this.nombreEmpresa = banner.getNombreEmpresa();
        this.puntaje = banner.getPuntaje();
        this.categorias = banner.getCategorias();
    }

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getNombreVideojuego() {
        return nombreVideojuego;
    }

    public void setNombreVideojuego(String nombreVideojuego) {
        this.nombreVideojuego = nombreVideojuego;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getRecursoMinimo() {
        return recursoMinimo;
    }

    public void setRecursoMinimo(String recursoMinimo) {
        this.recursoMinimo = recursoMinimo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EnumClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(EnumClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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
