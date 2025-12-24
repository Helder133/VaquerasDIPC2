/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.comprarVideojuego;

import java.time.LocalDate;

/**
 *
 * @author helder
 */
public class ComprarVideojuego {

    private int videojuego_id;
    private int usuario_id;
    private LocalDate fecha;

    public ComprarVideojuego(int videojuego_id, int usuario_id, LocalDate fecha) {
        this.videojuego_id = videojuego_id;
        this.usuario_id = usuario_id;
        this.fecha = fecha;
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

    public boolean isValid() {
        return videojuego_id > 0
                && usuario_id > 0
                && fecha != null;
    }
}
