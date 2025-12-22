/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.multimedia;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.multimedia.Multimedia;
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
public class MultimediaDB implements CRUD<Multimedia> {

    private final static String INSERTAR_MULTIMEDIA = "INSERT INTO multimedia (videojuego_id, imagen) VALUES (?, ?)";
    private final static String EDITAR_MULTIMEDIA = "UPDATE multimedia SET imagen = ? WHERE multimedia_id = ?";
    private final static String OBTENER_TODAS_LAS_MULTIMEDIAS = "SELECT * FROM multimedia";
    private final static String OBTENER_MULTIMEDIA_POR_ID = "SELECT * FROM multimedia WHERE multimedia_id = ?";
    private final static String OBTENER_MULTIMEDIA_DE_UN_VIDEOJUEGO = "SELECT * FROM multimedia WHERE videojuego_id = ?";
    private final static String ELIMINAR_MULTIMEDIA = "DELETE FROM multimedia WHERE multimedia_id = ?";

    public List<Multimedia> seleccionarMultimediasDeUnVideojuego(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Multimedia> multimedias = new ArrayList<>();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_MULTIMEDIA_DE_UN_VIDEOJUEGO)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                Multimedia multimedia = new Multimedia(
                        resultSet.getInt("videojuego_id"),
                        resultSet.getBytes("imagen")
                );
                multimedia.setMultimedia_id(resultSet.getInt("multimedia_id"));
                multimedias.add(multimedia);
            }
            return multimedias;
        }
    }

    @Override
    public void insertar(Multimedia t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_MULTIMEDIA)) {
            insert.setInt(1, t.getVideojuego_id());
            insert.setBytes(2, t.getImagen());

            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(Multimedia t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(EDITAR_MULTIMEDIA)) {
            update.setBytes(1, t.getImagen());
            update.setInt(2, t.getMultimedia_id());

            update.executeUpdate();
        }
    }

    @Override
    public List<Multimedia> seleccionar() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Multimedia> multimedias = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODAS_LAS_MULTIMEDIAS)) {
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador < max) {
                Multimedia multimedia = new Multimedia(
                        resultSet.getInt("videojuego_id"),
                        resultSet.getBytes("imagen")
                );
                multimedia.setMultimedia_id(resultSet.getInt("multimedia_id"));
                multimedias.add(multimedia);
            }
            return multimedias;
        }
    }

    @Override
    public Optional<Multimedia> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_MULTIMEDIA_POR_ID)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Multimedia multimedia = new Multimedia(
                        resultSet.getInt("videojuego_id"),
                        resultSet.getBytes("imagen"));
                multimedia.setMultimedia_id(resultSet.getInt("multimedia_id"));
                return Optional.of(multimedia);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Multimedia> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_MULTIMEDIA)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }

}
