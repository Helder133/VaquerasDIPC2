/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.clasificacionVideojuego;

/**
 *
 * @author helder
 */
public class CalificacionVideojuego {
    
    private int calificacion_id;
    private int usuario_id;
    private int videojuego_id;
    private float calificacion;

    public CalificacionVideojuego(int usuario_id, int videojuego_id, float calificacion) {
        this.usuario_id = usuario_id;
        this.videojuego_id = videojuego_id;
        this.calificacion = calificacion;
    }

    public int getCalificacion_id() {
        return calificacion_id;
    }

    public void setCalificacion_id(int calificacion_id) {
        this.calificacion_id = calificacion_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
    
    public boolean isValid() {
        return usuario_id > 0 
                && videojuego_id > 0
                && calificacion >= 1 
                && calificacion <=5;
    }
    
}
