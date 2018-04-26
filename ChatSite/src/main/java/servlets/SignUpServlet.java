package servlets;

import db_service.DBException;
import db_service.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if(login == null||password == null||email == null){
            response.getWriter().println("fields can't be empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            dbService.AddUser(login, password, email);
            response.getWriter().println("user \"" + login + "\" registered successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        }catch (DBException e){
            response.getWriter().println("Something went wrong(Database)");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
