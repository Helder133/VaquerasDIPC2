/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaquerad.vaquerasdipc2.db;

import java.util.List;

/**
 *
 * @author helder
 * @param <T>
 */
public interface CRUD <T>{
    public void insertar (T t);
    public void actualizar (T t);
    public List<T> seleccionar ();
    public T seleccionarPorParametro (int t);
    public T seleccionarPorParametro (String t);
    public void eleiminar (int t);
}
