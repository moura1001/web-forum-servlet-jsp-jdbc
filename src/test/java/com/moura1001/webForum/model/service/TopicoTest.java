package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Topico;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import com.moura1001.webForum.model.infra.TopicoH2Database;
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

public class TopicoTest {

    private TopicoDAO topicoDAO = new TopicoH2Database();
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

    @Test
    void deveRecuperarUmTopicoDeUmDeterminadoUsuarioPeloTitulo() {
        Topico topico = topicoDAO.recuperar("joao", "Tópico João");
        assertNotNull(topico);
        assertEquals("Post João", topico.getConteudo());
    }

    @Test
    void deveCadastrarUmNovoTopicoDeUmDeterminadoUsuarioEAtualizarSuaPontuacaoNoRanking() {
        try {
            topicoDAO.inserir(new Topico("Novo Tópico Maria", "Novo Post Maria", "maria", "Maria João"));
            
            IDataSet currenDataSet = jdt.getConnection().createDataSet();
            ITable currentTableUsuario = currenDataSet.getTable("USUARIO");
            ITable currentTableTopico = currenDataSet.getTable("TOPICO");
            currentTableTopico = DefaultColumnFilter.excludedColumnsTable(currentTableTopico, new String[]{"id_topico"});
            
            FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
            IDataSet expectedDataset = loader.load("/insert_topico.xml");
            ITable expectedTableUsuario = expectedDataset.getTable("USUARIO");
            ITable expectedTableTopico = expectedDataset.getTable("TOPICO");

            Assertion.assertEquals(expectedTableUsuario, currentTableUsuario);
            Assertion.assertEquals(expectedTableTopico, currentTableTopico);
        } catch (Exception e) {
            fail(e);
        }
    }
}
