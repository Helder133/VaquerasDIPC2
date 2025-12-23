/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.usuario;

import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.db.usuario.UsuarioDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.login.LoginRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.UsuarioUpdate;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera.CarteraUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.EnumUsuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Login;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.Usuario;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera.CarteraService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class UsuarioService {

    public Usuario login(LoginRequest loginRequest) throws SQLException, UserDataInvalidException {
        Login login = new Login(loginRequest.getEmail(), loginRequest.getContraseña());
        UsuarioDB usuarioDB = new UsuarioDB();
        Optional<Usuario> usuarioOpt = usuarioDB.login(login);
        if (usuarioOpt.isEmpty()) {
            throw new UserDataInvalidException("Contraseña o Usuario incorrecto vuelva a intentar");
        }
        return usuarioOpt.get();
    }

    public void crearUsuario(UsuarioRequest usuarioRequest) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Usuario usuario = extraerUsuario(usuarioRequest);
        UsuarioDB usuarioDB = new UsuarioDB();
        CarteraService carteraService = new CarteraService();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            
            connection.setAutoCommit(false);

            if (!usuarioDB.obtenerUsuarioPorEmail(usuario.getEmail()).isEmpty()) {
                throw new EntityAlreadyExistsException(String.format("El correo: %s, ya esta relacionado con otro usuario", usuario.getEmail()));
            }
            if (usuarioDB.validarTelefono(usuario.getTelefono())) {
                throw new EntityAlreadyExistsException(String.format("El telefono: %s, ya esta relacionado con otro usuario", usuario.getTelefono()));
            }
            int usuario_id = usuarioDB.insertar(usuario, connection);

            if (usuario.getRol().equals(EnumUsuario.comun)) {
                carteraService.crearCartera(usuario_id, connection);
            }

            connection.commit();

        } catch (EntityAlreadyExistsException | SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }

    }

    private Usuario extraerUsuario(UsuarioRequest usuarioRequest) throws UserDataInvalidException {
        try {
            Usuario usuario = new Usuario(
                    usuarioRequest.getNombre(),
                    usuarioRequest.getEmail(),
                    usuarioRequest.getContraseña(),
                    usuarioRequest.getFecha_nacimiento(),
                    usuarioRequest.getRol(),
                    usuarioRequest.getTelefono(),
                    usuarioRequest.getAvatar(),
                    usuarioRequest.getPais()
            );
            if (!usuario.isValid()) {
                throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
            }
            return usuario;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
    }

    public void editarUsuario(int id, UsuarioUpdate usuarioUpdate) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Usuario usuario = extraerUsuario(usuarioUpdate);
        usuario.setUsuario_id(id);
        UsuarioDB usuarioDB = new UsuarioDB();
        if (usuarioDB.validarTelefonoNuevo(usuario.getTelefono(), id)) {
            throw new EntityAlreadyExistsException(String.format("El telefono: %s, ya esta relacionado con otro usuario", usuario.getTelefono()));
        }
        usuarioDB.actualizar(usuario);
    }

    private Usuario extraerUsuario(UsuarioUpdate usuarioUpdate) throws UserDataInvalidException {
        try {
            Usuario usuario = new Usuario(
                    usuarioUpdate.getNombre(),
                    "",
                    usuarioUpdate.getContraseña(),
                    usuarioUpdate.getFecha_nacimiento(),
                    null,
                    usuarioUpdate.getTelefono(),
                    usuarioUpdate.getAvatar(),
                    usuarioUpdate.getPais()
            );
            if (ValidarDatosAActualizar(usuario)) {
                throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
            }
            return usuario;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
    }

    private boolean ValidarDatosAActualizar(Usuario usuario) {
        return StringUtils.isBlank(usuario.getNombre())
                && usuario.getFecha_nacimiento() != null;
    }

    public Optional<Usuario> existsEmail(String email) throws SQLException {
        UsuarioDB usuarioDB = new UsuarioDB();
        return usuarioDB.obtenerUsuarioPorEmail(email);
    }

    public List<Usuario> seleccionarTodosLosUsuarios() throws SQLException {
        UsuarioDB usuarioDB = new UsuarioDB();
        return usuarioDB.seleccionar();
    }

    public Usuario seleccionarUsuarioPorParametro(int code) throws SQLException, UserDataInvalidException {
        UsuarioDB usuarioDB = new UsuarioDB();
        Optional<Usuario> usuarioOpt = usuarioDB.seleccionarPorParametro(code);
        if (usuarioOpt.isEmpty()) {
            throw new UserDataInvalidException("Error al obtener al usuario");
        }
        return usuarioOpt.get();
    }

    public List<Usuario> seleccionarUsuarioPorParametro(String code) throws SQLException, UserDataInvalidException {
        UsuarioDB usuarioDB = new UsuarioDB();
        return usuarioDB.seleccionarPorParametro(code);
    }

    public void eliminarUsuario(int code) throws SQLException, UserDataInvalidException {
        UsuarioDB usuarioDB = new UsuarioDB();
        Optional<Usuario> usuarioOpt = usuarioDB.seleccionarPorParametro(code);
        if (usuarioOpt.isEmpty()) {
            throw new UserDataInvalidException("El usuario que trata de eliminar, no existe");
        }
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            
            CarteraService carteraService = new CarteraService();
            carteraService.eliminarCartera(code, connection);
            usuarioDB.eliminar(code, connection);
            
            
            connection.commit();
        } catch (UserDataInvalidException | SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    public Cartera obtenerCartera(int usuario_id) throws SQLException, UserDataInvalidException {
        CarteraService carteraService = new CarteraService();
        return carteraService.seleccionarCartera(usuario_id);
    }

    public void depositarEnCartera(CarteraUpdate carteraUpdate) throws SQLException, UserDataInvalidException {
        CarteraService carteraService = new CarteraService();
        carteraService.actualizarCarteraDeposito(carteraUpdate);
    }
}
