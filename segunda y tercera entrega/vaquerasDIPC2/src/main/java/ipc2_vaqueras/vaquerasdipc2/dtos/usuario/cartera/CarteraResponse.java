/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera;

import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;

/**
 *
 * @author helder
 */
public class CarteraResponse {
    private int cartera_id;
    private int usuario_id;
    private float saldo;
    
    public CarteraResponse (Cartera cartera) {
        this.cartera_id = cartera.getCartera_id();
        this.usuario_id = cartera.getUsuario_id();
        this.saldo = cartera.getSaldo();
    }

    public int getCartera_id() {
        return cartera_id;
    }

    public void setCartera_id(int cartera_id) {
        this.cartera_id = cartera_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
}
