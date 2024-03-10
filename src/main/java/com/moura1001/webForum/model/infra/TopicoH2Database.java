package com.moura1001.webForum.model.infra;

import com.moura1001.webForum.model.entity.Topico;
import com.moura1001.webForum.model.service.TopicoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TopicoH2Database implements TopicoDAO {

    private static final int QTD_INCREMENTA_PONTOS = 10;

    @Override
    public void inserir(Topico t) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String sql = "INSERT INTO topico(titulo, conteudo, login) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getConteudo());
            stmt.setString(3, t.getLoginUsuario());
            stmt.executeUpdate();

            sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, QTD_INCREMENTA_PONTOS);
            stmt.setString(2, t.getLoginUsuario());
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
    public Topico recuperar(String loginUsuario, int idTopico) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT t.id_topico, t.titulo, t.conteudo, t.login, u.nome FROM topico t INNER JOIN usuario u"
                    + " ON t.login = u.login AND u.login = ? AND t.id_topico = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginUsuario);
            stmt.setInt(2, idTopico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Topico(
                        rs.getInt("id_topico"),
                        rs.getString("titulo"),
                        rs.getString("conteudo"),
                        rs.getString("login"),
                        rs.getString("nome")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }
    }

    @Override
    public List<Topico> listarTodos() {
        List<Topico> topicos = new ArrayList<>(5);

        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            Statement stmt = conn.createStatement();
            String sql = "SELECT t.id_topico, t.titulo, t.conteudo, t.login, u.nome FROM topico t INNER JOIN usuario u USING(login)"
                    + " ORDER BY t.id_topico DESC";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                topicos.add(new Topico(
                        rs.getInt("id_topico"),
                        rs.getString("titulo"),
                        rs.getString("conteudo"),
                        rs.getString("login"),
                        rs.getString("nome")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }

        return topicos;
    }

}
