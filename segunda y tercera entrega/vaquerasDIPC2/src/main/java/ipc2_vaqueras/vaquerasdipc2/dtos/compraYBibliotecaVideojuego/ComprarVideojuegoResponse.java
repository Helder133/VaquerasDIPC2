/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import ipc2_vaqueras.vaquerasdipc2.models.comprarVideojuego.ComprarVideojuego;
import java.time.LocalDate;

/**
 *
 * @author helder
 */
public class ComprarVideojuegoResponse {

    private int videojuego_id;
    private int usuario_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;
    private int empresa_id;
    private String nombreEmpresa;
    private String nombreVideojuego;
    private String descripcion;
    private byte[] imagen;

    public ComprarVideojuegoResponse(ComprarVideojuego comprarVideojuego) {
        this.videojuego_id = comprarVideojuego.getVideojuego_id();
        this.usuario_id = comprarVideojuego.getUsuario_id();
        this.fecha = comprarVideojuego.getFecha();
        this.empresa_id = comprarVideojuego.getEmpresa_id();
        this.nombreEmpresa = comprarVideojuego.getNombreEmpresa();
        this.nombreVideojuego = comprarVideojuego.getNombreVideojuego();
        this.descripcion = comprarVideojuego.getDescripcion();
        this.imagen = comprarVideojuego.getImagen();
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

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreVideojuego() {
        return nombreVideojuego;
    }

    public void setNombreVideojuego(String nombreVideojuego) {
        this.nombreVideojuego = nombreVideojuego;
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
}
