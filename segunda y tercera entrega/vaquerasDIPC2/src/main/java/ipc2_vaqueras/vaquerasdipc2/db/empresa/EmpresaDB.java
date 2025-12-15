/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.empresa;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.empresa.Empresa;
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
public class EmpresaDB implements CRUD<Empresa> {

    //querys principales
    private static final String INSERTAR_NUEVA_EMPRESA = "INSERT INTO empresa (nombre, descripcion, comision_negociada) VALUES (?,?,?)";
    private static final String ACTUALIZAR_EMPRESA = "UPDATE empresa SET nombre = ?, descripcion = ?, comision_negociada = ? WHERE empresa_id = ?";
    private static final String SELECCIONAR_TODAS_LAS_EMPRESAS = "SELECT * FROM empresa";
    private static final String SELECCIONAR_EMPRESA_POR_INT = "SELECT * FROM empresa WHERE empresa_id = ?";
    private static final String SELECCIONAR_EMPRESA_POR_STRING = "SELECT * FROM empresa WHERE nombre like ?";
    private static final String ELIMINAR_EMPRESA = "DELETE FROM empresa WHERE empresa_id = ?";
    
    //querys auxiliares
    private static final String VERIFICAR_NOMBRE_UNICO = "SELECT * FROM empresa WHERE nombre = ?";
    private static final String VERIFICAR_NUEVO_NOMBRE = "SELECT * FROM empresa WHERE nombre = ? AND empresa_id <> ?";
    
    public boolean verificarNombreUnico (String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement verificarNombre = connection.prepareStatement(VERIFICAR_NOMBRE_UNICO)){
            verificarNombre.setString(1, nombre);
            ResultSet resultSet = verificarNombre.executeQuery();
            return resultSet.next();
        }
    }
    
    public boolean verificarNuevoNombre (String nombre, int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement verificarNombre = connection.prepareStatement(VERIFICAR_NUEVO_NOMBRE)){
            verificarNombre.setString(1, nombre);
            verificarNombre.setInt(2, id);
            ResultSet resultSet = verificarNombre.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(Empresa t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_NUEVA_EMPRESA)) {
            insert.setString(1, t.getNombre());
            insert.setString(2, t.getDescripcion());
            insert.setFloat(3, t.getComision_negociada());

            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(Empresa t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_EMPRESA)) {
            update.setString(1, t.getNombre());
            update.setString(2, t.getDescripcion());
            update.setFloat(3, t.getComision_negociada());
            update.executeUpdate();
        }
    }

    @Override
    public List<Empresa> seleccionar() throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODAS_LAS_EMPRESAS)) {
            ResultSet resultSet = select.executeQuery();

            while (resultSet.next() && contador <= max) {
                contador++;
                Empresa empresa = new Empresa(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("comision_negociada")
                );
                empresa.setEmpresa_id(resultSet.getInt("empresa_id"));
                empresas.add(empresa);
            }
            return empresas;
        }
    }

    @Override
    public Optional<Empresa> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_EMPRESA_POR_INT)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                Empresa empresa = new Empresa(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("comision_negociada")
                );
                empresa.setEmpresa_id(resultSet.getInt("empresa_id"));
                return Optional.of(empresa);
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Empresa> seleccionarPorParametro(String t) throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_EMPRESA_POR_STRING)) {
            select.setString(1, "%" + t + "%");
            
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador <= max) {
                contador++;
                Empresa empresa = new Empresa(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getFloat("comision_negociada")
                );
                empresa.setEmpresa_id(resultSet.getInt("empresa_id"));
                empresas.add(empresa);
            }
            return empresas;
        }
    }

    @Override
    public void eleiminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_EMPRESA)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }
}
