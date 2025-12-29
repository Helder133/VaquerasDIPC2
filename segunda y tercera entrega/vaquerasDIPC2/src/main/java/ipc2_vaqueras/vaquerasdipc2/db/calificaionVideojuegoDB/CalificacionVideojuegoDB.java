/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.calificaionVideojuegoDB;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.clasificacionVideojuego.CalificacionVideojuego;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CalificacionVideojuegoDB implements CRUD<CalificacionVideojuego> {

    private final static String INSERTAR_CALIFICACION = "INSERT INTO calificacion_videojuego (usuario_id, videojuego_id, calificacion) VALUES (?,?,?)";
    private final static String OBTENER_CALIFICACION_USUARIO_VIDEOJUEGO = "SELECT * FROM calificacion_videojuego WHERE videojuego_id = ? AND usuario_id = ?";
    private final static String ACTUALIZAR_CALIFICACION = "UPDATE calificacion_videojuego SET calificacion = ? WHERE calificacion_id = ?";
    private final static String PROMEDIO_DE_CALIFICACION_GLOBAL = "SELECT AVG(calificacion) AS promedio_global FROM calificacion_videojuego";
    private final static String VALIDAR_QUE_EXISTA_CALIFICACION_A_ACTUALIZAR = "SELECT * FROM calificacion_videojuego WHERE calificacion_id = ?";
    //va en la base de datos de videojuego
    //private final static String OBTENER_LOS_MEJORES_VIDEOJUEGOS = "select v.videojuego_id, v.nombre, AVG(c.calificacion) as rating_promedio, count(c.calificacion_id) as total_votos from videojuego v left join calificacion_videojuego c on v.videojuego_id = c.videojuego_id group by v.videojuego_id";

    public Optional<Double> promedioGlobal() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement promedioG = connection.prepareStatement(PROMEDIO_DE_CALIFICACION_GLOBAL)) {
            ResultSet resultSet = promedioG.executeQuery();
            if (resultSet.next()) {
                double promedio = resultSet.getDouble("promedio_global");
                return Optional.of(promedio);
            }
            return Optional.empty();
        }
    }

    @Override
    public void insertar(CalificacionVideojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_CALIFICACION)) {
            insert.setInt(1, t.getUsuario_id());
            insert.setInt(2, t.getVideojuego_id());
            insert.setFloat(3, t.getCalificacion());
            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(CalificacionVideojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CALIFICACION)) {
            update.setFloat(1, t.getCalificacion());
            update.setInt(2, t.getCalificacion_id());
            update.executeUpdate();
        }
    }

    public Optional<CalificacionVideojuego> obtenerCalificacionDeUsuarioAVideojuego(int videojuego_id, int usuario_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_CALIFICACION_USUARIO_VIDEOJUEGO)) {
            select.setInt(1, videojuego_id);
            select.setInt(2, usuario_id);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                CalificacionVideojuego calificacionVideojuego = new CalificacionVideojuego(resultSet.getInt("usuario_id"), resultSet.getInt("videojuego_id"), resultSet.getFloat("calificacion"));
                calificacionVideojuego.setCalificacion_id(resultSet.getInt("calificacion_id"));
                return Optional.of(calificacionVideojuego);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<CalificacionVideojuego> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<CalificacionVideojuego> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(VALIDAR_QUE_EXISTA_CALIFICACION_A_ACTUALIZAR)) {
            select.setInt(1, t);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                CalificacionVideojuego calificacionVideojuego = new CalificacionVideojuego(resultSet.getInt("usuario_id"), resultSet.getInt("videojuego_id"), resultSet.getFloat("calificacion"));
                calificacionVideojuego.setVideojuego_id(resultSet.getInt("calificacion_id"));
                return Optional.of(calificacionVideojuego);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<CalificacionVideojuego> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {

    }

}
