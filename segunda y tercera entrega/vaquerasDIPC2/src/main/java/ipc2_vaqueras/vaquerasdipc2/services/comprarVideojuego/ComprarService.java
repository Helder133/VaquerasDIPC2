/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.comprarVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.db.comprarVideojuego.ComprarVideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.ComprarVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.comprarVideojuego.ComprarVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
import ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego.BibliotecaVideojuegoService;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.UsuarioService;
import ipc2_vaqueras.vaquerasdipc2.services.videojuego.VideojuegoService;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 *
 * @author helder
 */
public class ComprarService {
    
    public void comprarVideojuego(ComprarVideojuegoRequest comprarVideojuegoRequest) throws SQLException, UserDataInvalidException {
        ComprarVideojuego comprarVideojuego = extraerDatos(comprarVideojuegoRequest);
        //obteniendo el videojuego
        VideojuegoService videojuegoService = new VideojuegoService();
        Videojuego videojuego = videojuegoService.obtenerVideojuegosPorParametro(comprarVideojuego.getVideojuego_id());
        //validar si el videojuego esta disponible para comprar
        if (!videojuego.isEstado()) {
            throw new UserDataInvalidException(String.format("El juego: %s, no esta disponible para comprarse", videojuego.getNombre()));
        }
        
        //obteniendo al usuario y su cartera
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.seleccionarUsuarioPorParametro(comprarVideojuego.getUsuario_id());
        
        //validar que el usuario tenga la edad permitida para comprar el videojuego
        validarEdadPermitiva(videojuego.getEdad_minima(), usuario.getFecha_nacimiento());
        
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            
            if (validarUnicoVideojuego(comprarVideojuego, connection)) {
                throw new UserDataInvalidException("El videojuego seleccionado ya lo a comprado anteriormente");
            }
            
            //Se descuenta el pago de la cartera, y si no hay saldo suficiente, da una exception
            usuarioService.pagarCartera(usuario.getUsuario_id(), videojuego.getPrecio(), connection);
            //se agrega en la tabla de compras
            ComprarVideojuegoDB comprarVideojuegoDB = new ComprarVideojuegoDB();
            comprarVideojuegoDB.insertar(comprarVideojuego, connection);
            //se agrega a la biblioteca
            BibliotecaVideojuegoService bibliotecaVideojuegoService = new BibliotecaVideojuegoService();
            bibliotecaVideojuegoService.agregarBiblioteca(comprarVideojuegoRequest, connection);
            
            connection.commit();
        } catch (SQLException | UserDataInvalidException e) {
        if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    private ComprarVideojuego extraerDatos(ComprarVideojuegoRequest comprarVideojuegoRequest) throws SQLException, UserDataInvalidException{
        ComprarVideojuego comprarVideojuego = new ComprarVideojuego(comprarVideojuegoRequest.getVideojuego_id(), comprarVideojuegoRequest.getUsuario_id(), comprarVideojuegoRequest.getFecha());
        if (!comprarVideojuego.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados");
        }
        return comprarVideojuego;
    }
    
    private boolean validarUnicoVideojuego(ComprarVideojuego comprarVideojuego, Connection connection) throws SQLException {
        ComprarVideojuegoDB comprarVideojuegoDB = new ComprarVideojuegoDB();
        return comprarVideojuegoDB.validarUnicaCompra(comprarVideojuego.getVideojuego_id(), comprarVideojuego.getUsuario_id(), connection);
    }
    
    public List<ComprarVideojuego> obtenerTodasLasCompras() throws SQLException {
        ComprarVideojuegoDB comprarVideojuegoDB = new ComprarVideojuegoDB();
        return comprarVideojuegoDB.seleccionar();
    }

    private void validarEdadPermitiva(int edad_minima, LocalDate fecha_nacimiento)  throws UserDataInvalidException {
        LocalDate fechaActual = LocalDate.now();
        Period period = Period.between(fecha_nacimiento, fechaActual);
        
        if (period.getYears() < edad_minima) {
            throw new UserDataInvalidException("No cuenta con la edad minima requerida para comprar el juego");
        }
    }
}
