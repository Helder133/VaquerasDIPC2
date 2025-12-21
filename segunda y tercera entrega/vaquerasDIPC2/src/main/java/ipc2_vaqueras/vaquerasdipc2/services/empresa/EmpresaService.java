/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.empresa;

import ipc2_vaqueras.vaquerasdipc2.db.empresa.EmpresaDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.empresa.EmpresaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.empresa.EmpresaUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.empresa.Empresa;
import ipc2_vaqueras.vaquerasdipc2.models.videojuego.Videojuego;
import ipc2_vaqueras.vaquerasdipc2.services.videojuego.VideojuegoService;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class EmpresaService {

    public void crearEmpresa(EmpresaRequest empresaRequest) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Empresa empresa = extraerEmpresa(empresaRequest);
        EmpresaDB empresaDB = new EmpresaDB();
        if (empresaDB.verificarNombreUnico(empresa.getNombre())) {
            throw new EntityAlreadyExistsException(String.format("El nombre: %s, ya esta relacionado con otra empresa", empresa.getNombre()));
        }
        empresaDB.insertar(empresa);

    }

    private Empresa extraerEmpresa(EmpresaRequest empresaRequest) throws UserDataInvalidException {
        try {
            Empresa empresa = new Empresa(
                    empresaRequest.getNombre(),
                    empresaRequest.getDescripcion(),
                    empresaRequest.getComision_negociada()
            );
            
            if (!empresa.isValid()) {
                throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
            }
            return empresa;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
    }

    public void editarEmpresa(int id, EmpresaUpdate empresaUpdate) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Empresa empresa = extraerEmpresa(empresaUpdate);
        empresa.setEmpresa_id(id);
        EmpresaDB empresaDB = new EmpresaDB();
        if (empresaDB.verificarNuevoNombre(empresa.getNombre(), id)) {
            throw new EntityAlreadyExistsException(String.format("El nombre: %s, ya esta registrado en otra empresa, ingrese otro nombre", empresa.getNombre()));
        }
        empresaDB.actualizar(empresa);
    }

    private Empresa extraerEmpresa(EmpresaUpdate empresaUpdate) throws UserDataInvalidException {
        try {
            Empresa empresa = new Empresa(
                    empresaUpdate.getNombre(),
                    empresaUpdate.getDescripcion(),
                    empresaUpdate.getComision_negociada()
            );
            empresa.setEstado(empresaUpdate.isEstado());
            if (!empresa.isValid()) {
                throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
            }
            return empresa;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
    }

    public List<Empresa> seleccionarTodasLasEmpresas() throws SQLException {
        EmpresaDB empresaDB = new EmpresaDB();
        return empresaDB.seleccionar();
    }

    public Empresa seleccionarEmpresaPorParametro(int code) throws SQLException, UserDataInvalidException {
        EmpresaDB empresaDB = new EmpresaDB();
        Optional<Empresa> empresaOpt = empresaDB.seleccionarPorParametro(code);
        if (empresaOpt.isEmpty()) {
            throw new UserDataInvalidException("Error al obtener la empresa");
        }
        return empresaOpt.get();
    }

    public List<Empresa> seleccionarEmpresaPorParametro(String code) throws SQLException {
        EmpresaDB empresaDB = new EmpresaDB();
        return empresaDB.seleccionarPorParametro(code);
    }

    public void eliminarEmpresa(int code) throws SQLException, EntityAlreadyExistsException {
        EmpresaDB empresaDB = new EmpresaDB();
        Optional<Empresa> empresaOpt = empresaDB.seleccionarPorParametro(code);
        if (empresaOpt.isEmpty()) {
            throw new EntityAlreadyExistsException(
                    "La empresa que trata de eliminar, no existe");
        }
        empresaDB.eliminar(code);
    }

    public List<Videojuego> obteneVideojuegosDeLaEmpresa(int code) throws SQLException {
        VideojuegoService videojuegoService = new VideojuegoService();
        return videojuegoService.obtenerTodosLosVideojuegosDeUnaEmpresa(code);
    }
    
}
