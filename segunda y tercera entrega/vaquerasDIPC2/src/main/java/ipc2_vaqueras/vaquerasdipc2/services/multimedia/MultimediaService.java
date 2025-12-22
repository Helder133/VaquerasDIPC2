/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.multimedia;

import ipc2_vaqueras.vaquerasdipc2.db.multimedia.MultimediaDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class MultimediaService {
    
    public void crearMultimedia(MultimediaRequest multimediaRequest) throws SQLException, UserDataInvalidException {
        Multimedia multimedia = extraerDatos(multimediaRequest);
        MultimediaDB multimediaDB = new MultimediaDB();
        multimediaDB.insertar(multimedia);
    }

    private Multimedia extraerDatos(MultimediaRequest multimediaRequest) throws UserDataInvalidException{
        Multimedia multimedia = new  Multimedia(multimediaRequest.getVideojuego_id(), multimediaRequest.getImagen());
        if (!multimedia.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return multimedia;
    }
    
    public void editarMultimedia(MultimediaUpdate multimediaUpdate, int id) throws SQLException, UserDataInvalidException {
        Multimedia multimedia = extraerDatos(multimediaUpdate);
        multimedia.setMultimedia_id(id);
        MultimediaDB multimediaDB = new MultimediaDB();
        multimediaDB.actualizar(multimedia);
    }
    
    private Multimedia extraerDatos(MultimediaUpdate multimediaUpdate) throws UserDataInvalidException{
        Multimedia multimedia = new  Multimedia(multimediaUpdate.getVideojuego_id(), multimediaUpdate.getImagen());
        if (!multimedia.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return multimedia;
    }
     
    public Multimedia obtenerMultimediaPorId(int t) throws SQLException, UserDataInvalidException {
        MultimediaDB multimediaDB = new MultimediaDB();
        Optional<Multimedia> multimeOptional = multimediaDB.seleccionarPorParametro(t);
        if (multimeOptional.isEmpty()) {
            throw new UserDataInvalidException("El multimedia seleccionado no existe");
        }
        return multimeOptional.get(); 
    }
    
    public List<Multimedia> obtenerMultimediasDeUnVideojuego(int t) throws SQLException {
        MultimediaDB multimediaDB = new MultimediaDB();
        return multimediaDB.seleccionarMultimediasDeUnVideojuego(t);
    }
    
    public void eliminarMultimedia(int t) throws SQLException {
        MultimediaDB multimediaDB = new MultimediaDB();
        multimediaDB.eliminar(t);
    }
}
