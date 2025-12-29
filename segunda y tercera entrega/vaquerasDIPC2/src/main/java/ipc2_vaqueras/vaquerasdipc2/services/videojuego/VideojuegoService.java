/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.videojuego;

import ipc2_vaqueras.vaquerasdipc2.db.videojuego.VideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.videojuego.CategoriaVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.multimedia.MultimediaUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
import ipc2_vaqueras.vaquerasdipc2.services.calificacion.CalificacionVideojuegoService;
import ipc2_vaqueras.vaquerasdipc2.services.categoria.videojuego.CategoriaVideojuegoService;
import ipc2_vaqueras.vaquerasdipc2.services.empresa.EmpresaService;
import ipc2_vaqueras.vaquerasdipc2.services.multimedia.MultimediaService;
import java.sql.SQLException;
import java.util.Comparator;
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
        return calcularPuntaje(videojuegoDB.seleccionar());
    }

    public List<Videojuego> obtenerTodosLosVideojuegosNoComprado(int usuario_id) throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return calcularPuntaje(videojuegoDB.seleccionar(usuario_id));
    }

    public List<Videojuego> obtenerTodosLosVideojuegosDeUnaEmpresa(int empresa_id) throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return calcularPuntaje(videojuegoDB.seleccionarPorEmpresa(empresa_id));
    }

    public List<Videojuego> obtenerVideojuegosPorParametro(String nombre) throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return calcularPuntaje(videojuegoDB.seleccionarPorParametro(nombre));
    }

    public Videojuego obtenerVideojuegosPorParametro(int id) throws SQLException, UserDataInvalidException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        Optional<Videojuego> videoOptional = videojuegoDB.seleccionarPorParametro(id);
        if (videoOptional.isEmpty()) {
            throw new UserDataInvalidException("No se puedo encontrar el videojuego solicitado");
        }
        return calcularPuntaje(videoOptional.get());
    }
    
    private Videojuego obtenertodaSuCategoria (Videojuego videojuego) throws SQLException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        videojuego.setCategorias(categoriaVideojuegoService.obtenerLasCategoriasDeUnVideojuego(videojuego.getVideojuego_id()));
        return videojuego;
    }
    
    public List<Videojuego> obtenerLosMejoresVideojuegosSegunComunidad() throws SQLException {
        VideojuegoDB videojuegoDB = new VideojuegoDB();
        return calcularPuntaje(videojuegoDB.obtenerLosMejoresVideojuegosSegunComunidad());
    }

    private List<Videojuego> calcularPuntaje(List<Videojuego> videojuegos) throws SQLException {
        CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
        double c = calificacionVideojuegoService.promedioDeCalificacionGeneral();
        int m = 10;

        for (Videojuego videojuego : videojuegos) {
            double porcentaje = calcularPorcentaje(videojuego.getRating_promedio(), videojuego.getTotal(), c, m);
            videojuego.setPuntaje(porcentaje);
            videojuego = obtenertodaSuCategoria(videojuego);
        }
        videojuegos.sort(Comparator.comparing(Videojuego::getPuntaje).reversed());

        return videojuegos;
    }

    private Videojuego calcularPuntaje(Videojuego videojuego) throws SQLException {
        CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
        double c = calificacionVideojuegoService.promedioDeCalificacionGeneral();
        int m = 10;
        
        double porcentaje = calcularPorcentaje(videojuego.getRating_promedio(), videojuego.getTotal(), c, m);
        videojuego.setPuntaje(porcentaje);
        videojuego = obtenertodaSuCategoria(videojuego);
        
        return videojuego;
    }

    private double calcularPorcentaje(double r, int v, double c, int m) {
        return ((double) v / (v + m)) * r
                + ((double) m / (v + m)) * c;
    }

    private boolean validarDatosActualizado(Videojuego videojuego) {
        return StringUtils.isNotBlank(videojuego.getNombre())
                && videojuego.getPrecio() > 0
                && StringUtils.isNotBlank(videojuego.getRecurso_minimo())
                && videojuego.getEdad_minima() > 0;
    }

    //Multimedia de un videojuego...
    public void crearMultimedia(MultimediaRequest multimediaRequest) throws SQLException, UserDataInvalidException {
        obtenerVideojuegosPorParametro(multimediaRequest.getVideojuego_id());

        MultimediaService multimediaService = new MultimediaService();
        multimediaService.crearMultimedia(multimediaRequest);
    }

    public void editarMultimedia(MultimediaUpdate multimediaUpdate, int id) throws SQLException, UserDataInvalidException {
        obtenerVideojuegosPorParametro(multimediaUpdate.getVideojuego_id());

        MultimediaService multimediaService = new MultimediaService();
        multimediaService.editarMultimedia(multimediaUpdate, id);
    }

    public Multimedia obtenerMultimediaPorId(int t) throws SQLException, UserDataInvalidException {
        MultimediaService multimediaService = new MultimediaService();
        return multimediaService.obtenerMultimediaPorId(t);
    }

    public List<Multimedia> obtenerMultimediasDeUnVideojuego(int t) throws SQLException, UserDataInvalidException {
        obtenerVideojuegosPorParametro(t);

        MultimediaService multimediaService = new MultimediaService();
        return multimediaService.obtenerMultimediasDeUnVideojuego(t);
    }

    public void eliminarMultimedia(int t) throws SQLException {
        MultimediaService multimediaService = new MultimediaService();
        multimediaService.eliminarMultimedia(t);
    }

    //Categorias de un videojuego
    public void crearVideojuegoCategoria(CategoriaVideojuegoRequest categoriaVideojuegoRequest) throws SQLException,
            UserDataInvalidException, EntityAlreadyExistsException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        categoriaVideojuegoService.crearCategoriaVideojuego(categoriaVideojuegoRequest);
    }

    public List<CategoriaVideojuego> obtenerCategoriasDeUnVideojuego(int videojuego_id) throws SQLException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        return categoriaVideojuegoService.obtenerLasCategoriasDeUnVideojuego(videojuego_id);
    }

    public void eliminarCategoriaDeUnVideojuego(int videojuego_id, int categoria_id) throws SQLException, UserDataInvalidException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        categoriaVideojuegoService.eliminarCategoriaVideojuego(videojuego_id, categoria_id);
    }
}
