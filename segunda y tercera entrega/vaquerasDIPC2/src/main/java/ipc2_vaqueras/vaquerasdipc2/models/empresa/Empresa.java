/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.empresa;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class Empresa {
    private int empresa_id;
    private String nombre;
    private String descripcion;
    private float comisionNegociada;
    private boolean estado;
    private boolean estadoComentario;

    public Empresa(String nombre, String descripcion, float comision_negociada) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.comisionNegociada = comision_negociada;
        this.estado = true;
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
    
    public boolean isValid () {
        return StringUtils.isNotBlank(nombre)
                && StringUtils.isNotBlank(descripcion);
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
