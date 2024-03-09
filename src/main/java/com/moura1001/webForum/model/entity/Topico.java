package com.moura1001.webForum.model.entity;

public class Topico {

    private int id;
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
    
    public Topico(int id, String titulo, String conteudo, String loginUsuario, String nomeUsuario) {
        this(titulo, conteudo, loginUsuario, nomeUsuario);
        this.id = id;
    }
    
    public int getId() {
        return id;
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
