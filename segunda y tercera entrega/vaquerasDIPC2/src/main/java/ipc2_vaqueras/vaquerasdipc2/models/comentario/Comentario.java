/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.models.comentario;

import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class Comentario {

    private int comentario_id;
    private int usuario_id;
    private int videojuego_id;
    private String comentario;
    private boolean visible;
    private LocalDateTime fecha_hora;
    private int comentario_padre;
    //---------------------------
    private String nombre;

    public Comentario(int usuario_id, int videojuego_id, String comentario, LocalDateTime fecha_hora) {
        this.usuario_id = usuario_id;
        this.videojuego_id = videojuego_id;
        this.comentario = comentario;
        this.visible = true;
        this.fecha_hora = fecha_hora;
    }

    public int getComentario_id() {
        return comentario_id;
    }

    public void setComentario_id(int comentario_id) {
        this.comentario_id = comentario_id;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getComentario_padre() {
        return comentario_padre;
    }

    public void setComentario_padre(int comentario_padre) {
        this.comentario_padre = comentario_padre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isValidar() {
        return usuario_id > 0
                && videojuego_id > 0
                && fecha_hora != null
                && StringUtils.isNotBlank(comentario);
    }

}
