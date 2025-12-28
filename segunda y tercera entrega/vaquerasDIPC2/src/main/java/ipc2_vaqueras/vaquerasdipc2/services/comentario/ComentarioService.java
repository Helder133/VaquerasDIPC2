/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.comentario;

import ipc2_vaqueras.vaquerasdipc2.db.comentario.ComentarioDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.comentario.ComentarioRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.comentario.ComentarioUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.comentario.Comentario;
import ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego.BibliotecaVideojuegoService;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class ComentarioService {
    
    public void ComentarioVideojuego (ComentarioRequest comentarioRequest) throws SQLException, UserDataInvalidException {
        Comentario comentario = extraerComentario(comentarioRequest);
        BibliotecaVideojuegoService bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
        if (!bibliotecaVideojuegoService.validarQueTengaElVideojuego(comentario.getUsuario_id(), comentario.getVideojuego_id())) {
            throw new UserDataInvalidException("Necesita tener comprado el videojuego, antes de comentar");
        }
        ComentarioDB comentarioDB = new ComentarioDB();
        comentarioDB.insertar(comentario);
    }

    private Comentario extraerComentario(ComentarioRequest comentarioRequest) throws UserDataInvalidException{
        Comentario comentario = new Comentario(comentarioRequest.getUsuario_id(), comentarioRequest.getVideojuego_id(), comentarioRequest.getComentario(), comentarioRequest.getFecha_hora());
        comentario.setComentario_padre(comentarioRequest.getComentario_padre());
        if (!comentario.isValidar()) {
            throw new UserDataInvalidException("Error en lo datos enviados, vuelva a intentar");
        }
        return comentario;
    }
    
    public void actualizarComentario(ComentarioUpdate comentarioUpdate, int comentario_id) throws SQLException, UserDataInvalidException {
        Comentario comentario = extraerComentario(comentarioUpdate);
        comentario.setComentario_id(comentario_id);
        ComentarioDB comentarioDB = new ComentarioDB();
        comentarioDB.actualizar(comentario);
    }
    
    private Comentario extraerComentario(ComentarioUpdate comentarioUpdate) throws UserDataInvalidException{
        Comentario comentario = new Comentario(0, 0, comentarioUpdate.getComentario(), null);
        comentario.setVisible(comentarioUpdate.isVisible());
        if (StringUtils.isBlank(comentario.getComentario())) {
            throw new UserDataInvalidException("Error en los datos enviados, intente de nuevo");
        }
        return comentario;
    }
    
    public List<Comentario> seleccionarTodosLosComentarios(int videojuego_id) throws SQLException {
        ComentarioDB comentarioDB = new ComentarioDB();
        return comentarioDB.seleccionarTodo(videojuego_id);
    }
    
    public List<Comentario> seleccionarTodosLosComentariosVisibles(int videojuego_id) throws SQLException {
        ComentarioDB comentarioDB = new ComentarioDB();
        return comentarioDB.seleccionarSoloVisible(videojuego_id);
    }
    
    public Comentario seleccionarUnComentario(int comentario_id) throws SQLException, UserDataInvalidException {
        ComentarioDB comentarioDB = new ComentarioDB();
        Optional<Comentario> comentarioOptional = comentarioDB.seleccionarPorParametro(comentario_id);
        if (comentarioOptional.isEmpty()) {
            throw new UserDataInvalidException("El comentario seleccionado no existe");
        }
        return comentarioOptional.get();
    }
    
    public Comentario seleccionarUnComentarioVisible(int comentario_id) throws SQLException, UserDataInvalidException {
        ComentarioDB comentarioDB = new ComentarioDB();
        Optional<Comentario> comentarioOptional = comentarioDB.seleccionarPorParametroVisible(comentario_id);
        if (comentarioOptional.isEmpty()) {
            throw new UserDataInvalidException("El comentario seleccionado no existe");
        }
        return comentarioOptional.get();
    }
    
    public void eliminarComentario(int comentario_id) throws SQLException, UserDataInvalidException {
        ComentarioDB comentarioDB = new ComentarioDB();
        Optional<Comentario> comentarioOptional = comentarioDB.seleccionarPorParametro(comentario_id);
        if (comentarioOptional.isEmpty()) {
            throw new UserDataInvalidException("El comentario a eliminar no existe");
        }
        comentarioDB.eliminar(comentario_id);
    }
}
