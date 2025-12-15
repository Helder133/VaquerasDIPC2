/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.empresa;

/**
 *
 * @author helder
 */
public class EmpresaRequest {
    private String nombre;
    private String descripcion;
    private float comision_negociada;

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
    
}
