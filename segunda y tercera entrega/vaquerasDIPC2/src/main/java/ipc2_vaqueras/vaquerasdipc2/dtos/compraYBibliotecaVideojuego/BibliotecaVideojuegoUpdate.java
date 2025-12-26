/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuegoUpdate {
    
    private int biblioteca_id;
    private boolean estado_instalacion;

    public int getBiblioteca_id() {
        return biblioteca_id;
    }

    public void setBiblioteca_id(int biblioteca_id) {
        this.biblioteca_id = biblioteca_id;
    }

    public boolean isEstado_instalacion() {
        return estado_instalacion;
    }

    public void setEstado_instalacion(boolean estado_instalacion) {
        this.estado_instalacion = estado_instalacion;
    }
    
    
    
}
