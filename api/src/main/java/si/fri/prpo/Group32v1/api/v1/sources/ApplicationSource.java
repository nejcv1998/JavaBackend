package si.fri.prpo.Group32v1.api.v1.sources;

import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.services.beans.ApplicationBean;
import si.fri.prpo.Group32v1.services.controllers.ApplicationControllerBean;
import si.fri.prpo.Group32v1.services.dtos.ApplicationDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ApplicationSource {

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private ApplicationControllerBean applicationControllerBean;

    @GET
    public Response getApplications() {
        List<Application> apps = applicationBean.getApplications();

        return Response.status(Response.Status.OK).entity(apps).build();
    }

    @GET
    @Path("{id}")
    public Response getApplicationById(@PathParam("id") Integer id) {
        Application app = applicationBean.getApplicationById(id);

        if(app == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(app).build();
        }
    }

    @POST
    public Response addApplication(ApplicationDto applicationDto) {
        return Response.status(Response.Status.CREATED).entity(applicationControllerBean.createApplication(applicationDto)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateApplication(@PathParam("id") Integer id, Application application) {
        return Response.status(Response.Status.OK).entity(applicationBean.updateApplication(id, application)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeApplication(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(applicationBean.removeApplication(id)).build();
    }
}
