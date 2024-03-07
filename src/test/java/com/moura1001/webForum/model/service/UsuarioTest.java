package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Usuario;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import com.moura1001.webForum.model.infra.UsuarioH2Database;
import java.util.List;
import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private JdbcDatabaseTester jdt;
    private UsuarioDAO usuarioDAO = new UsuarioH2Database();

    @BeforeEach
    void setUp() {
        try {
            jdt = new JdbcDatabaseTester(ConfigH2Database.JDBC_DRIVER, ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD);
            FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
            jdt.setDataSet(loader.load("/init.xml"));
            jdt.onSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deveRecuperarTodosOsUsuarios() {
        List<Usuario> usuarios = usuarioDAO.ranking();
        assertEquals(2, usuarios.size());
        assertEquals("joao", usuarios.get(0).getLogin());
        assertEquals("maria", usuarios.get(1).getLogin());
    }

    @Test
    void deveRecuperarUmUsuarioPeloSeuLogin() {
        Usuario usuario = usuarioDAO.recuperar("maria");
        assertNotNull(usuario);
        assertEquals("Maria João", usuario.getNome());
    }

    @Test
    void deveInserirUmNovoUsuario() {
        usuarioDAO.inserir(new Usuario("newuser", "newuser@email.com", "New User", "789", 2048));
        try {
            IDataSet currenDataSet = jdt.getConnection().createDataSet();
            ITable currentTable = currenDataSet.getTable("USUARIO");

            FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
            IDataSet expectedDataset = loader.load("/insert_usuario.xml");
            ITable expectedTable = expectedDataset.getTable("USUARIO");

            Assertion.assertEquals(expectedTable, currentTable);
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void deveAutenticarUsuarioAPartirDoLoginESenha() {
        usuarioDAO.autenticarUsuario("joao", "123");
    }

}
