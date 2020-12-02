package si.fri.prpo.Group32v1.api.v1.sources;

import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.services.beans.ConsultationBean;
import si.fri.prpo.Group32v1.services.controllers.ConsultationControllerBean;
import si.fri.prpo.Group32v1.services.dtos.ConsultationDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("consultations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ConsultationSource {

    @Inject
    private ConsultationBean consultationBean;

    @Inject
    private ConsultationControllerBean consultationControllerBean;

    @GET
    public Response getConsultations() {
        List<Consultation> cons = consultationBean.getConsultations();

        return Response.status(Response.Status.OK).entity(cons).build();
    }

    @GET
    @Path("{id}")
    public Response getConsultationById(@PathParam("id") Integer id) {
        Consultation consultation = consultationBean.getConsultationById(id);

        if (consultation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(consultation).build();
        }
    }

    @POST
    public Response addConsultation(ConsultationDto consultationDto) {
        return Response.status(Response.Status.CREATED).entity(consultationControllerBean.createConsultation(consultationDto)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateConsultation(@PathParam("id") Integer id, Consultation consultation) {
        return Response.status(Response.Status.OK).entity(consultationBean.updateConsultation(id, consultation)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeConsultation(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(consultationBean.removeConsultation(id)).build();
    }
}
