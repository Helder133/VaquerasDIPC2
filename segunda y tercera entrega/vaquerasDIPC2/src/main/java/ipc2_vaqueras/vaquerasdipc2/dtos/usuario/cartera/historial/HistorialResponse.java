/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera.historial;

import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.EnumHistorial;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.Historial;
import java.time.LocalDate;

/**
 *
 * @author helder
 */
public class HistorialResponse {

    private int historial_id;
    private int cartera_id;
    private EnumHistorial transaccion;
    private LocalDate fecha;

    public HistorialResponse(Historial historial) {
        this.historial_id = historial.getHistorial_id();
        this.cartera_id = historial.getCartera_id();
        this.transaccion = historial.getTransaccion();
        this.fecha = historial.getFecha();
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
}
