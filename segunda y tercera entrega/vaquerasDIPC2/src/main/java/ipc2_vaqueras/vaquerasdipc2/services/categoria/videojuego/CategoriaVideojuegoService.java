/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.categoria.videojuego;

import ipc2_vaqueras.vaquerasdipc2.db.categoria.CategoriaDB;
import ipc2_vaqueras.vaquerasdipc2.db.categoria.videojuego.CategoriaVideojuegoDB;
import ipc2_vaqueras.vaquerasdipc2.dtos.categoria.videojuego.CategoriaVideojuegoRequest;
import ipc2_vaqueras.vaquerasdipc2.exceptions.EntityAlreadyExistsException;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.Categoria;
import ipc2_vaqueras.vaquerasdipc2.models.categoria.videojuego.CategoriaVideojuego;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class CategoriaVideojuegoService {

    public void crearCategoriaVideojuego(CategoriaVideojuegoRequest categoriaVideojuegoRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        CategoriaVideojuego categoriaVideojuego = extraerDatos(categoriaVideojuegoRequest);
        CategoriaVideojuegoDB categoriaVideojuegoDB = new CategoriaVideojuegoDB();
        CategoriaDB categoriaDB = new CategoriaDB();
        Optional<Categoria> categoriaOptional = categoriaDB.verificarNuevoNombre(categoriaVideojuego.getNombre());
        if (categoriaOptional.isEmpty()) {
            throw new UserDataInvalidException("La categoria seleccionada no existe, verifique que la categoria ya este creada antes de asociarla con un videojuego");
        }
        categoriaVideojuego.setCategoria_id(categoriaOptional.get().getCategoria_id());
        if (categoriaVideojuegoDB.existeCategoriaVideojuego(categoriaVideojuego.getVideojuego_id(), categoriaVideojuego.getCategoria_id())) {
            throw new EntityAlreadyExistsException("El videojuego ya cuenta con la categoria seleccionada");
        }
        categoriaVideojuegoDB.insertar(categoriaVideojuego);
        
    }

    private CategoriaVideojuego extraerDatos(CategoriaVideojuegoRequest categoriaVideojuegoRequest) throws UserDataInvalidException{
        CategoriaVideojuego categoriaVideojuego = new CategoriaVideojuego(categoriaVideojuegoRequest.getVideojuego_id(), categoriaVideojuegoRequest.getNombre());
        if (!categoriaVideojuego.isValid()) {
            throw new UserDataInvalidException("Error en los datos enviados, vuelva a intentar");
        }
        return categoriaVideojuego;
    }
    
    public List<CategoriaVideojuego> obtenerLasCategoriasDeUnVideojuego(int videojuego_id) throws SQLException {
        CategoriaVideojuegoDB categoriaVideojuegoDB = new CategoriaVideojuegoDB();
        return categoriaVideojuegoDB.obtenerLasCategoriasDeUnVideojuego(videojuego_id);
    }
    
    public void eliminarCategoriaVideojuego(int videojuego_id, int categoria_id) throws SQLException, UserDataInvalidException {
        CategoriaVideojuegoDB categoriaVideojuegoDB = new CategoriaVideojuegoDB();
        if (!categoriaVideojuegoDB.existeCategoriaVideojuego(videojuego_id, categoria_id)) {
            throw new UserDataInvalidException("La categoria que desea eliminar del videojuego, no existe");
        }
        categoriaVideojuegoDB.eliminar(videojuego_id, categoria_id);
    }
    
}
