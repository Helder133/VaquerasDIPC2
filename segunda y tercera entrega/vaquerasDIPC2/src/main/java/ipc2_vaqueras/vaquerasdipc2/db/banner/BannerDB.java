/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.banner;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.banner.Banner;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.EnumClasificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class BannerDB implements CRUD<Banner> {

    private final static String INSERTAR_BANNER = "INSERT INTO banner (videojuego_id, estado) VALUES (?,?)";
    private final static String SELECCIONAR_TODOS_LOS_BANNER = "select  b.banner_id, b.estado, b.videojuego_id, v.empresa_id, v.nombre as nombre_videojuego, v.precio, v.recurso_minimo, v.fecha, v.imagen, v.descripcion, v.clasificacion, e.nombre as nombre_empresa, coalesce(c.rating_promedio, 0) as rating_promedio, coalesce(c.total_votos, 0) as total_votos from banner b join videojuego v on b.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id left join (select videojuego_id, avg(calificacion) as rating_promedio, count(*) as total_votos from calificacion_videojuego group by videojuego_id) c on c.videojuego_id = v.videojuego_id";
    private final static String SELECCIONAR_TODOS_LOS_BANNER_VISBLES = "select  b.banner_id, b.estado, b.videojuego_id, v.empresa_id, v.nombre as nombre_videojuego, v.precio, v.recurso_minimo, v.fecha, v.imagen, v.descripcion, v.clasificacion, e.nombre as nombre_empresa, coalesce(c.rating_promedio, 0) as rating_promedio, coalesce(c.total_votos, 0) as total_votos from banner b join videojuego v on b.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id left join (select videojuego_id, avg(calificacion) as rating_promedio, count(*) as total_votos from calificacion_videojuego group by videojuego_id) c on c.videojuego_id = v.videojuego_id where b.estado = 1";
    private final static String ACTUALIZAR_BANNER = "UPDATE banner SET estado = ? WHERE banner_id = ?";
    private final static String ELIMINAR_BANNER = "DELETE FROM banner WHERE banner_id = ?";

    private final static String VALIDAR_UNICO_JUEGO = "SELECT * FROM banner WHERE videojuego_id = ?";
    private final static String VALIDAR_UNICO_JUEGO_POR_ID = "SELECT * FROM banner WHERE banner_id = ?";

    public boolean validarUnicoJuegoEnBanner(int videojuego_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_UNICO_JUEGO)) {
            validar.setInt(1, videojuego_id);
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }

    public boolean validarUnicoJuegoEnBannerPorId(int banner_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_UNICO_JUEGO_POR_ID)) {
            validar.setInt(1, banner_id);
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(Banner t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insertar = connection.prepareStatement(INSERTAR_BANNER)) {
            insertar.setInt(1, t.getVideojuego_id());
            insertar.setBoolean(2, t.isEstado());
            insertar.executeUpdate();
        }
    }

    @Override
    public void actualizar(Banner t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_BANNER)) {
            update.setBoolean(1, t.isEstado());
            update.setInt(2, t.getBanner_id());
            update.executeUpdate();
        }
    }

    public List<Banner> seleccionarHabilitados() throws SQLException {
        List<Banner> banners = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODOS_LOS_BANNER_VISBLES)) {
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                banners.add(extraerDatos(resultSet));
            }
            return banners;
        }
    }
    
    @Override
    public List<Banner> seleccionar() throws SQLException {
        List<Banner> banners = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODOS_LOS_BANNER)) {
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                banners.add(extraerDatos(resultSet));
            }
            return banners;
        }
    }

    @Override
    public Optional<Banner> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Banner> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_BANNER)) {
            delete.setInt(1, t);
            delete.executeUpdate();        }
    }

    private Banner extraerDatos(ResultSet resultSet) throws SQLException {
        Banner banner = new Banner(resultSet.getInt("videojuego_id"), resultSet.getBoolean("estado"));
        banner.setBanner_id(resultSet.getInt("banner_id"));
        banner.setEmpresa_id(resultSet.getInt("empresa_id"));
        banner.setNombreVideojuego(resultSet.getString("nombre_videojuego"));
        banner.setPrecio(resultSet.getFloat("precio"));
        banner.setRecursoMinimo(resultSet.getString("recurso_minimo"));
        banner.setFecha(resultSet.getDate("fecha").toLocalDate());
        banner.setImagen(resultSet.getBytes("imagen"));
        banner.setDescripcion(resultSet.getString("descripcion"));
        banner.setClasificacion(EnumClasificacion.valueOf(resultSet.getString("clasificacion")));
        banner.setNombreEmpresa(resultSet.getString("nombre_empresa"));
        banner.setRating_promedio(resultSet.getDouble("rating_promedio"));
        banner.setTotal(resultSet.getInt("total_votos"));
        
        return banner;
    }

}
