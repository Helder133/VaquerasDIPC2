/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.bibliotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego.BibliotecaVideojuego;
import java.sql.Connection;
import java.sql.Date;
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
public class BibliotecaVideojuegoDB implements CRUD<BibliotecaVideojuego> {

    private final static String INSERTAR_VIDEOJUEGO_EN_BIBLIOTECA = "INSERT INTO biblioteca_videojuego (videojuego_id, usuario_id, fecha, estado_instalacion) VALUES (?,?,?,?)";
    private final static String ACTUALIZAR_ESTADO_DE_DESCARGA = "UPDATE biblioteca_videojuego SET estado_instalacion = ? WHERE biblioteca_id = ?";
    private final static String SELECCIONAR_VIDEOJUEGO_POR_CONINCIDENCIA_DE_NOMBRE_DE_UN_USUARIO = "select b.biblioteca_id, b.estado_instalacion, b.videojuego_id, b.usuario_id, b.fecha, v.empresa_id, v.nombre as nombre_videojuego, v.imagen, v.descripcion, e.nombre as nombre_empresa, coalesce(c.rating_promedio, 0) as rating_promedio, coalesce(c.total_votos, 0) as total_votos from biblioteca_videojuego b join videojuego v on b.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id left join (select videojuego_id, avg(calificacion) as rating_promedio, count(*) as total_votos from calificacion_videojuego group by videojuego_id) c on c.videojuego_id = v.videojuego_id where b.usuario_id = ? and v.nombre like ?";
    private final static String SELECCIONAR_TODOS_LOS_VIDEOJEUGOS_DE_UN_USUARIO = "select b.biblioteca_id, b.estado_instalacion, b.videojuego_id, b.usuario_id, b.fecha, v.empresa_id, v.nombre as nombre_videojuego, v.imagen, v.descripcion, e.nombre as nombre_empresa, coalesce(c.rating_promedio, 0) as rating_promedio, coalesce(c.total_votos, 0) as total_votos from biblioteca_videojuego b join videojuego v on b.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id left join (select videojuego_id, avg(calificacion) as rating_promedio, count(*) as total_votos from calificacion_videojuego group by videojuego_id) c on c.videojuego_id = v.videojuego_id where b.usuario_id = ?;";
    //private final static String SELECCIONAR_UN_VIDEOJUEGO_EN_ESPECIFICO_DE_UN_USUARIO = "";

    private final static String VALIDAR_QUE_NO_TENGA_EL_VIDEOJUEGO = "SELECT * FROM biblioteca_videojuego WHERE usuario_id = ? AND videojuego_id = ?";

    //funcion con transaccion
    public boolean validarJuegoExistente(int usuario_id, int videojuego_id, Connection connection) throws SQLException {
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_QUE_NO_TENGA_EL_VIDEOJUEGO)) {
            validar.setInt(1, usuario_id);
            validar.setInt(2, videojuego_id);
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }

    public boolean validarJuegoExistente(int usuario_id, int videojuego_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_QUE_NO_TENGA_EL_VIDEOJUEGO)) {
            validar.setInt(1, usuario_id);
            validar.setInt(2, videojuego_id);
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }
    
    //metodo con transacion
    public void insertar(BibliotecaVideojuego t, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_VIDEOJUEGO_EN_BIBLIOTECA)) {
            insert.setInt(1, t.getVideojuego_id());
            insert.setInt(2, t.getUsuario_id());
            insert.setDate(3, Date.valueOf(t.getFecha()));
            insert.setBoolean(4, t.isEstado_instalacion());

            insert.executeUpdate();
        }
    }

    @Override
    public void insertar(BibliotecaVideojuego t) throws SQLException {
    }

    @Override
    public void actualizar(BibliotecaVideojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ESTADO_DE_DESCARGA)) {
            update.setBoolean(1, t.isEstado_instalacion());
            update.setInt(2, t.getBiblioteca_id());

            update.executeUpdate();
        }
    }

    public List<BibliotecaVideojuego> seleccionar(int usuario_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<BibliotecaVideojuego> bibliotecaVideojuegos = new ArrayList<>();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODOS_LOS_VIDEOJEUGOS_DE_UN_USUARIO)) {
            select.setInt(1, usuario_id);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                bibliotecaVideojuegos.add(extraerBiblioteca(resultSet));
            }
            return bibliotecaVideojuegos;
        }
    }

    public List<BibliotecaVideojuego> seleccionar(int usuario_id, String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<BibliotecaVideojuego> bibliotecaVideojuegos = new ArrayList<>();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_VIDEOJUEGO_POR_CONINCIDENCIA_DE_NOMBRE_DE_UN_USUARIO)) {
            select.setInt(1, usuario_id);
            select.setString(2, "%" + nombre + "%");
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                bibliotecaVideojuegos.add(extraerBiblioteca(resultSet));
            }
            return bibliotecaVideojuegos;
        }
    }
    
    @Override
    public List<BibliotecaVideojuego> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<BibliotecaVideojuego> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BibliotecaVideojuego> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {

    }

    private BibliotecaVideojuego extraerBiblioteca(ResultSet resultSet) throws SQLException {
        BibliotecaVideojuego bibliotecaVideojuego = new BibliotecaVideojuego(resultSet.getInt("videojuego_id"),
                resultSet.getInt("usuario_id"),
                resultSet.getDate("fecha").toLocalDate(),
                resultSet.getBoolean("estado_instalacion"));
        bibliotecaVideojuego.setBiblioteca_id(resultSet.getInt("biblioteca_id"));
        bibliotecaVideojuego.setNombreEmpresa(resultSet.getString("nombre_empresa"));
        bibliotecaVideojuego.setNombreVideojuego(resultSet.getString("nombre_videojuego"));
        bibliotecaVideojuego.setDescripcion(resultSet.getString("descripcion"));
        bibliotecaVideojuego.setImagen(resultSet.getBytes("imagen"));
        bibliotecaVideojuego.setRating_promedio(resultSet.getDouble("rating_promedio"));
        bibliotecaVideojuego.setTotal(resultSet.getInt("total_votos"));
        
        return bibliotecaVideojuego;
    }

}
