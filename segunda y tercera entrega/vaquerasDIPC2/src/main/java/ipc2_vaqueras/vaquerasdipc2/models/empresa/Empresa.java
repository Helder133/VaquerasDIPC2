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
    private float comision_negociada;
    private boolean estado;

    public Empresa(String nombre, String descripcion, float comision_negociada) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.comision_negociada = comision_negociada;
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

    public float getComision_negociada() {
        return comision_negociada;
    }

    public void setComision_negociada(float comision_negociada) {
        this.comision_negociada = comision_negociada;
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
    
}
