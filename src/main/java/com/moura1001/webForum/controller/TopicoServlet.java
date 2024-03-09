package com.moura1001.webForum.controller;

import com.moura1001.webForum.model.entity.Topico;
import com.moura1001.webForum.model.infra.ConfigH2Database;
import com.moura1001.webForum.model.infra.TopicoH2Database;
import com.moura1001.webForum.model.service.TopicoDAO;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/topicos", "/topicos/criar"})
public class TopicoServlet extends HttpServlet {

    private static final TopicoDAO topicoDAO = new TopicoH2Database();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String sqlSetupPath = getServletContext().getRealPath("/WEB-INF/classes/setup.sql");
        ConfigH2Database.setupDatabase(sqlSetupPath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getSession(false).getAttribute("authenticatedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if ("/topicos".equals(path)) {
            request.setAttribute("topicos", topicoDAO.listarTodos());
            request.getRequestDispatcher("/pages/topico/index.jsp").forward(request, response);
            return;
        }
        
        if ("/topicos/criar".equals(path)) {
            request.getRequestDispatcher("/pages/topico/criar.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String login = (String) request.getSession(false).getAttribute("authenticatedUser");
        if(login == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if ("/topicos/criar".equals(path)) {
            try {
                topicoDAO.inserir(new Topico(
                        request.getParameter("titulo"),
                        request.getParameter("conteudo"),
                        login,
                        ""
                ));

                response.sendRedirect(request.getContextPath() + "/topicos");
            } catch (RuntimeException e) {
                request.setAttribute("titulo", "Tópico - Erro");
                String erro = "Não foi possível criar o tópico pelo seguinte motivo: " + e.getMessage();
                request.setAttribute("erro", erro);
                request.getRequestDispatcher("/pages/erro/erro.jsp").forward(request, response);
            }
        }
    }

}
