/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.categoria;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CategoriaDB implements CRUD<Categoria>{
    
    //Querys principales
    private static final String INSERTAR_CATEGORIA = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
    private static final String EDITAR_CATEGORIA = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE categoria_id";
    private static final String OBTENER_TODAS_LAS_CATEGORIAS = "SELECT * FROM categotia";
    private static final String OBTENER_CATEGORIA_POR_ID = "SELECT * FROM categoria WHERE categoria_id = ?";
    private static final String OBTENER_CATEGORIA_POR_STRING = "SELECT * FROM categoria WHERE nombre LIKE ?";
    private static final String ELIMINAR_CATEGORIA = "DELETE FROM categoria WHERE categoria_id = ?";
    
    //Querys secundarias
    private static final String VERIFICAR_NOMBRE_DE_LA_NUEVA_CATEGORIA = "SELECT * FROM categoria WHERE nombre = ?";
    private static final String VERIFICAR_NOMBRE_CON_EL_QUE_SE_VA_ACTUALIZAR = "SELECT * FROM categoria WHERE nombre = ? AND categoria_id <> ?";
    
    @Override
    public void insertar(Categoria t) throws SQLException {
        
    }

    @Override
    public void actualizar(Categoria t) throws SQLException {
        
    }

    @Override
    public List<Categoria> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Categoria> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Categoria> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eleiminar(int t) throws SQLException {
        
    }
    
            
}
