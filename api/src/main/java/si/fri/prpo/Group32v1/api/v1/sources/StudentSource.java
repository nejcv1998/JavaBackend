package si.fri.prpo.Group32v1.api.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.beans.StudentBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentSource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private StudentBean studentBean;

    @Operation(description = "Get student", summary = "Get students")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Students fetched successfully"
            )
    })
    @RolesAllowed("admin")
    @GET
    public Response getStudents() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long studentsCount = studentBean.getStudentsCount(query);

        return Response
                .ok(studentBean.getStudents(query))
                .header("X-Total-Count", studentsCount)
                .build();
    }

    @Operation(description = "Get student", summary = "Get student with id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Students fetched successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @GET
    @Path("{id}")
    public Response getStudentById(@PathParam("id") Integer id) {
        Student student = studentBean.getStudentById(id);

        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(student).build();
        }
    }

    @Operation(description = "Create student", summary = "Create new student")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Student created successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @POST
    public Response addStudent(@RequestBody(
            description = "DTO object for Student creation.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Student.class)))
                                       Student student) {

        return Response.status(Response.Status.CREATED).entity(studentBean.addStudent(student)).build();
    }

    @Operation(description = "Update student", summary = "Update student with id")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Student with id updated successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @PUT
    @Path("{id}")
    public Response updateStudent(@PathParam("id") Integer id, Student student) {
        return Response.status(Response.Status.OK).entity(studentBean.updateStudent(id, student)).build();
    }

    @Operation(description = "Delete student", summary = "Delete student with id")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Student with id deleted successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response removeStudent(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(studentBean.removeStudent(id)).build();
    }
}
