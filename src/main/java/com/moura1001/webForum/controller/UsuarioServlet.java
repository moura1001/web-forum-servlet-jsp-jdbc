package com.moura1001.webForum.controller;

import com.moura1001.webForum.model.entity.Usuario;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import com.moura1001.webForum.model.infra.UsuarioH2Database;
import com.moura1001.webForum.model.service.UsuarioDAO;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/usuario/autenticar", "/usuario/cadastrar", "/usuario/criarConta",
    "/usuario/deslogar", "/usuarios/ranking"
})
public class UsuarioServlet extends HttpServlet {

    private static final UsuarioDAO usuarioDAO = new UsuarioH2Database();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String sqlSetupPath = getServletContext().getRealPath("/WEB-INF/classes/setup.sql");
        ConfigH2Database.setupDatabase(sqlSetupPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if ("/usuario/deslogar".equals(path)) {
            request.getSession(true).setAttribute("authenticatedUser", null);
            response.sendRedirect(request.getContextPath() + "/");
        }

        if (request.getSession(true).getAttribute("authenticatedUser") != null) {

            if ("/usuarios/ranking".equals(path)) {
                try {
                    request.setAttribute("ranking", usuarioDAO.ranking());
                    request.getRequestDispatcher("/pages/ranking/index.jsp").forward(request, response);
                } catch (RuntimeException e) {
                    request.setAttribute("titulo", "Ranking - Erro");
                    String erro = "Não foi possível obter informações sobre o ranking de interações pelo seguinte motivo: " + e.getMessage();
                    request.setAttribute("erro", erro);
                    request.getRequestDispatcher("/pages/erro/erro.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/topicos");
            }
            return;
        }

        if ("/usuario/cadastrar".equals(path)) {
            request.getRequestDispatcher("/pages/cadastro/index.html").forward(request, response);
            return;
        }

        if ("/usuario/autenticar".equals(path)) {
            try {
                usuarioDAO.autenticarUsuario(
                        request.getParameter("login"),
                        request.getParameter("senha")
                );

                request.getSession(true).setAttribute("authenticatedUser", request.getParameter("login"));

                response.sendRedirect(request.getContextPath() + "/topicos");
            } catch (RuntimeException e) {
                request.setAttribute("titulo", "Login - Erro");
                String erro = "Ocorreu um erro ao tentar logar na conta: " + e.getMessage();
                request.setAttribute("erro", erro);
                request.getRequestDispatcher("/pages/erro/erro.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if ("/usuario/criarConta".equals(path)) {
            try {
                usuarioDAO.inserir(new Usuario(
                        request.getParameter("login"),
                        request.getParameter("email"),
                        request.getParameter("nome"),
                        request.getParameter("senha")
                ));

                response.sendRedirect(request.getContextPath() + "/");
            } catch (RuntimeException e) {
                request.setAttribute("titulo", "Cadastro - Erro");
                String erro = "Não foi possível realizar o cadastro da conta pelo seguinte motivo: " + e.getMessage();
                request.setAttribute("erro", erro);
                request.getRequestDispatcher("/pages/erro/erro.jsp").forward(request, response);
            }
        }
    }

}
