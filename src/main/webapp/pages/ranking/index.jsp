<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fórum - Ranking</title>
        <link rel="stylesheet" type="text/css" href="/web-forum-servlet-jsp-jdbc/pages/ranking/index.css">
    </head>
    <body>
        <div id="container">
            <div id="container__title">
                <h1>Qual a sua posição no ranking?</h1>
            </div>
            <div id="container__menu">
                <a href="/web-forum-servlet-jsp-jdbc/topicos" id="btnVoltar">Voltar</a>
                <a href="/web-forum-servlet-jsp-jdbc/usuario/deslogar" id="btnDeslogar">Deslogar</a>
            </div>
            <div id="container__ranking">
                <c:choose>
                    <c:when test="${empty ranking}">
                        <h3>Ainda não existem dados suficientes para computar o ranking de interações</h3>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <thead>
                                <tr>
                                    <th>Colocação</th>
                                    <th>Nome</th>
                                    <th>Login</th>
                                    <th>Quantidade de pontos</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="usuario" items="${ranking}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}º</td>
                                        <td>${usuario.getNome()}</td>
                                        <td>${usuario.getLogin()}</td>
                                        <td>${usuario.getPontos()}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
