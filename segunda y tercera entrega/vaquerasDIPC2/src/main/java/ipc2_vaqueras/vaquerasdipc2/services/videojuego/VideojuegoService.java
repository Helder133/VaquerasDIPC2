/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.videojuego;

import ipc2_vaqueras.vaquerasdipc2.db.videojuego.VideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
import ipc2_vaqueras.vaquerasdipc2.services.empresa.EmpresaService;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class VideojuegoService {

    public void crearVideojuego(Videojuego videojuego) throws SQLException, UserDataInvalidException {
        if (!videojuego.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        EmpresaService empresaService = new EmpresaService();
        empresaService.seleccionarEmpresaPorParametro(videojuego.getEmpresa_id());
        
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        videojuegoDB.insertar(videojuego);
    }

    public void actualizarVideojuego(Videojuego videojuego) throws SQLException, UserDataInvalidException {
        if (!validarDatosActualizado(videojuego)) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        if (!videojuegoDB.ExisteVideojuego(videojuego.getVideojuego_id())) {
            throw new UserDataInvalidException("El videojuego ha actualizar no existe");
        }
        videojuegoDB.actualizar(videojuego);
    }

    public List<Videojuego> obtenerTodosLosVideojuegos() throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return videojuegoDB.seleccionar();
    }

    public List<Videojuego> obtenerTodosLosVideojuegosDeUnaEmpresa(int empresa_id) throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return videojuegoDB.seleccionarPorEmpresa(empresa_id);
    }

    public List<Videojuego> obtenerVideojuegosPorParametro(String nombre) throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return videojuegoDB.seleccionarPorParametro(nombre);
    }

    public Videojuego obtenerVideojuegosPorParametro(int id) throws SQLException, UserDataInvalidException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        Optional<Videojuego> videoOptional = videojuegoDB.seleccionarPorParametro(id);
        if (videoOptional.isEmpty()) {
            throw new UserDataInvalidException("No se puedo encontrar el videojuego solicitado");
        }
        return videoOptional.get();
    }

    private boolean validarDatosActualizado(Videojuego videojuego) {
        return StringUtils.isNotBlank(videojuego.getNombre())
                && videojuego.getPrecio() > 0
                && StringUtils.isNotBlank(videojuego.getRecurso_minimo())
                && videojuego.getEdad_minima() > 0;
    }
}
