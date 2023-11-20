package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ChatbotServlet", urlPatterns={"/chatbot/*"})
public class ChatbotServlet extends HttpServlet {
    private ChatbaseService chatbaseService;

    @Override
    public void init() throws ServletException {
        super.init();
        chatbaseService = new ChatbaseService();
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*"); // Allows requests from all origins. For production, replace '*' with your frontend's URL.
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setContentType("text/html");
        response.getWriter().println("<h1>Chatbot Server is Running</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        String question = request.getParameter("question");

        if (question == null || question.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Question parameter is missing or empty.");
            return;
        }

        String chatbaseResponse;
        try {
            chatbaseResponse = chatbaseService.sendMessage(question);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("application/json");
        response.getWriter().println(chatbaseResponse);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
