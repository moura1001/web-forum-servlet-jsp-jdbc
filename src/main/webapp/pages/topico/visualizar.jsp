<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fórum - Conteúdo Tópico</title>
        <link rel="stylesheet" type="text/css" href="/web-forum-servlet-jsp-jdbc/pages/topico/visualizar.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.20.0/jquery.validate.min.js"></script>
    </head>
    <body>
        <div id="container">
            <div id="container__title">
                <h1>${topico.getTitulo()}</h1>
                <h3>por ${topico.getNomeUsuario()}</h3>
            </div>
            <div id="container__menu">
                <a href="/web-forum-servlet-jsp-jdbc/topicos" id="btnVoltar">Voltar</a>
                <a href="/web-forum-servlet-jsp-jdbc/usuario/deslogar" id="btnDeslogar">Deslogar</a>
            </div>
            <div id="container__topico">
                <div id="container_conteudoTopico">
                    <c:forEach var="line" items="${conteudoTopico}">
                        <p>&nbsp&nbsp${line}</p>
                    </c:forEach>
                </div>
                <div id="container__comentarios">
                    <c:choose>
                        <c:when test="${empty comentarios}">
                            <p>Ainda não existe nenhum comentário para ser exibido</p>
                        </c:when>
                        <c:otherwise>
                            <p>Comentários:</p>
                            <ul>
                                <c:forEach var="comentario" items="${comentarios}">
                                    <li>${comentario.getNomeUsuario()} comentou o seguinte: ${comentario.getComentario()}</li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div id="container__form">
                <form id="formCriar" method="post" action="/web-forum-servlet-jsp-jdbc/topicos/adicionarComentario">
                    <div id="container__userInput">
                        <input type="hidden" id="idTopico" name="idTopico" value="${topico.getId()}" />
                        <input type="hidden" id="loginTopico" name="loginTopico" value="${topico.getLoginUsuario()}" />
                        <div class="userInput">
                            <label for="conteudo">Comentário</label>
                            <textarea rows="4" cols="64"
                                required id="conteudo" name="conteudo"
                                placeholder="digite o comentário" ></textarea>
                        </div>
                    </div>
                    <input type="submit" id="btnSubmit" value="Adicionar" />
                </form>
            </div>
        </div>
        <script src="/web-forum-servlet-jsp-jdbc/pages/topico/validator.js"></script>
    </body>
</html>
