package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Topico;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import com.moura1001.webForum.model.infra.TopicoH2Database;
import com.moura1001.webForum.model.infra.UsuarioH2Database;
import java.util.List;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TopicoTest {

    private UsuarioDAO usuarioDAO = new UsuarioH2Database();
    private TopicoDAO topicoDAO = new TopicoH2Database(usuarioDAO);
    private JdbcDatabaseTester jdt;

    @BeforeEach
    public void setUp() {
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
    public void deveListarTodosOsTopicosCadastrados() {
        List<Topico> topicos = topicoDAO.listarTodos();
        assertEquals(2, topicos.size());
        assertEquals("maria", topicos.get(0).getLoginUsuario());
        assertEquals("joao", topicos.get(1).getLoginUsuario());
    }
}
