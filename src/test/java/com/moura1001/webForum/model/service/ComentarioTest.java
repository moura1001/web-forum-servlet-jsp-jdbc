package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Comentario;
import com.moura1001.webForum.model.infra.ComentarioH2Database;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import java.util.List;
import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

public class ComentarioTest {

    private ComentarioDAO comentarioDAO = new ComentarioH2Database();
    private JdbcDatabaseTester jdt;
    
    @BeforeAll
    public static void init() {
        ConfigH2Database.setupDatabase("src/main/resources/setup.sql", true);
    }

    @BeforeEach
    public void setUp() {
        try {
            jdt = new JdbcDatabaseTester(ConfigH2Database.JDBC_DRIVER, ConfigH2Database.DB_URL, ConfigH2Database.USER, ConfigH2Database.PASSWORD);
            FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
            jdt.setDataSet(loader.load("/init.xml"));
            jdt.onSetup();
        } catch (Exception e) {
            throw new RuntimeException("Erro no setup do teste", e);
        }
    }

    @Test
    public void deveListarTodosOsComentariosDeUmDeterminadoTopico() {
        List<Comentario> comentarios = comentarioDAO.listarTodos(123);
        assertEquals(1, comentarios.size());
        assertEquals("maria", comentarios.get(0).getLoginUsuario());
        
        comentarios = comentarioDAO.listarTodos(234);
        assertEquals(1, comentarios.size());
        assertEquals("joao", comentarios.get(0).getLoginUsuario());
    }
    
    @Test
    void deveCadastrarUmNovoComentarioNumDeterminadoTopicoEAtualizarAPontuacaoDoUsuarioNoRanking() {
        try {
            comentarioDAO.inserir(new Comentario("Novo Comentário Maria", "maria", "Maria João", 123));
            
            IDataSet currenDataSet = jdt.getConnection().createDataSet();
            ITable currentTableUsuario = currenDataSet.getTable("USUARIO");
            ITable currentTableComentario = currenDataSet.getTable("COMENTARIO");
            currentTableComentario = DefaultColumnFilter.excludedColumnsTable(currentTableComentario, new String[]{"id_comentario"});
            
            FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
            IDataSet expectedDataset = loader.load("/insert_comentario.xml");
            ITable expectedTableUsuario = expectedDataset.getTable("USUARIO");
            ITable expectedTableComentario = expectedDataset.getTable("COMENTARIO");

            Assertion.assertEquals(expectedTableUsuario, currentTableUsuario);
            Assertion.assertEquals(expectedTableComentario, currentTableComentario);
        } catch (Exception e) {
            fail(e);
        }
    }
}
