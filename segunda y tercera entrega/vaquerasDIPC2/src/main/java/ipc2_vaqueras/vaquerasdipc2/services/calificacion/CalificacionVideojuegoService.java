/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.calificacion;

import ipc2_vaqueras.vaquerasdipc2.db.calificaionVideojuegoDB.CalificacionVideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.calificacionVideojuego.CalificacionVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.calificacionVideojuego.CalificacionVideojuegoUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.clasificacionVideojuego.CalificacionVideojuego;
import ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego.BibliotecaVideojuegoService;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CalificacionVideojuegoService {

    public void calificarVideojuego(CalificacionVideojuegoRequest calificacionVideojuegoRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        CalificacionVideojuego calificacionVideojuego = extraerDatos(calificacionVideojuegoRequest);
        BibliotecaVideojuegoService bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
        if (!bibliotecaVideojuegoService.validarQueTengaElVideojuego(calificacionVideojuego.getUsuario_id(), calificacionVideojuego.getVideojuego_id())) {
            throw new UserDataInvalidException("Necesita tener comprado el videojuego, antes de calificarlo");
        }
        CalificacionVideojuegoDB calificacionVideojuegoDB = new CalificacionVideojuegoDB();
        if (calificacionVideojuegoDB.obtenerCalificacionDeUsuarioAVideojuego(calificacionVideojuego.getVideojuego_id(), calificacionVideojuego.getUsuario_id()).isPresent()) {
            throw new EntityAlreadyExistsException("ya hay una calificacion registrada en el videojuego, no puede insertar otra, solo actualizar");
        }
        calificacionVideojuegoDB.insertar(calificacionVideojuego);
    }

    private CalificacionVideojuego extraerDatos(CalificacionVideojuegoRequest calificacionVideojuegoRequest) throws UserDataInvalidException {
        CalificacionVideojuego calificacionVideojuego = new CalificacionVideojuego(calificacionVideojuegoRequest.getUsuario_id(), calificacionVideojuegoRequest.getVideojuego_id(), calificacionVideojuegoRequest.getCalificacion());
        if (!calificacionVideojuego.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return calificacionVideojuego;
    }

    public void actualizarCalificacion(CalificacionVideojuegoUpdate calificacionVideojuegoUpdate, int calificacion_id) throws SQLException, UserDataInvalidException {
        CalificacionVideojuego calificacionVideojuego = extraerDatos(calificacionVideojuegoUpdate);
        calificacionVideojuego.setCalificacion_id(calificacion_id);
        CalificacionVideojuegoDB calificacionVideojuegoDB = new CalificacionVideojuegoDB();
        if (calificacionVideojuegoDB.seleccionarPorParametro(calificacion_id).isEmpty()) {
            throw new UserDataInvalidException("La calificacion que desea actualiazar no se encontro, vuelva a intentar");
        }
        calificacionVideojuegoDB.actualizar(calificacionVideojuego);
    }

    private CalificacionVideojuego extraerDatos(CalificacionVideojuegoUpdate calificacionVideojuegoUpdate) throws UserDataInvalidException {
        CalificacionVideojuego calificacionVideojuego = new CalificacionVideojuego(0, 0, calificacionVideojuegoUpdate.getCalificacion());
        if (!(calificacionVideojuegoUpdate.getCalificacion() >= 1 && calificacionVideojuegoUpdate.getCalificacion() <= 5)) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return calificacionVideojuego;
    }

    public Optional<CalificacionVideojuego> obtenerCalificacionDeUsuarioAVideojuego(int usuario_id, int videojuego_id) throws SQLException {
        CalificacionVideojuegoDB calificacionVideojuegoDB = new CalificacionVideojuegoDB();
        return calificacionVideojuegoDB.obtenerCalificacionDeUsuarioAVideojuego(videojuego_id, usuario_id);
    }

    public double promedioDeCalificacionGeneral() throws SQLException {
        CalificacionVideojuegoDB calificacionVideojuegoDB = new CalificacionVideojuegoDB();
        return calificacionVideojuegoDB.promedioGlobal().orElse(3.0);
    }
}
