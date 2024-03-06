package com.moura1001.webForum.model.entity;

public class Comentario {

    private String comentario;
    private String loginUsuario;
    private int idTopico;

    public Comentario(String titulo, String login, int idTopico) {
        this.comentario = titulo;
        this.loginUsuario = login;
        this.idTopico = idTopico;
    }

    public String getTitulo() {
        return comentario;
    }

    public String getLogin() {
        return loginUsuario;
    }

    public int getIdTopico() {
        return idTopico;
    }

}
