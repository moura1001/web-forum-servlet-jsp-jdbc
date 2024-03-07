package com.moura1001.webForum.model.infra;

import com.moura1001.webForum.model.entity.Usuario;
import com.moura1001.webForum.model.service.UsuarioDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioH2Database implements UsuarioDAO {

    @Override
    public void inserir(Usuario u) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getNome());
            stmt.setString(4, u.getSenha());
            stmt.setInt(5, u.getPontos());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }
    }

    @Override
    public Usuario recuperar(String login) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT * FROM usuario WHERE login = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("login"),
                        rs.getString("email"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getInt("pontos")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }
    }

    @Override
    public void adicionarPontos(String login, int pontos) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pontos);
            stmt.setString(2, login);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }
    }

    @Override
    public List<Usuario> ranking() {
        List<Usuario> usuarios = new ArrayList<>(5);

        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM usuario ORDER BY pontos DESC";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("login"),
                        rs.getString("email"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getInt("pontos")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);
        }

        return usuarios;
    }

    @Override
    public void autenticarUsuario(String login, String senha) {
        try (Connection conn = DriverManager.getConnection(ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD)) {

            String sql = "SELECT 1 FROM usuario WHERE login = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next())
                throw new RuntimeException("Não foi possível autenticar o usuário");

        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível executar o acesso", e);        
        }
    }

}
