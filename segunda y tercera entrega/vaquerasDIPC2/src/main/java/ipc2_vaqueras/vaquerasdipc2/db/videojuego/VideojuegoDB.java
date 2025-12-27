/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.videojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
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
public class VideojuegoDB implements CRUD<Videojuego> {

    private final static String INSERTAR_VIDEOJUEGO = "INSERT INTO videojuego (empresa_id, nombre, precio, recurso_minimo, edad_minima, estado, fecha, imagen, descripcion) VALUES (?,?,?,?,?,?,?,?,?)";
    private final static String EDITAR_VIDEOJUEGO = "UPDATE videojuego SET nombre = ?, precio = ?, recurso_minimo = ?, edad_minima = ?, estado = ?, imagen = ?, descripcion = ? WHERE videojuego_id = ?";
    private final static String SELECCIONAR_TODOS_LOS_VIDEOJUEGOS = "select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado, v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id";
    private final static String SELECCIONAR_TODOS_LOS_VIDEOJUEGOS_DE_UNA_EMPRESA = "select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado, v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id WHERE v.empresa_id = ?";
    private final static String SELECCIONAR_VIDEOJUEGO_POR_STRING = "select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado, v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id WHERE v.nombre LIKE ?";
    private final static String SELECCIONAR_VIDEOJUEGO_POR_INT = "select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado, v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id WHERE v.videojuego_id = ?";
    private final static String SELECCIONAR_VIDEOJUEGO_NO_COMPRADO = "select v.videojuego_id, v.empresa_id, v.nombre, v.precio, v.recurso_minimo, v.edad_minima, v.estado, v.fecha, v.imagen, v.descripcion, e.nombre as nombre_empresa from videojuego v join empresa e on v.empresa_id = e.empresa_id where v.estado = 1 and not exists (select 1 from biblioteca_videojuego b where b.videojuego_id = v.videojuego_id and b.usuario_id = ?)";
//private final static String ELIMINAR_VIDEOJUEGO="DELETE FROM videojuego WHERE videojuego_id = ?";

    public boolean ExisteVideojuego(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_VIDEOJUEGO_POR_INT)) {
            select.setInt(1, t);
            ResultSet resultSet = select.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public void insertar(Videojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_VIDEOJUEGO)) {
            insert.setInt(1, t.getEmpresa_id());
            insert.setString(2, t.getNombre());
            insert.setFloat(3, t.getPrecio());
            insert.setString(4, t.getRecurso_minimo());
            insert.setInt(5, t.getEdad_minima());
            insert.setBoolean(6, t.isEstado());
            insert.setDate(7, Date.valueOf(t.getFecha()));
            insert.setBytes(8, t.getImagen());
            insert.setString(9, t.getDescripcion());

            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(Videojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(EDITAR_VIDEOJUEGO)) {
            insert.setString(1, t.getNombre());
            insert.setFloat(2, t.getPrecio());
            insert.setString(3, t.getRecurso_minimo());
            insert.setInt(4, t.getEdad_minima());
            insert.setBoolean(5, t.isEstado());
            insert.setBytes(6, t.getImagen());
            insert.setString(7, t.getDescripcion());
            insert.setInt(8, t.getVideojuego_id());

            insert.executeUpdate();
        }
    }

    @Override
    public List<Videojuego> seleccionar() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Videojuego> videojuegos = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODOS_LOS_VIDEOJUEGOS)) {
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador < max) {
                contador++;
                Videojuego videojuego = new Videojuego(
                        resultSet.getInt("empresa_id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getString("recurso_minimo"),
                        resultSet.getInt("edad_minima"),
                        resultSet.getBoolean("estado"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getBytes("imagen"),
                        resultSet.getString("descripcion")
                );
                videojuego.setVideojuego_id(resultSet.getInt("videojuego_id"));
                videojuego.setNombre_empresa(resultSet.getString("nombre_empresa"));
                videojuegos.add(videojuego);
            }
            return videojuegos;
        }
    }
    
    public List<Videojuego> seleccionar(int usuario_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Videojuego> videojuegos = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_VIDEOJUEGO_NO_COMPRADO)) {
            select.setInt(1, usuario_id);
            
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador < max) {
                contador++;
                Videojuego videojuego = new Videojuego(
                        resultSet.getInt("empresa_id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getString("recurso_minimo"),
                        resultSet.getInt("edad_minima"),
                        resultSet.getBoolean("estado"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getBytes("imagen"),
                        resultSet.getString("descripcion")
                );
                videojuego.setVideojuego_id(resultSet.getInt("videojuego_id"));
                videojuego.setNombre_empresa(resultSet.getString("nombre_empresa"));
                videojuegos.add(videojuego);
            }
            return videojuegos;
        }
    }

    @Override
    public Optional<Videojuego> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_VIDEOJUEGO_POR_INT)) {
            select.setInt(1, t);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Videojuego videojuego = new Videojuego(
                        resultSet.getInt("empresa_id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getString("recurso_minimo"),
                        resultSet.getInt("edad_minima"),
                        resultSet.getBoolean("estado"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getBytes("imagen"),
                        resultSet.getString("descripcion")
                );
                videojuego.setVideojuego_id(resultSet.getInt("videojuego_id"));
                videojuego.setNombre_empresa(resultSet.getString("nombre_empresa"));
                return Optional.of(videojuego);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Videojuego> seleccionarPorParametro(String t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Videojuego> videojuegos = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_VIDEOJUEGO_POR_STRING)) {
            select.setString(1, "%" + t + "%");

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador < max) {
                contador++;
                Videojuego videojuego = new Videojuego(
                        resultSet.getInt("empresa_id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getString("recurso_minimo"),
                        resultSet.getInt("edad_minima"),
                        resultSet.getBoolean("estado"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getBytes("imagen"),
                        resultSet.getString("descripcion")
                );
                videojuego.setVideojuego_id(resultSet.getInt("videojuego_id"));
                videojuego.setNombre_empresa(resultSet.getString("nombre_empresa"));
                videojuegos.add(videojuego);
            }
            return videojuegos;
        }
    }

    public List<Videojuego> seleccionarPorEmpresa(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Videojuego> videojuegos = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODOS_LOS_VIDEOJUEGOS_DE_UNA_EMPRESA)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador < max) {
                contador++;
                Videojuego videojuego = new Videojuego(
                        resultSet.getInt("empresa_id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getString("recurso_minimo"),
                        resultSet.getInt("edad_minima"),
                        resultSet.getBoolean("estado"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getBytes("imagen"),
                        resultSet.getString("descripcion")
                );
                videojuego.setVideojuego_id(resultSet.getInt("videojuego_id"));
                videojuego.setNombre_empresa(resultSet.getString("nombre_empresa"));
                videojuegos.add(videojuego);
            }
            return videojuegos;
        }
    }

    @Override
    public void eliminar(int t) throws SQLException {
        //no se puede eliminar los videojuegos 
    }

}
