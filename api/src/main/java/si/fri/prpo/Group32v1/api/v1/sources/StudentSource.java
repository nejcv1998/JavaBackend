package si.fri.prpo.Group32v1.api.v1.sources;

import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.beans.StudentBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentSource {

    @Inject
    private StudentBean studentBean;

    @GET
    public Response getStudents() {
        List<Student> students = studentBean.getStudents();

        return Response.status(Response.Status.OK).entity(students).build();
    }

    @GET
    @Path("{id}")
    public Response getStudentById(@PathParam("id") Integer id) {
        Student student = studentBean.getStudentById(id);

        if(student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(student).build();
        }
    }

    @POST
    public Response addStudent(Student student) {
        return Response.status(Response.Status.CREATED).entity(studentBean.addStudent(student)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateStudent(@PathParam("id") Integer id, Student student) {
        return Response.status(Response.Status.OK).entity(studentBean.updateStudent(id, student)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeStudent(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(studentBean.removeStudent(id)).build();
    }
}
