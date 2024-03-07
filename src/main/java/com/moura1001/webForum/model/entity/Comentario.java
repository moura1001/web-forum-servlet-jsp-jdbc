package com.moura1001.webForum.model.entity;

public class Comentario {

    private String comentario;
    private String loginUsuario;
    private String nomeUsuario;
    private int idTopico;

    public Comentario(String conteudo, String loginUsuario, String nomeUsuario, int idTopico) {
        this.comentario = conteudo;
        this.loginUsuario = loginUsuario;
        this.nomeUsuario = nomeUsuario;
        this.idTopico = idTopico;
    }

    public String getComentario() {
        return comentario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getIdTopico() {
        return idTopico;
    }

}
