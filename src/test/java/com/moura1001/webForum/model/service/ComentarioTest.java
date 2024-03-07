package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Comentario;
import com.moura1001.webForum.model.infra.ComentarioH2Database;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import java.util.List;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    private ComentarioDAO comentarioDAO = new ComentarioH2Database();
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
    public void deveListarTodosOsComentariosDeUmDeterminadoTopico() {
        List<Comentario> comentarios = comentarioDAO.listarTodos(1);
        assertEquals(1, comentarios.size());
        assertEquals("maria", comentarios.get(0).getLoginUsuario());
        
        comentarios = comentarioDAO.listarTodos(2);
        assertEquals(1, comentarios.size());
        assertEquals("joao", comentarios.get(0).getLoginUsuario());
    }
}
