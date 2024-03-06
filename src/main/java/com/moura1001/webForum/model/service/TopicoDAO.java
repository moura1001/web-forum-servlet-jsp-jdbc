package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Topico;
import java.util.List;

public interface TopicoDAO {

    // insere um novo tópico no banco de dados
    public void inserir(Topico t);

    // recupera o tópico de um usuário pelo título
    public Topico recuperar(String loginUsuario, String titulo);

    // retorna a lista de tópicos ordenada pela ordem de inserção (id populado pela sequência)
    public List<Topico> listarTodos();

}
