package si.fri.prpo.Group32v1.api.v1.sources;

import si.fri.prpo.Group32v1.entities.Professor;
import si.fri.prpo.Group32v1.services.beans.ProfessorBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProfessorSource {

    @Inject
    private ProfessorBean professorBean;

    @GET
    public Response getProfessors() {
        List<Professor> profs = professorBean.getProfessors();
        return Response.status(Response.Status.OK).entity(profs).build();
    }

    @GET
    @Path("{id}")
    public Response getProfessorById(@PathParam("id") Integer id) {
        Professor prof = professorBean.getProfessorById(id);

        if(prof == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(prof).build();
        }
    }

    @POST
    public Response addProfessor(Professor professor) {
        return Response.status(Response.Status.CREATED).entity(professorBean.addProfessor(professor)).build();
    }

    @PUT
    public Response updateProfessor(@PathParam("id") Integer id, Professor professor) {
        return Response.status(Response.Status.OK).entity(professorBean.updateProfessor(id, professor)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeProfessor(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(professorBean.removeProfessor(id)).build();
    }
}
