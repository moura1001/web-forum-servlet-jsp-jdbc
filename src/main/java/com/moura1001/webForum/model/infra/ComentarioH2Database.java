package com.moura1001.webForum.model.infra;

import com.moura1001.webForum.model.entity.Comentario;
import com.moura1001.webForum.model.service.ComentarioDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioH2Database implements ComentarioDAO {

    @Override
    public void inserir(Comentario c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Comentario> listarTodos(int idTopico) {
        List<Comentario> comentarios = new ArrayList<>(5);

        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT c.comentario, c.login, t.id_topico, u.nome FROM"
                    + " topico t INNER JOIN comentario c ON t.id_topico = c.id_topico AND t.id_topico = ?"
                    + " INNER JOIN usuario u USING(login)"
                    + " ORDER BY c.id_comentario DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTopico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comentarios.add(new Comentario(
                        rs.getString("comentario"),
                        rs.getString("login"),
                        rs.getString("nome"),
                        rs.getInt("id_topico")
                ));
            }

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }

        return comentarios;
    }

}
