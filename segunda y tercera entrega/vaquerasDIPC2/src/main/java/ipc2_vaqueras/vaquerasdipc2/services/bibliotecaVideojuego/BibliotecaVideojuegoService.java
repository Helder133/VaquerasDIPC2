/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.bibliotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.bibliotecaVideojuego.BibliotecaVideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.BibliotecaVideojuegoUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.compraYBibliotecaVideojuego.ComprarVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego.BibliotecaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.services.calificacion.CalificacionVideojuegoService;
import ipc2_vaqueras.vaquerasdipc2.services.categoria.videojuego.CategoriaVideojuegoService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuegoService {

    public void agregarBiblioteca(ComprarVideojuegoRequest comprarVideojuegoRequest, Connection connection) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        BibliotecaVideojuego bibliotecaVideojuego = new BibliotecaVideojuego(comprarVideojuegoRequest.getVideojuego_id(), comprarVideojuegoRequest.getUsuario_id(), comprarVideojuegoRequest.getFecha(), false);
        if (validarUnicoVideojuego(bibliotecaVideojuego, connection)) {
            throw new EntityAlreadyExistsException("El videojuego seleccionado ya lo tiene en su biblioteca");
        }
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        bibliotecaVideojuegoDB.insertar(bibliotecaVideojuego, connection);
    }

    private boolean validarUnicoVideojuego(BibliotecaVideojuego bibliotecaVideojuego, Connection connection) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return bibliotecaVideojuegoDB.validarJuegoExistente(bibliotecaVideojuego.getUsuario_id(), bibliotecaVideojuego.getVideojuego_id(), connection);
    }
    
    public boolean validarQueTengaElVideojuego(int usuario_id,int videojuego_id) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return bibliotecaVideojuegoDB.validarJuegoExistente(usuario_id, videojuego_id);
    }
    
    public void actualizarEstadoDeActualizacion(BibliotecaVideojuegoUpdate bibliotecaVideojuegoUpdate) throws SQLException {
        BibliotecaVideojuego bibliotecaVideojuego = new BibliotecaVideojuego(0, 0, null, bibliotecaVideojuegoUpdate.isEstado_instalacion());
        bibliotecaVideojuego.setBiblioteca_id(bibliotecaVideojuegoUpdate.getBiblioteca_id());
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        bibliotecaVideojuegoDB.actualizar(bibliotecaVideojuego);
    }

    public List<BibliotecaVideojuego> obtenerLaBibliotecaDeUnUsuario(int usuario_id) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return calcularPuntaje(bibliotecaVideojuegoDB.seleccionar(usuario_id));
    }

    public List<BibliotecaVideojuego> obtenerLaBibliotecaDeUnUsuarioFiltrandoPorNombre(int usuario_id, String nombre) throws SQLException {
        BibliotecaVideojuegoDB bibliotecaVideojuegoDB = new BibliotecaVideojuegoDB();
        return calcularPuntaje(bibliotecaVideojuegoDB.seleccionar(usuario_id, nombre));
    }
    
    private BibliotecaVideojuego obtenertodaSuCategoria (BibliotecaVideojuego bibliotecaVideojuego) throws SQLException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        bibliotecaVideojuego.setCategorias(categoriaVideojuegoService.obtenerLasCategoriasDeUnVideojuego(bibliotecaVideojuego.getVideojuego_id()));
        return bibliotecaVideojuego;
    }

    private List<BibliotecaVideojuego> calcularPuntaje(List<BibliotecaVideojuego> bibliotecaVideojuegos) throws SQLException {
        CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
        double c = calificacionVideojuegoService.promedioDeCalificacionGeneral();
        int m = 10;

        for (BibliotecaVideojuego bibliotecaVideojuego : bibliotecaVideojuegos) {
            double porcentaje = calcularPorcentaje(bibliotecaVideojuego.getRating_promedio(), bibliotecaVideojuego.getTotal(), c, m);
            bibliotecaVideojuego.setPuntaje(porcentaje);
            bibliotecaVideojuego = obtenertodaSuCategoria(bibliotecaVideojuego);
        }
        bibliotecaVideojuegos.sort(Comparator.comparing(BibliotecaVideojuego::getPuntaje).reversed());

        return bibliotecaVideojuegos;
    }

    private BibliotecaVideojuego calcularPuntaje(BibliotecaVideojuego bibliotecaVideojuego) throws SQLException {
        CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
        double c = calificacionVideojuegoService.promedioDeCalificacionGeneral();
        int m = 10;
        
        double porcentaje = calcularPorcentaje(bibliotecaVideojuego.getRating_promedio(), bibliotecaVideojuego.getTotal(), c, m);
        bibliotecaVideojuego.setPuntaje(porcentaje);
        bibliotecaVideojuego = obtenertodaSuCategoria(bibliotecaVideojuego);
        
        return bibliotecaVideojuego;
    }

    private double calcularPorcentaje(double r, int v, double c, int m) {
        return ((double) v / (v + m)) * r
                + ((double) m / (v + m)) * c;
    }
}
