/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.configuracionSistema;

/**
 *
 * @author helder
 */
public class Configuracion {

    private int configuracion_id;
    private float porcentaje_ganancia;
    private int tamaño_grupo;

    public Configuracion(float porcentaje_ganancia, int tamaño_grupo) {
        this.porcentaje_ganancia = porcentaje_ganancia;
        this.tamaño_grupo = tamaño_grupo;
    }

    public int getConfiguracion_id() {
        return configuracion_id;
    }

    public void setConfiguracion_id(int configuracion_id) {
        this.configuracion_id = configuracion_id;
    }

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

    public boolean isValid() {
        return porcentaje_ganancia > 0
                && tamaño_grupo > 0;
    }

}
