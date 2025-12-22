/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.dtos.multimedia;

import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;

/**
 *
 * @author helder
 */
public class MultimediaResponse {
    
    private int multimedia_id;
    private int videojuego_id;
    private byte[] imagen;

    public MultimediaResponse(Multimedia multimedia) {
        this.multimedia_id = multimedia.getMultimedia_id();
        this.videojuego_id = multimedia.getVideojuego_id();
        this.imagen = multimedia.getImagen();
    }

    public int getMultimedia_id() {
        return multimedia_id;
    }

    public void setMultimedia_id(int multimedia_id) {
        this.multimedia_id = multimedia_id;
    }

    public int getVideojuego_id() {
        return videojuego_id;
    }

    public void setVideojuego_id(int videojuego_id) {
        this.videojuego_id = videojuego_id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    
}
