/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaquerad.vaquerasdipc2.db;

import ipc2_vaquerad.vaquerasdipc2.models.usuario.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 * @param <T>
 */
public interface CRUD<T> {

    public void insertar(T t) throws SQLException;

    public void actualizar(T t) throws SQLException;

    public List<T> seleccionar() throws SQLException;

    public Optional<Usuario> seleccionarPorParametro(int t) throws SQLException;

    public List<T> seleccionarPorParametro(String t) throws SQLException;

    public void eleiminar(int t) throws SQLException;
}
