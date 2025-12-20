/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera.historial;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.EnumHistorial;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.Historial;
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
public class HistorialDB implements CRUD<Historial> {

    private static final String INSERTAR_HISTORIAL = "INSERT INTO historial_cartera (cartera_id, transaccion, fecha, monto) VALUES (?, ?, ?, ?)";
    private static final String SELECCIONAR_HISTORIAL= "SELECT * FROM historial_cartera WHERE cartera_id = ?";
    private static final String ELIMINAR_HISTORIAL = "DELETE FROM historial_cartera WHERE cartera_id = ?";
    
    public boolean validarMinimoUnHistorial(int cartera_id) throws SQLException {
         Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_HISTORIAL)) {
            select.setInt(1, cartera_id);        
            ResultSet resultSet = select.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(Historial t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(INSERTAR_HISTORIAL)) {
            update.setInt(1, t.getCartera_id());
            update.setString(2, t.getTransaccion().toString());
            update.setDate(3, Date.valueOf(t.getFecha()));
            update.setFloat(4, t.getMonto());
            update.executeUpdate();
        }
    }
    
    public List<Historial> obtenerHistorial(int cartera_id) throws SQLException {
        List<Historial> historials = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_HISTORIAL)) {
            select.setInt(1, cartera_id);
            
            ResultSet resultSet = select.executeQuery();

            while (resultSet.next()) {
                Historial historial = new Historial(
                        resultSet.getInt("cartera_id"),
                        EnumHistorial.valueOf(resultSet.getString("transaccion")),
                        resultSet.getDate("fehca").toLocalDate(),
                        resultSet.getFloat("monto")
                );
                historial.setHistorial_id(resultSet.getInt("historial_id"));
                historials.add(historial);
            }
            return historials;
        }
    }
    
    @Override
    public void actualizar(Historial t) throws SQLException {
        
    }

    @Override
    public List<Historial> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Historial> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Historial> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_HISTORIAL)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }
    
}
