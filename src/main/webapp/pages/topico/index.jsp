<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fórum - Tópicos</title>
        <link rel="stylesheet" type="text/css" href="/web-forum-servlet-jsp-jdbc/pages/topico/index.css">
    </head>
    <body>
        <div id="container">
            <div id="container__title">
                <h1>Olá, ${login}, seja bem-vindo ao Fórum</h1>
            </div>
            <div id="container__menu">
                <a href="/web-forum-servlet-jsp-jdbc/usuarios/ranking" id="btnRanking">Ranking de interações</a>
                <a href="/web-forum-servlet-jsp-jdbc/topicos/criar" id="btnCriar">Criar novo tópico</a>
                <a href="/web-forum-servlet-jsp-jdbc/usuario/deslogar" id="btnDeslogar">Deslogar</a>
            </div>
            <div id="container__topicos">
                <c:choose>
                    <c:when test="${empty topicos}">
                        <h3>Ainda não existe nenhum tópico para ser exibido</h3>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach var="topico" items="${topicos}">
                                <a href="/web-forum-servlet-jsp-jdbc/topicos/visualizar?id=${topico.getId()}&login=${topico.getLoginUsuario()}"
                                   id="${topico.getId()}">
                                    <li>${topico.getTitulo()} - por ${topico.getNomeUsuario()}</li>
                                </a>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
</body>
</html>
