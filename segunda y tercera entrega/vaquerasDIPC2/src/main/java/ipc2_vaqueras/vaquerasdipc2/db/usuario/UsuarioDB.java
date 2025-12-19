/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.usuario;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.EnumUsuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Login;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class UsuarioDB implements CRUD<Usuario> {
    //querys principales CRUD
    private static final String INSERTAR_NUEVO_USUARIO = "INSERT INTO usuario (nombre, email, contraseña, fecha_nacimiento, rol, telefono, avatar, pais) VALUES (?,?,?,?,?,?,?,?)";
    private static final String ACTUALIZAR_USUARIO_SIN_CONTRASEÑA = "UPDATE usuario SET nombre = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    private static final String ACTUALIZAR_USUARIO_CON_CONTRASEÑA = "UPDATE usuario SET nombre = ?, contraseña = ?, fecha_nacimiento = ?, telefono = ?, avatar = ?, pais = ? WHERE usuario_id = ?";
    private static final String SELECCIONAR_TODO_LOS_USUARIO = "SELECT * FROM usuario";
    private static final String SELECCIONAR_USUARIO_POR_INT = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String SELECCIONAR_USUARIO_POR_STRING = "SELECT * FROM usuario WHERE nombre LIKE ?";
    private static final String ELIMINAR_USUARIO = "DELETE FROM usuario WHERE usuario_id = ?";
    
    //querys auxiliares
    private static final String LOGIN = "SELECT * FROM usuario WHERE email = ? AND contraseña = ?";
    private static final String VALIDAR_EMAIL = "SELECT * FROM usuario WHERE email = ?";
    private static final String VALIDAR_TELEFONO = "SELECT * FROM usuario WHERE telefono = ?";
    private static final String VALIDAR_NUEVO_TELEFONO = "SELECT * FROM usuario WHERE telefono = ? AND usuario_id <> ?";
    
    public Optional<Usuario> login(Login login) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement userlogin = connection.prepareStatement(LOGIN)) {
            userlogin.setString(1, login.getEmail());
            userlogin.setString(2, login.getContraseña());
            ResultSet resultSet = userlogin.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getBytes("avatar"),
                        resultSet.getString("pais")
                );
                usuario.setEmpresa_id(resultSet.getInt("empresa_id"));
                usuario.setUsuario_id(resultSet.getInt("Usuario_Id"));

                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement buscarPorEmail = connection.prepareStatement(VALIDAR_EMAIL)) {
            buscarPorEmail.setString(1, email);
            ResultSet resultSet = buscarPorEmail.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getBytes("avatar"),
                        resultSet.getString("pais")
                );
                usuario.setEmpresa_id(resultSet.getInt("empresa_id"));
                usuario.setUsuario_id(resultSet.getInt("Usuario_Id"));

                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    public boolean validarTelefono(String telefono) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validarTelefono = connection.prepareStatement(VALIDAR_TELEFONO)){
            validarTelefono.setString(1, telefono);
            ResultSet resultSet = validarTelefono.executeQuery();
            return resultSet.next();
        }
    }
    
    public boolean validarTelefonoNuevo(String telefono, int id) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validarTelefono = connection.prepareStatement(VALIDAR_NUEVO_TELEFONO)){
            validarTelefono.setString(1, telefono);
            validarTelefono.setInt(2, id);
            ResultSet resultSet = validarTelefono.executeQuery();
            return resultSet.next();
        }
    }
    
    @Override
    public void insertar(Usuario t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR_NUEVO_USUARIO)) {
            insert.setString(1, t.getNombre());
            insert.setString(2, t.getEmail());
            insert.setString(3, t.getContraseña());
            insert.setDate(4, Date.valueOf(t.getFecha_nacimiento()));
            insert.setString(5, t.getRol().toString());
            insert.setString(6, t.getTelefono());
            insert.setBytes(7, t.getAvatar());
            insert.setString(8, t.getPais());

            insert.executeUpdate();
        }
    }

    @Override
    public void actualizar(Usuario t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        if (StringUtils.isBlank(t.getContraseña())) {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_USUARIO_SIN_CONTRASEÑA)) {
                update.setString(1, t.getNombre());
                update.setDate(2, Date.valueOf(t.getFecha_nacimiento()));
                update.setString(3, t.getTelefono());
                update.setBytes(4, t.getAvatar());
                update.setString(5, t.getPais());
                update.setInt(6, t.getUsuario_id());
                update.executeUpdate();
            }
        } else {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_USUARIO_CON_CONTRASEÑA)) {
                update.setString(1, t.getNombre());
                update.setString(2, t.getContraseña());
                update.setDate(3, Date.valueOf(t.getFecha_nacimiento()));
                update.setString(4, t.getTelefono());
                update.setBytes(5, t.getAvatar());
                update.setString(6, t.getPais());
                update.setInt(7, t.getUsuario_id());
                update.executeUpdate();
            }
        }
    }

    @Override
    public List<Usuario> seleccionar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_TODO_LOS_USUARIO)) {
            ResultSet resultSet = select.executeQuery();

            while (resultSet.next() && contador <= max) {
                contador++;
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getBytes("avatar"),
                        resultSet.getString("pais")
                );
                usuario.setEmpresa_id(resultSet.getInt("empresa_id"));
                usuario.setUsuario_id(resultSet.getInt("usuario_id"));
                usuarios.add(usuario);
            }
            return usuarios;
        }
    }

    @Override
    public Optional<Usuario> seleccionarPorParametro(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_USUARIO_POR_INT)) {
            select.setInt(1, t);

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getBytes("avatar"),
                        resultSet.getString("pais")
                );
                usuario.setEmpresa_id(resultSet.getInt("empresa_id"));
                usuario.setUsuario_id(resultSet.getInt("usuario_id"));
                return Optional.of(usuario);
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> seleccionarPorParametro(String t) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        int max = 10;
        int contador = 0;
        try (PreparedStatement select = connection.prepareStatement(SELECCIONAR_USUARIO_POR_STRING)) {
            select.setString(1, "%" + t + "%");

            ResultSet resultSet = select.executeQuery();
            while (resultSet.next() && contador <= max) {
                contador++;
                Usuario usuario = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getDate("fecha_nacimiento").toLocalDate(),
                        EnumUsuario.valueOf(resultSet.getString("rol")),
                        resultSet.getString("telefono"),
                        resultSet.getBytes("avatar"),
                        resultSet.getString("pais")
                );
                usuario.setEmpresa_id(resultSet.getInt("empresa_id"));
                usuario.setUsuario_id(resultSet.getInt("usuario_id"));
                usuarios.add(usuario);
            }
            return usuarios;
        }
    }

    @Override
    public void eliminar(int t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR_USUARIO)) {
            delete.setInt(1, t);
            delete.executeUpdate();
        }
    }
}
