/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;
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
public class CarteraDB implements CRUD<Cartera> {

    private static final String INSERTAR_CARTERA = "INSERT INTO cartera_digital (usuario_id, saldo) VALUES (?, ?)";
    private static final String ACTUALIZAR_CARTERA = "UPDATE cartera_digital SET saldo = ? WHERE cartera_id = ?";
    private static final String SELECCIONAR_CARTERA = "SELECT * FROM cartera_digital WHERE usuario_id = ?";
    private static final String ELIMINAR_CARTERA = "DELETE FROM cartera_digital WHERE usuario_id = ?";

    private static final String VALIDAR_USUARIO = "SELECT * FROM cartera_digital WHERE usuario_id = ?";
    //private static final String

    public boolean validarUnicoUsuario(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_USUARIO)) {
            validar.setInt(1, id);

            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }
    
    public void insertar(Cartera t, Connection connection) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement(INSERTAR_CARTERA)) {
            update.setInt(1, t.getUsuario_id());
            update.setFloat(2, t.getSaldo());
            
            update.executeUpdate();
        }
    }
    
    @Override
    public void insertar(Cartera t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(INSERTAR_CARTERA)) {
            update.setInt(1, t.getUsuario_id());
            update.setFloat(2, t.getSaldo());
            update.executeUpdate();
        }
    }

    public void actualizar(Cartera t, Connection connection) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CARTERA)) {
            update.setFloat(1, t.getSaldo());
            update.setInt(2, t.getCartera_id());
            update.executeUpdate();
        }
    }
    
    @Override
    public void actualizar(Cartera t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CARTERA)) {
            update.setFloat(1, t.getSaldo());
            update.setInt(2, t.getCartera_id());
            update.executeUpdate();
        }
    }

    @Override
    public List<Cartera> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Optional<Cartera> seleccionarPorParametro(int t, Connection connection) throws SQLException {
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_CARTERA)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Cartera cartera = new Cartera(
                        resultSet.getInt("usuario_id"),
                        resultSet.getFloat("saldo")
                );
                cartera.setCartera_id(resultSet.getInt("cartera_id"));
                return Optional.of(cartera);
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Cartera> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_CARTERA)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Cartera cartera = new Cartera(
                        resultSet.getInt("usuario_id"),
                        resultSet.getFloat("saldo")
                );
                cartera.setCartera_id(resultSet.getInt("cartera_id"));
                return Optional.of(cartera);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Cartera> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void eliminar(int t, Connection connection) throws SQLException {
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_CARTERA)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_CARTERA)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }

}
