/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera.historial;

import ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera.historial.HistorialDB;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.Historial;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author helder
 */
public class HistorialService {
    
    public void crearHitorial(Historial historial, Connection connection) throws SQLException {
        HistorialDB historialDB = new HistorialDB();
        historialDB.insertar(historial, connection);
    } 
    
    public List<Historial> obtenerHistorial(int cartera_id) throws SQLException {
        HistorialDB historialDB = new HistorialDB();
        return historialDB.obtenerHistorial(cartera_id);
    }
    
    public void eliminarHistorial(int cartera_id, Connection connection) throws SQLException {
        HistorialDB historialDB = new HistorialDB();
        historialDB.eliminar(cartera_id, connection);
    }
    
    public boolean isExisteHistorial(int cartera_id, Connection connection) throws SQLException {
        HistorialDB historialDB = new HistorialDB();
        return historialDB.validarMinimoUnHistorial(cartera_id, connection);
    }
}
