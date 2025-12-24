/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera;

import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera.CarteraDB;
import ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera.historial.HistorialDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.usuario.cartera.CarteraUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.EnumHistorial;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.historial.Historial;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera.historial.HistorialService;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CarteraService {

    public void crearCartera(int usuario_id, Connection connection) throws SQLException, EntityAlreadyExistsException {
        if (validarUnicaCartera(usuario_id)) {
            throw new EntityAlreadyExistsException("El usuario ya cuenta con una cartera digital");
        }
        Cartera cartera = new Cartera(usuario_id, 0);
        CarteraDB carteraDB = new CarteraDB();
        carteraDB.insertar(cartera, connection);
    }

    private boolean validarUnicaCartera(int usuario_id) throws SQLException {
        CarteraDB carteraDB = new CarteraDB();
        return carteraDB.validarUnicoUsuario(usuario_id);
    }

    public void actualizarCarteraDeposito(CarteraUpdate carteraUpdate) throws SQLException, UserDataInvalidException {
        if (carteraUpdate.getSaldo() < 0) {
            throw new UserDataInvalidException("La cantidad con que el saldo se va a actualizar es menor a 0 y no se puede");
        }
        Cartera carteraN = extraerCartera(carteraUpdate);
        CarteraDB carteraDB = new CarteraDB();
        HistorialService historialService = new HistorialService();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Optional<Cartera> carteraOpt = carteraDB.seleccionarPorParametro(carteraN.getUsuario_id());
            if (carteraOpt.isEmpty()) {
                throw new UserDataInvalidException("No se pudo encontrar la cartera soliciatada");
            }
            float total = carteraOpt.get().getSaldo() + carteraN.getSaldo();
            carteraOpt.get().setSaldo(total);

            Historial historial = new Historial(carteraOpt.get().getCartera_id(), EnumHistorial.deposito, LocalDate.now(), carteraN.getSaldo());
            historialService.crearHitorial(historial, connection);

            carteraDB.actualizar(carteraOpt.get(), connection);

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

    private Cartera extraerCartera(CarteraUpdate carteraUpdate) throws UserDataInvalidException {
        Cartera cartera = new Cartera(carteraUpdate.getUsuario_id(), carteraUpdate.getSaldo());
        if (!cartera.isValid()) {
            throw new UserDataInvalidException("No se puedo procesar el pago, vuleva a intentar");
        }
        return cartera;

    }

    public Cartera seleccionarCartera(int usuario_id) throws SQLException, UserDataInvalidException {
        CarteraDB carteraDB = new CarteraDB();
        Optional<Cartera> carteraOpt = carteraDB.seleccionarPorParametro(usuario_id);
        if (carteraOpt.isEmpty()) {
            throw new UserDataInvalidException("No se puedo encontrar la cartera del usuario seleccionado");
        }
        return carteraOpt.get();
    }

    public void eliminarCartera(int usuario_id, Connection connection) throws SQLException, UserDataInvalidException {
        CarteraDB carteraDB = new CarteraDB();
        Optional<Cartera> carteraOpt = carteraDB.seleccionarPorParametro(usuario_id);
        HistorialService historialService = new HistorialService();

        if (historialService.isExisteHistorial(carteraOpt.get().getCartera_id(), connection)) {
            historialService.eliminarHistorial(carteraOpt.get().getCartera_id(), connection);
        }
        if (carteraDB.validarUnicoUsuario(usuario_id)) {
            carteraDB.eliminar(usuario_id, connection);
        }
    }

    public List<Historial> obtenerHistorialDeUsuario(int usuario_id) throws SQLException, UserDataInvalidException {
        if (!validarUnicaCartera(usuario_id)) {
            throw new UserDataInvalidException("El usuario no cuenta con una cartera digital, por lo tanto no tiene un historial");
        }
        HistorialDB historialDB = new HistorialDB();
        return historialDB.obtenerHistorial(usuario_id);
    }

}
