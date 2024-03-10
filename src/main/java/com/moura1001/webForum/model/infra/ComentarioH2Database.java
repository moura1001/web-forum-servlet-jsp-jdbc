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

    private static final int QTD_INCREMENTA_PONTOS = 3;

    @Override
    public void inserir(Comentario c) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String sql = "INSERT INTO comentario(comentario, login, id_topico) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getComentario());
            stmt.setString(2, c.getLoginUsuario());
            stmt.setInt(3, c.getIdTopico());
            stmt.executeUpdate();

            sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, QTD_INCREMENTA_PONTOS);
            stmt.setString(2, c.getLoginUsuario());
            stmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Erro ao tentar reverter operação", ex);
                }
            }
            throw new RuntimeException("Não foi possível executar o acesso", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Erro ao tentar encerrar conexão", ex);
                }
            }
        }
    }

    @Override
    public List<Comentario> listarTodos(int idTopico) {
        List<Comentario> comentarios = new ArrayList<>(5);

        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT c.comentario, c.login, t.id_topico, u.nome FROM"
                    + " topico t INNER JOIN comentario c ON t.id_topico = c.id_topico AND t.id_topico = ?"
                    + " INNER JOIN usuario u ON c.login = u.login"
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
