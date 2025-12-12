/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaquerad.vaquerasdipc2.db.usuario;

import ipc2_vaquerad.vaquerasdipc2.db.CRUD;
import ipc2_vaquerad.vaquerasdipc2.models.usuario.Usuario;
import java.util.List;

/**
 *
 * @author helder
 */
public class UsuarioDB implements CRUD<Usuario>{
    private static final String INSERTAR_NUEVO_USUARIO = "INSERT INTO usuario (nombre, email, contraseña, fecha_nacimiento, rol, telefono, avatar, pais, empresa_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_USUARIO_SIN_CONTRASEÑA = "UPDATE usuario SET nombre = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    private static final String ACTUALIZAR_USUARIO_CON_CONTRASEÑA = "UPDATE usuario SET nombre = ?, contraseña = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    @Override
    public void insertar(Usuario t) {
        
    }

    @Override
    public void actualizar(Usuario t) {
        
    }

    @Override
    public List<Usuario> seleccionar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario seleccionarPorParametro(int t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario seleccionarPorParametro(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eleiminar(int t) {
        
    }
}
