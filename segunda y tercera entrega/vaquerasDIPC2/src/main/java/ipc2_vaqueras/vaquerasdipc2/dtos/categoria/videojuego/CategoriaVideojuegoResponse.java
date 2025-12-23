/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.categoria.videojuego;

import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;

/**
 *
 * @author helder
 */
public class CategoriaVideojuegoResponse {

    private int videojuego_id;
    private int categoria_id;
    private String nombre;

    public CategoriaVideojuegoResponse(CategoriaVideojuego categoriaVideojuego) {
        this.videojuego_id = categoriaVideojuego.getVideojuego_id();
        this.categoria_id = categoriaVideojuego.getCategoria_id();
        this.nombre = categoriaVideojuego.getNombre();
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
