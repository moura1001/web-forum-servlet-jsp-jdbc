package com.moura1001.webForum.model.infra;

import com.moura1001.webForum.model.entity.Topico;
import com.moura1001.webForum.model.service.TopicoDAO;
import com.moura1001.webForum.model.service.UsuarioDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TopicoH2Database implements TopicoDAO {

    private static UsuarioDAO usuarioDAO;

    public TopicoH2Database(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void inserir(Topico t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Topico recuperar(String loginUsuario, String titulo) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT t.titulo, t.conteudo, t.login, u.nome FROM topico t INNER JOIN usuario u"
                    + " ON t.login = u.login AND u.login = ? AND t.titulo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginUsuario);
            stmt.setString(2, titulo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Topico(
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
            String sql = "SELECT t.titulo, t.conteudo, t.login, u.nome FROM topico t INNER JOIN usuario u USING(login)"
                    + " ORDER BY t.id_topico DESC";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                topicos.add(new Topico(
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
