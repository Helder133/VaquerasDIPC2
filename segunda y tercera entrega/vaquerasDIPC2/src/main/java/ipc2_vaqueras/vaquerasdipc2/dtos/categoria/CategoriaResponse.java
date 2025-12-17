/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.categoria;

import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;

/**
 *
 * @author helder
 */
public class CategoriaResponse {

    private int categoria_id;
    private String nombre;
    private String descripcion;

    public CategoriaResponse(Categoria categoria) {
        this.nombre = categoria.getNombre();
        this.descripcion = categoria.getDescripcion();
        this.categoria_id = categoria.getCategoria_id();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}
