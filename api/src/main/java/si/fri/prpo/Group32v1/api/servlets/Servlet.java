package si.fri.prpo.Group32v1.api.servlets;

import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.entities.Professor;
import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.beans.ConsultationBean;
import si.fri.prpo.Group32v1.services.beans.ProfessorBean;
import si.fri.prpo.Group32v1.services.beans.StudentBean;
import si.fri.prpo.Group32v1.services.controllers.ApplicationControllerBean;
import si.fri.prpo.Group32v1.services.controllers.ConsultationControllerBean;
import si.fri.prpo.Group32v1.services.dtos.ApplicationDto;
import si.fri.prpo.Group32v1.services.dtos.ConsultationDto;

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

    @Inject
    private ProfessorBean professorBean;

    @Inject
    private ConsultationBean consultationBean;

    @Inject
    private ApplicationControllerBean applicationControllerBean;

    @Inject
    private ConsultationControllerBean consultationControllerBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Student> students = studentBean.getStudents();
        List<Application> apps;

        PrintWriter writer = resp.getWriter();

        ApplicationDto app = new ApplicationDto();
        app.setStudentId(1);
        app.setConsultationId(2);
        app.setAccepted(true);
        applicationControllerBean.createApplication(app);


        writer.append("Students:\n");
        for (Student s : students) {
            apps = s.getApplicationList();
            writer.append(s.toString() + "\n");
            writer.append("   Applications:\n");
            for (Application a : apps) {
                writer.append("   " + a.toString() + "\n");
            }
        }

        List<Professor> profs = professorBean.getProfessorsCriteriaAPI();
        List<Consultation> cons;

        writer.append("\nProfessors:\n");
        for (Professor p : profs) {
            cons = p.getConsultationList();
            writer.append(p.toString() + "\n");
            writer.append("   Consultations:\n");
            for (Consultation c : cons) {
                writer.append("   " + c.toString() + "\n");
            }
        }

        ConsultationDto consultationDto = new ConsultationDto();
        consultationDto.setName("govorilne ure");
        consultationDto.setDescription("pogovarjal se bomo");
        consultationDto.setTimeStart("2020-12-01T09:00:00");
        consultationDto.setTimeEnd("2020-12-01T12:00:00");
        consultationDto.setMaxParticipants(5);
        consultationDto.setProfessorId(2);

        consultationControllerBean.createConsultationsFromTo(consultationDto, 30);

        writer.append("\nConsultations after adding:\n");
        List<Consultation> consultations = consultationBean.getConsultations();
        for(Consultation c: consultations) {
            writer.append("   " + c.toString() + "\n");
        }
    }
}