/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera;

/**
 *
 * @author helder
 */
public class Cartera {
    
    private int cartera_id;
    private int usuario_id;
    private float saldo;

    public Cartera(int usuario_id, float saldo) {
        this.usuario_id = usuario_id;
        this.saldo = saldo;
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
