/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.banner;

import ipc2_vaqueras.vaquerasdipc2.db.banner.BannerDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.banner.BannerRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.banner.BannerUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.banner.Banner;
import ipc2_vaqueras.vaquerasdipc2.services.calificacion.CalificacionVideojuegoService;
import ipc2_vaqueras.vaquerasdipc2.services.categoria.videojuego.CategoriaVideojuegoService;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author helder
 */
public class BannerService {

    public void insertarBanner(BannerRequest bannerRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Banner banner = extraerBanner(bannerRequest);
        BannerDB bannerDB = new BannerDB();
        if (bannerDB.validarUnicoJuegoEnBanner(banner.getVideojuego_id())) {
            throw new EntityAlreadyExistsException("El videojuego que desea agregar ya esta en el listado");
        }
        bannerDB.insertar(banner);
    }

    private Banner extraerBanner(BannerRequest bannerRequest) throws UserDataInvalidException {
        Banner banner = new Banner(bannerRequest.getVideojuego_id(), true);
        if (!banner.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return banner;
    }
    
    public void actualizarBanner(BannerUpdate bannerUpdate, int banner_id) throws SQLException, UserDataInvalidException {
        BannerDB bannerDB = new BannerDB();
        if (!bannerDB.validarUnicoJuegoEnBannerPorId(banner_id)) {
            throw new UserDataInvalidException("No se puedo encontrar banner a actualizar");
        }
        Banner banner = new Banner(0,bannerUpdate.isEstado());
        banner.setBanner_id(banner_id);
        bannerDB.actualizar(banner);
    }

    public List<Banner> seleccionarTodos() throws SQLException {
        BannerDB bannerDB = new BannerDB();
        return calcularPuntaje(bannerDB.seleccionar());
    }
    
    public List<Banner> seleccionarTodosHabilitados() throws SQLException {
        BannerDB bannerDB = new BannerDB();
        return calcularPuntaje(bannerDB.seleccionarHabilitados());
    }
    
    public void eliminarBanner(int banner_id) throws SQLException, UserDataInvalidException {
        BannerDB bannerDB = new BannerDB();
        if (!bannerDB.validarUnicoJuegoEnBannerPorId(banner_id)) {
            throw new UserDataInvalidException("No se encontro el banner a eliminar");
        }
        bannerDB.eliminar(banner_id);
    }
    
    private List<Banner> calcularPuntaje(List<Banner> banners) throws SQLException {
        CalificacionVideojuegoService calificacionVideojuegoService = new CalificacionVideojuegoService();
        double c = calificacionVideojuegoService.promedioDeCalificacionGeneral();
        int m = 10;
        
        for (Banner banner: banners) {
            double porcentaje = calcularPorcentaje(banner.getRating_promedio(), banner.getTotal(), c, m);
            banner.setPuntaje(porcentaje);
            banner = obtenertodaSuCategoria(banner);
        }
        banners.sort(Comparator.comparing(Banner::getPuntaje).reversed());
        return banners;
    }
    
    private double calcularPorcentaje(double r, int v, double c, int m) {
        return ((double) v / (v + m)) * r
                + ((double) m / (v + m)) * c;
    }
    
    private Banner obtenertodaSuCategoria (Banner banner) throws SQLException {
        CategoriaVideojuegoService categoriaVideojuegoService = new CategoriaVideojuegoService();
        banner.setCategorias(categoriaVideojuegoService.obtenerLasCategoriasDeUnVideojuego(banner.getVideojuego_id()));
        return banner;
    }
}
