package si.fri.prpo.Group32v1.api.servlets;

import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.StudentBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    @Inject
    private StudentBean studentBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Student> students = studentBean.getStudents();

        PrintWriter writer = resp.getWriter();

        for(Student s: students) {
            writer.append(s.toString() + "\n");
        }

    }
}