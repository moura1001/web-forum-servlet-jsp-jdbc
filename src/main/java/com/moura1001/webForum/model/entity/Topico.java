package com.moura1001.webForum.model.entity;

public class Topico {

    private String titulo;
    private String conteudo;
    private String loginUsuario;
    private String nomeUsuario;

    public Topico(String titulo, String conteudo, String loginUsuario, String nomeUsuario) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.loginUsuario = loginUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

}
