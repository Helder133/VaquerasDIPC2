/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.configuracionSistema;

import ipc2_vaqueras.vaquerasdipc2.db.configuracionSistema.ConfiguracionDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.configuracionSistema.ConfiguracionUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.configuracionSistema.Configuracion;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class ConfiguracionService {
    
    public Configuracion obtenerLaConfiguracionDelSistema () throws SQLException, UserDataInvalidException{
        ConfiguracionDB configuracionDB = new ConfiguracionDB();
        Optional<Configuracion> configuracionOptional = configuracionDB.seleccionarUnicaConfiguracion();
        if (configuracionOptional.isEmpty()) {
            throw new UserDataInvalidException("Error al obtener la configuracion de sistema, si el problema no se soluciona al recargar, contacte con soporte");
        }
        return configuracionOptional.get();
    }
    
    public void actualizarConfiguracionSistema (ConfiguracionUpdate configuracionUpdate, int id) throws SQLException, UserDataInvalidException {
        Configuracion configuracion = extraerConfiguracion(configuracionUpdate);
        configuracion.setConfiguracion_id(id);
        ConfiguracionDB configuracionDB = new ConfiguracionDB();
        if (configuracionDB.seleccionarPorParametro(id).isEmpty()) {
            throw new UserDataInvalidException("No se puedo encontrar la configuracion a actualizar, intente de nuevo");
        }
        configuracionDB.actualizar(configuracion);
    }

    private Configuracion extraerConfiguracion(ConfiguracionUpdate configuracionUpdate) throws UserDataInvalidException {
        Configuracion configuracion = new Configuracion(configuracionUpdate.getPorcentaje_ganancia(), configuracionUpdate.getTamaño_grupo());
        if (!configuracion.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return configuracion;
    }
    
}
