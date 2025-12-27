/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.empresa;

/**
 *
 * @author helder
 */
public class EmpresaUpdate {
    private String nombre;
    private String descripcion;
    private float comisionNegociada;
    private boolean estado;
    private boolean estadoComentario;
    
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

    public float getComisionNegociada() {
        return comisionNegociada;
    }

    public void setComisionNegociada(float comisionNegociada) {
        this.comisionNegociada = comisionNegociada;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstadoComentario() {
        return estadoComentario;
    }

    public void setEstadoComentario(boolean estadoComentario) {
        this.estadoComentario = estadoComentario;
    }
    
}
