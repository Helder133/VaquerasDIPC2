/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego;

import java.time.LocalDate;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuego {

    private int biblioteca_id;
    private int videojuego_id;
    private int usuario_id;
    private LocalDate fecha;
    private boolean estado;
    private String nombre;
    private String recursoMinimo;
    private int edadMinima;
    private byte[] imagen;
    
    
    public BibliotecaVideojuego(int videojuego_id, int usuario_id, LocalDate fecha, boolean estado) {
        this.videojuego_id = videojuego_id;
        this.usuario_id = usuario_id;
        this.fecha = fecha;
        this.estado = estado;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRecursoMinimo() {
        return recursoMinimo;
    }

    public void setRecursoMinimo(String recursoMinimo) {
        this.recursoMinimo = recursoMinimo;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public boolean isValid() {
        return videojuego_id > 0
                && usuario_id > 0
                && fecha != null;
    }

}
