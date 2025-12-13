/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaquerad.vaquerasdipc2.db.usuario;

import ipc2_vaquerad.vaquerasdipc2.db.CRUD;
import ipc2_vaquerad.vaquerasdipc2.db.DBConnection;
import ipc2_vaquerad.vaquerasdipc2.models.usuario.EnumUsuario;
import ipc2_vaquerad.vaquerasdipc2.models.usuario.Login;
import ipc2_vaquerad.vaquerasdipc2.models.usuario.Usuario;
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
public class UsuarioDB implements CRUD<Usuario>{
    //querys principales CRUD
    private static final String INSERTAR_NUEVO_USUARIO = "INSERT INTO usuario (nombre, email, contraseña, fecha_nacimiento, rol, telefono, avatar, pais, empresa_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_USUARIO_SIN_CONTRASEÑA = "UPDATE usuario SET nombre = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    private static final String ACTUALIZAR_USUARIO_CON_CONTRASEÑA = "UPDATE usuario SET nombre = ?, contraseña = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    private static final String SELECCIONAR_TODO_LOS_USUARIO = "SELECT * FROM usuario";
    private static final String SELECCIONAR_USUARIO_POR_INT = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String SELECCIONAR_USUARIO_POR_STRING = "SELECT * FROM usuario WHERE nombre LIKE ?";
    private static final String ELIMINAR_USUARIO = "DELETE FROM usuario WHERE usuario_id = ?";
    
    //querys auxiliares
    private static final String LOGIN = "SELECT * FROM usuario WHERE email = ? AND contraseña = ?";
    private static final String VALIDAR_EMAIL = "SELECT * FROM usuario WHERE email = ?";
    
    public Optional<Usuario> login (Login login) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement userlogin = connection.prepareStatement(LOGIN)){
            userlogin.setString(1, login.getEmail());
            userlogin.setString(2, login.getContraseña());
            ResultSet resultSet = userlogin.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        resultSet.getString("contraseña"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getString("avatar"),
                        resultSet.getString("pais"),
                        resultSet.getInt("empresa_id")
                );
                usuario.setUsuario_id(resultSet.getInt("Usuario_Id"));
                
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
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
