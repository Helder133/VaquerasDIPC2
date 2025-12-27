/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.db.comentario;

import ipc2_vaqueras.vaquerasdipc2.db.CRUD;
import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.models.comentario.Comentario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author helder
 */
public class ComentarioDB implements CRUD<Comentario> {

    private final static String INSERTAR_COMENTARIO = "INSERT INTO comentario_videojuego (usuario_id, videojuego_id, comentario, fecha_hora) VALUES (?,?,?,?)";
    private final static String INSERTAR_COMENTARIO_HIJO = "INSERT INTO comentario_videojuego (usuario_id, videojuego_id, comentario, fecha_hora, comentario_padre) VALUES (?,?,?,?,?)";
    private final static String SELECCIONAR_LOS_COMENTARIOS_DE_UN_VIDEOJUEGO = "select c.*, u.nombre from comentario_videojuego c join usuario u on c.usuario_id = u.usuario_id where c.videojuego_id = ?";
    private final static String SELECCIONAR_LOS_COMENTARIOS_DE_UN_VIDEOJUEGO_VISIBLES = "select c.*, u.nombre from comentario_videojuego c join usuario u on c.usuario_id = u.usuario_id where c.videojuego_id = ? AND c.visible = 1";
    private final static String ACTUALIZAR_UN_COMENTARIO = "UPDATE comentario_videojuego SET comentario = ?, visible = ? WHERE comentario_id = ?";

    @Override
    public void insertar(Comentario t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        if (t.getComentario_padre() <= 0) {
            try (PreparedStatement insert = connection.prepareStatement(INSERTAR_COMENTARIO)) {
                insert.setInt(1, t.getUsuario_id());
                insert.setInt(2, t.getVideojuego_id());
                insert.setString(3, t.getComentario());
                insert.setTimestamp(4, Timestamp.valueOf(t.getFecha_hora()));
                
                insert.executeUpdate();
            }
        } else {
            try (PreparedStatement insert = connection.prepareStatement(INSERTAR_COMENTARIO_HIJO)) {
                insert.setInt(1, t.getUsuario_id());
                insert.setInt(2, t.getVideojuego_id());
                insert.setString(3, t.getComentario());
                insert.setTimestamp(4, Timestamp.valueOf(t.getFecha_hora()));
                insert.setInt(5, t.getComentario_padre());
            
                insert.executeUpdate();
            }
        }

    }

    @Override
    public void actualizar(Comentario t) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_UN_COMENTARIO)) {
            update.setString(1, t.getComentario());
            update.setBoolean(2, t.isVisible());
            update.setInt(3, t.getComentario_id());
            
            update.executeUpdate();
        }
    }

    @Override
    public List<Comentario> seleccionar() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Comentario> seleccionarPorParametro(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Comentario> seleccionarPorParametro(String t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
