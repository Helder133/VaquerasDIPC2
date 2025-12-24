/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.bibliotecaVideojuego;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.models.bibliiotecaVideojuego.BibliotecaVideojuego;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class BibliotecaVideojuegoDB implements CRUD<BibliotecaVideojuego>{
    
    private final static String INSERTAR_VIDEOJUEGO_EN_BIBLIOTECA = "";
    private final static String ACTUALIZAR_ESTADO_DE_DESCARGA = "";
    private final static String SELECCIONAR_VIDEOJUEGO_POR_CONINCIDENCIA_DE_NOMBRE_DE_UN_USUARIO = "";
    private final static String SELECCIONAR_TODOS_LOS_VIDEOJEUGOS_DE_UN_USUARIO = "";
    private final static String SELECCIONAR_UN_VIDEOJUEGO_EN_ESPECIFICO_DE_UN_USUARIO= "";
    
    private final static String VALIDAR_QUE_NO_TENGA_EL_VIDEOJUEGO = "";
    
    @Override
    public void insertar(BibliotecaVideojuego t) throws SQLException {
        
    }

    @Override
    public void actualizar(BibliotecaVideojuego t) throws SQLException {
        
    }

    @Override
    public List<BibliotecaVideojuego> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<BibliotecaVideojuego> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BibliotecaVideojuego> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        
    }
    
}
