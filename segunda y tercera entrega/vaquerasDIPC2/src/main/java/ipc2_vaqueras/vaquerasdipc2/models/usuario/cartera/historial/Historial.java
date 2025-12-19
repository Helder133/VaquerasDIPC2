/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial;

import java.time.LocalDate;

/**
 *
 * @author helder
 */
public class Historial {
    
    private int historial_id;
    private int cartera_id;
    private EnumHistorial transaccion;
    private LocalDate fecha;

    public Historial(int cartera_id, EnumHistorial transaccion, LocalDate fecha) {
        this.cartera_id = cartera_id;
        this.transaccion = transaccion;
        this.fecha = fecha;
    }

    public int getHistorial_id() {
        return historial_id;
    }

    public void setHistorial_id(int historial_id) {
        this.historial_id = historial_id;
    }

    public int getCartera_id() {
        return cartera_id;
    }

    public void setCartera_id(int cartera_id) {
        this.cartera_id = cartera_id;
    }

    public EnumHistorial getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(EnumHistorial transaccion) {
        this.transaccion = transaccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public boolean isValid () {
        return fecha != null 
                && transaccion != null
                && cartera_id > 0;
    }
    
}
