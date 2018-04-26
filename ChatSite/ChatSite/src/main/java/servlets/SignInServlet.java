package servlets;

import db_service.DBException;
import db_service.DBService;
import db_service.data_set.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final DBService dbService;

    public SignInServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(login == null||password == null){
            response.getWriter().println("fields can't be empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            UsersDataSet dataSet = dbService.getUser(login);
            if(dataSet == null|| !dataSet.getPassword().equals(password)){
                response.getWriter().println(dataSet);
                response.getWriter().println("Incorrect username and/or password!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            response.getWriter().println("Authorized: \"" + login + "\"");
            response.setStatus(HttpServletResponse.SC_OK);
        }catch (DBException e){
            response.getWriter().println("Something went wrong(Database)");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
