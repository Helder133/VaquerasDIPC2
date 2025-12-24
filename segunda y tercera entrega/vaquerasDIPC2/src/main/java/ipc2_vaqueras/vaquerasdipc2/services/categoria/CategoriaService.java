/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.categoria;

import ipc2_vaqueras.vaquerasdipc2.db.categoria.CategoriaDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.CategoriaRequest;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.CategoriaUpdate;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CategoriaService {

    public void crearCategoria(CategoriaRequest categoriaRequest) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Categoria categoria = extraerCategoria(categoriaRequest);
        CategoriaDB categoriaDB = new CategoriaDB();
        if (categoriaDB.verificarNuevoNombre(categoriaRequest.getNombre()).isPresent()) {
            throw new EntityAlreadyExistsException(String.format("El nombre: %s, ya esta relacionada con otra categoria", categoria.getNombre()));
        }
        categoriaDB.insertar(categoria);
    }

    private Categoria extraerCategoria(CategoriaRequest categoriaRequest) throws UserDataInvalidException {
        Categoria categoria = new Categoria(
                categoriaRequest.getNombre(),
                categoriaRequest.getDescripcion()
        );
        if (!categoria.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }

        return categoria;
    }
    
    public void editarCategoria(CategoriaUpdate categoriaUpdate, int id) throws UserDataInvalidException, SQLException, EntityAlreadyExistsException {
        Categoria categoria = extraerCategoria(categoriaUpdate);
        categoria.setCategoria_id(id);
        CategoriaDB categoriaDB = new CategoriaDB();
        if (categoriaDB.verificarNombreAActualizar(categoria.getNombre(), id)) {
            throw new EntityAlreadyExistsException(String.format("El nombre: %s, ya esta relacionada con otra categoria", categoria.getNombre()));
        }
        categoriaDB.actualizar(categoria);
    }

    private Categoria extraerCategoria(CategoriaUpdate categoriaUpdate) throws UserDataInvalidException {
        Categoria categoria = new Categoria(
                categoriaUpdate.getNombre(),
                categoriaUpdate.getDescripcion()
        );
        if (!categoria.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }

        return categoria;
    }
    
    public List<Categoria> seleccionarTodosLasCategorias () throws SQLException {
        CategoriaDB categoriaDB = new CategoriaDB();
        return categoriaDB.seleccionar();
    }
    
    public List<Categoria> seleccionarCategoriaPorParametro (String nombre) throws SQLException {
        CategoriaDB categoriaDB = new CategoriaDB();
        return categoriaDB.seleccionarPorParametro(nombre);
    }
    
    public Categoria seleccionarCategoriaPorParametro(int id) throws SQLException, UserDataInvalidException {
        CategoriaDB categoriaDB = new CategoriaDB();
        Optional<Categoria> categoriaOpt = categoriaDB.seleccionarPorParametro(id);
        if (categoriaOpt.isEmpty()) {
            throw new UserDataInvalidException("No se encontro la categoria solicitada, vuelva a intentar");
        }
        return categoriaOpt.get();
    }
    
    public void eliminarCategoria(int id) throws SQLException, UserDataInvalidException {
        CategoriaDB categoriaDB = new CategoriaDB();
        Optional<Categoria> categoriaOpt = categoriaDB.seleccionarPorParametro(id);
        if (categoriaOpt.isEmpty()) {
            throw new UserDataInvalidException("La categoria que desea eliminar no existe");
        }
        categoriaDB.eliminar(id);
    }
}
