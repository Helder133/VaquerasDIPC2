/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.categoria;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;
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
public class CategoriaDB implements CRUD<Categoria> {

    //Querys principales
    private static final String INSERTAR_CATEGORIA = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
    private static final String EDITAR_CATEGORIA = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE categoria_id = ?";
    private static final String OBTENER_TODAS_LAS_CATEGORIAS = "SELECT * FROM categoria";
    private static final String OBTENER_CATEGORIA_POR_ID = "SELECT * FROM categoria WHERE categoria_id = ?";
    private static final String OBTENER_CATEGORIA_POR_STRING = "SELECT * FROM categoria WHERE nombre LIKE ?";
    private static final String ELIMINAR_CATEGORIA = "DELETE FROM categoria WHERE categoria_id = ?";

    //Querys secundarias
    private static final String VERIFICAR_NOMBRE_DE_LA_NUEVA_CATEGORIA = "SELECT * FROM categoria WHERE nombre = ?";
    private static final String VERIFICAR_NOMBRE_CON_EL_QUE_SE_VA_ACTUALIZAR = "SELECT * FROM categoria WHERE nombre = ? AND categoria_id <> ?";

    public Optional<Categoria> verificarNuevoNombre(String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VERIFICAR_NOMBRE_DE_LA_NUEVA_CATEGORIA)) {
            validar.setString(1, nombre);

            ResultSet resultSet = validar.executeQuery();
            if (resultSet.next()) {
                Categoria categoria = new Categoria(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")
                );
                categoria.setCategoria_id(resultSet.getInt("categoria_id"));
                return Optional.of(categoria);
            }
            return Optional.empty();
        }
    }

    public boolean verificarNombreAActualizar(String nombre, int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VERIFICAR_NOMBRE_CON_EL_QUE_SE_VA_ACTUALIZAR)) {
            validar.setString(1, nombre);
            validar.setInt(2, id);
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public void insertar(Categoria t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_CATEGORIA)) {
            insert.setString(1, t.getNombre());
            insert.setString(2, t.getDescripcion());
            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(Categoria t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(EDITAR_CATEGORIA)) {
            update.setString(1, t.getNombre());
            update.setString(2, t.getDescripcion());
            update.setInt(3, t.getCategoria_id());
            update.executeUpdate();
        }
    }

    @Override
    public List<Categoria> seleccionar() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Categoria> categorias = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODAS_LAS_CATEGORIAS)) {
            ResultSet resultSet = select.executeQuery();

            while (resultSet.next() && contador < max) {
                Categoria categoria = new Categoria(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")
                );
                categoria.setCategoria_id(resultSet.getInt("categoria_id"));
                categorias.add(categoria);
            }
            return categorias;
        }

    }

    @Override
    public Optional<Categoria> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_CATEGORIA_POR_ID)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Categoria categoria = new Categoria(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")
                );
                categoria.setCategoria_id(resultSet.getInt("categoria_id"));
                return Optional.of(categoria);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Categoria> seleccionarPorParametro(String t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Categoria> categorias = new ArrayList<>();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(OBTENER_CATEGORIA_POR_STRING)) {
            select.setString(1, "%" + t + "%");

            ResultSet resultSet = select.executeQuery();

            while (resultSet.next() && contador < max) {
                Categoria categoria = new Categoria(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")
                );
                categoria.setCategoria_id(resultSet.getInt("categoria_id"));
                categorias.add(categoria);
            }
            return categorias;
        }
    }

    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_CATEGORIA)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }

}
