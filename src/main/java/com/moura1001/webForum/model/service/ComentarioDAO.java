package com.moura1001.webForum.model.service;

import com.moura1001.webForum.model.entity.Comentario;
import java.util.List;

public interface ComentarioDAO {

    // insere um novo comentário no banco de dados
    public void inserir(Comentario c);

    // retorna a lista de comentários de um tópico ordenada pela ordem de inserção (id populado pela sequência)
    public List<Comentario> listarTodos(int idTopico);

}
