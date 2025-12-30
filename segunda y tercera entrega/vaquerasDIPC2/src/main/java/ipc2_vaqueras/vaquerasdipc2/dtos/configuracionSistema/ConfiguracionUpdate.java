/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.configuracionSistema;

/**
 *
 * @author helder
 */
public class ConfiguracionUpdate {

    private float porcentaje_ganancia;
    private int tamaño_grupo;

    public float getPorcentaje_ganancia() {
        return porcentaje_ganancia;
    }

    public void setPorcentaje_ganancia(float porcentaje_ganancia) {
        this.porcentaje_ganancia = porcentaje_ganancia;
    }

    public int getTamaño_grupo() {
        return tamaño_grupo;
    }

    public void setTamaño_grupo(int tamaño_grupo) {
        this.tamaño_grupo = tamaño_grupo;
    }
}
