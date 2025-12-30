/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.configuracionSistema;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.configuracionSistema.Configuracion;
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
public class ConfiguracionDB implements CRUD<Configuracion> {

    private static final String SELECCIONAR_CONFIGURACION_DEL_SISTEMA = "SELECT * FROM configuracion_sistema WHERE configuracion_id = 1";
    private static final String ACTUALIZAR_CONFIGURACION_DEL_SISTEMA = "UPDATE configuracion_sistema SET porcentaje_ganancia = ?, tamaño_grupo = ? WHERE configuracion_id = ?";
    private static final String VALIDAR_CINFIGURACION = "SELECT * FROM configuracion_sistema WHERE configuracion_id = ?";
    
    @Override
    public void insertar(Configuracion t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Optional<Configuracion> seleccionarUnicaConfiguracion() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_CONFIGURACION_DEL_SISTEMA)) {
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Configuracion configuracion = new Configuracion(resultSet.getFloat("porcentaje_ganancia"), resultSet.getInt("tamaño_grupo"));
                configuracion.setConfiguracion_id(resultSet.getInt("configuracion_id"));
                return Optional.of(configuracion);
            }
            return Optional.empty();
        } 
    }
    
    @Override
    public void actualizar(Configuracion t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CONFIGURACION_DEL_SISTEMA)) {
            update.setFloat(1, t.getPorcentaje_ganancia());
            update.setInt(2, t.getTamaño_grupo());
            update.setInt(3, t.getConfiguracion_id());
            update.executeUpdate();
        }
    }

    @Override
    public List<Configuracion> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Configuracion> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(VALIDAR_CINFIGURACION)) {
            select.setInt(1, t);
            
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Configuracion configuracion = new Configuracion(resultSet.getFloat("porcentaje_ganancia"), resultSet.getInt("tamaño_grupo"));
                configuracion.setConfiguracion_id(resultSet.getInt("configuracion_id"));
                return Optional.of(configuracion);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Configuracion> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
