/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.comprarVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.models.comprarVideojuego.ComprarVideojuego;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class ComprarVideojuegoDB implements CRUD<ComprarVideojuego>{
    
    private static final String INSERTAR_COMPRA = "";
    private static final String VERIFICAR_COMPRA_YA_HECHA = "";
    private static final String OBTENER_TODAS_LAS_COMPRAS = "";
    //private static final String = "";
    //private static final String = "";
    
    @Override
    public void insertar(ComprarVideojuego t) throws SQLException {
        
    }

    @Override
    public void actualizar(ComprarVideojuego t) throws SQLException {
        
    }

    @Override
    public List<ComprarVideojuego> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
