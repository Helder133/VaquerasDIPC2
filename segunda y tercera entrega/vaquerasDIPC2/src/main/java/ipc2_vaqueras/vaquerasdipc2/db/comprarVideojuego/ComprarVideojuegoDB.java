/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.comprarVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.comprarVideojuego.ComprarVideojuego;
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
public class ComprarVideojuegoDB implements CRUD<ComprarVideojuego>{
    
    private static final String INSERTAR_COMPRA = "INSERT INTO comprar_videojuego (videojuego_id, usuario_id, fecha) VALUES (?,?,?)";
    private static final String VERIFICAR_COMPRA_YA_HECHA = "SELECT * FROM comprar_videojuego WHERE videojuego_id = ? AND usuario_id = ?";
    private static final String OBTENER_TODAS_LAS_COMPRAS = "select c.fecha, c.videojuego_id, c.usuario_id, v.empresa_id, v.nombre as nombre_videojuego, v.imagen, v.descripcion, e.nombre as nombre_empresa from comprar_videojuego c join videojuego v on c.videojuego_id = v.videojuego_id join empresa e on v.empresa_id = e.empresa_id;";
    //private static final String = "";
    //private static final String = "";
    
    //insercion con transaccion 
    public void insertar(ComprarVideojuego t, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_COMPRA)) {
            insert.setInt(1, t.getVideojuego_id());
            insert.setInt(2, t.getUsuario_id());
            insert.setDate(3, Date.valueOf(t.getFecha()));
            
            insert.executeUpdate();
        }
    }
    
    
    //boolean con transaccion
    public boolean validarUnicaCompra(int videojuego_id, int usuario_id, Connection connection) throws SQLException {
        try (PreparedStatement validar = connection.prepareStatement(VERIFICAR_COMPRA_YA_HECHA)) {
            validar.setInt(1, videojuego_id);
            validar.setInt(2, usuario_id);
            
            ResultSet resultSet = validar.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(ComprarVideojuego t) throws SQLException {
        
    }

    @Override
    public void actualizar(ComprarVideojuego t) throws SQLException {
        
    }

    @Override
    public List<ComprarVideojuego> seleccionar() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ComprarVideojuego> comprarVideojuegos = new ArrayList<>();
        try (PreparedStatement obtener = connection.prepareStatement(OBTENER_TODAS_LAS_COMPRAS)) {
            
            ResultSet resultSet = obtener.executeQuery();
            while (resultSet.next()) {
                ComprarVideojuego comprarVideojuego = new ComprarVideojuego(
                        resultSet.getInt("videojuego_id"),  
                        resultSet.getInt("usuario_id"),  
                        resultSet.getDate("fecha").toLocalDate());
                comprarVideojuego.setEmpresa_id(resultSet.getInt("empresa_id"));
                comprarVideojuego.setNombreEmpresa(resultSet.getString("nombre_empresa"));
                comprarVideojuego.setNombreVideojuego(resultSet.getString("nombre_videojuego"));
                comprarVideojuego.setImagen(resultSet.getBytes("imagen"));
                comprarVideojuego.setDescripcion(resultSet.getString("descripcion"));
                comprarVideojuegos.add(comprarVideojuego);
            }
            return comprarVideojuegos;
        }
    }

    @Override
    public Optional<ComprarVideojuego> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ComprarVideojuego> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        
    }
    
}
