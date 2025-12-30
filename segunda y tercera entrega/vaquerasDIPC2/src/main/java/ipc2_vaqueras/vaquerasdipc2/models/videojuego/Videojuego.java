/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.videojuego;

import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class Videojuego {
    private int videojuego_id;
    private int empresa_id;
    private String nombre;
    private float precio;
    private String recurso_minimo;
    private EnumClasificacion clasificacion;
    private boolean estado;
    private LocalDate fecha;
    private byte[] imagen;
    private String descripcion;
    private String nombre_empresa;
    private double rating_promedio;
    private int total;
    private double puntaje;
    private List<CategoriaVideojuego> categorias;
    private List<Multimedia> multimedias;

    public Videojuego(int empresa_id, String nombre, float precio, String recurso_minimo, EnumClasificacion clasificacion, boolean estado, LocalDate fecha, byte[] imagen, String descripcion) {
        this.empresa_id = empresa_id;
        this.nombre = nombre;
        this.precio = precio;
        this.recurso_minimo = recurso_minimo;
        this.clasificacion = clasificacion;
        this.estado = estado;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getRecurso_minimo() {
        return recurso_minimo;
    }

    public void setRecurso_minimo(String recurso_minimo) {
        this.recurso_minimo = recurso_minimo;
    }

    public EnumClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(EnumClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
    
    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
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
    
    public boolean isValid () {
        return StringUtils.isNotBlank(nombre)
                && StringUtils.isNotBlank(recurso_minimo)
                && empresa_id > 0
                && precio >= 0
                && clasificacion != null
                && fecha != null;
    }
    
}
