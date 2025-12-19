/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera;

import ipc2_vaqueras.vaquerasdipc2.db.usuario.cartera.CarteraDB;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.usuario.cartera.Cartera;
import ipc2_vaqueras.vaquerasdipc2.services.usuario.cartera.historial.HistorialService;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CarteraService {
    
    public void crearCartera(int usuario_id) throws SQLException, EntityAlreadyExistsException{
        if (validarUnicaCartera(usuario_id)) {
            throw new EntityAlreadyExistsException("El usuario ya cuenta con una cartera digital");
        }
        Cartera cartera = new Cartera(usuario_id,0);
        CarteraDB carteraDB = new CarteraDB();
        carteraDB.insertar(cartera);
    }
    
    private boolean validarUnicaCartera(int usuario_id) throws SQLException {
        CarteraDB carteraDB = new CarteraDB();
        return carteraDB.validarUnicoUsuario(usuario_id);
    }
    
    public void actualizarCartera(Cartera cartera) throws SQLException, UserDataInvalidException {
        if (cartera.getSaldo() < 0) {
            throw new UserDataInvalidException("La cantidad con que el saldo se va a actualizar es menor a 0 y no se puede");
        }
        CarteraDB carteraDB = new CarteraDB();
        carteraDB.actualizar(cartera);
    }
    
    public Cartera seleccionarCartera(int usuario_id) throws SQLException, UserDataInvalidException {
        CarteraDB carteraDB = new CarteraDB();
        Optional<Cartera> carteraOpt = carteraDB.seleccionarPorParametro(usuario_id);
        if (carteraOpt.isEmpty()) {
            throw new UserDataInvalidException("No se puedo encontrar la cartera del usuario seleccionado");
        }
        return carteraOpt.get();
    }
    
    public void eliminarCartera(int usuario_id) throws SQLException, UserDataInvalidException {
        CarteraDB carteraDB = new CarteraDB();
        Optional<Cartera> carteraOpt = carteraDB.seleccionarPorParametro(usuario_id);
        if (carteraOpt.isEmpty()) {
            throw new UserDataInvalidException("La cartera a eliminar no existe");
        }
        try {
            HistorialService historialService = new HistorialService();
            historialService.eliminarHistorial(usuario_id);
        } catch (SQLException e) {
        }
        
        carteraDB.eliminar(usuario_id);
    }
    
}
