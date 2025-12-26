/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.bibliotecaVideojuego.BibliotecaVideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.BibliotecaVideojuegoUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.ComprarVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego.BibliotecaVideojuego;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuegoService {

    public void agregarBiblioteca(ComprarVideojuegoRequest comprarVideojuegoRequest, Connection connection) throws SQLException, UserDataInvalidException {
        BibliotecaVideojuego bibliotecaVideojuego = new BibliotecaVideojuego(comprarVideojuegoRequest.getUsuario_id(), comprarVideojuegoRequest.getVideojuego_id(), comprarVideojuegoRequest.getFecha(), false);
        if (validarUnicoVideojuego(bibliotecaVideojuego, connection)) {
            throw new UserDataInvalidException("El videojuego seleccionado ya lo tiene en su biblioteca");
        }
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        bibliotecaVideojuegoDB.insertar(bibliotecaVideojuego, connection);
    }

    private boolean validarUnicoVideojuego(BibliotecaVideojuego bibliotecaVideojuego, Connection connection) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return bibliotecaVideojuegoDB.validarJuegoExistente(bibliotecaVideojuego.getUsuario_id(), bibliotecaVideojuego.getVideojuego_id(), connection);
    }
    
    public void actualizarEstadoDeActualizacion(BibliotecaVideojuegoUpdate bibliotecaVideojuegoUpdate) throws SQLException {
        BibliotecaVideojuego bibliotecaVideojuego = new BibliotecaVideojuego(0, 0, null, bibliotecaVideojuegoUpdate.isEstado_instalacion());
        bibliotecaVideojuego.setBiblioteca_id(bibliotecaVideojuegoUpdate.getBiblioteca_id());
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        bibliotecaVideojuegoDB.actualizar(bibliotecaVideojuego);
    }

    public List<BibliotecaVideojuego> obtenerLaBibliotecaDeUnUsuario(int usuario_id) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return bibliotecaVideojuegoDB.seleccionar(usuario_id);
    }

    public List<BibliotecaVideojuego> obtenerLaBibliotecaDeUnUsuarioFiltrandoPorNombre(int usuario_id, String nombre) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return bibliotecaVideojuegoDB.seleccionar(usuario_id, nombre);
    }
}
