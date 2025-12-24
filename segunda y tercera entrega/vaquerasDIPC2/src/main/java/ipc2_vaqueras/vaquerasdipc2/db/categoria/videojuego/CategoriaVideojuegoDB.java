/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.categoria.videojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
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
public class CategoriaVideojuegoDB implements CRUD<CategoriaVideojuego> {

    private final static String INSERTAR_CATEGORIA_DE_UN_VIDEOJUEGO = "INSERT INTO categoria_videojuego (videojuego_id, categoria_id) VALUES (?,?)";
    private final static String SELECCIONAR_LAS_CATEGORIAS_DE_UN_VIDEOJUEGO = "select c.nombre, cv.categoria_id, cv.videojuego_id from categoria c join categoria_videojuego cv on c.categoria_id = cv.categoria_id where cv.videojuego_id = ?";
    private final static String ELIMNAR_CATEGORIAVIDEOJUEGO = "DELETE FROM categoria_videojuego WHERE videojuego_id = ? AND categoria_id = ?";
    private final static String VALIDAR_QUE_YA_EXISTA_CATEGORIA_DE_VIDEOJUEGO = "SELECT * FROM categoria_videojuego WHERE videojuego_id = ? AND categoria_id = ?";
    //private final static String O = "";
    
    public boolean existeCategoriaVideojuego(int videojuego_id, int categoria_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(VALIDAR_QUE_YA_EXISTA_CATEGORIA_DE_VIDEOJUEGO)) {
            select.setInt(1, videojuego_id);
            select.setInt(2, categoria_id);
            
            ResultSet resultSet = select.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(CategoriaVideojuego t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_CATEGORIA_DE_UN_VIDEOJUEGO)) {
            insert.setInt(1, t.getVideojuego_id());
            insert.setInt(2, t.getCategoria_id());
            
            insert.executeUpdate();
        }
    }
    
    public List<CategoriaVideojuego> obtenerLasCategoriasDeUnVideojuego(int videojuego_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<CategoriaVideojuego> categoriaVideojuegos = new ArrayList<>();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_LAS_CATEGORIAS_DE_UN_VIDEOJUEGO)) {
            select.setInt(1, videojuego_id);
            
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                CategoriaVideojuego categoriaVideojuego = new CategoriaVideojuego(
                        resultSet.getInt("videojuego_id"), 
                        resultSet.getString("nombre"));
                categoriaVideojuego.setCategoria_id(resultSet.getInt("categoria_id"));
                categoriaVideojuegos.add(categoriaVideojuego);
            }
            return categoriaVideojuegos;
        }
    }
    
    public void eliminar(int videojuego_id, int categoria_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMNAR_CATEGORIAVIDEOJUEGO)){
            delete.setInt(1, videojuego_id);
            delete.setInt(2, categoria_id);
            
            delete.executeUpdate();
        }
    }
    
    @Override
    public void actualizar(CategoriaVideojuego t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CategoriaVideojuego> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<CategoriaVideojuego> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CategoriaVideojuego> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {

    }

}
