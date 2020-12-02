package si.fri.prpo.Group32v1.services.controllers;

import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.annotations.CallLogger;
import si.fri.prpo.Group32v1.services.beans.ApplicationBean;
import si.fri.prpo.Group32v1.services.beans.ConsultationBean;
import si.fri.prpo.Group32v1.services.beans.StudentBean;
import si.fri.prpo.Group32v1.services.dtos.ApplicationDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.ApplicationPath;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class ApplicationControllerBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ApplicationControllerBean.class.getName());

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private StudentBean studentBean;

    @Inject
    private ConsultationBean consultationBean;

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + ApplicationControllerBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + ApplicationControllerBean.class.getSimpleName() + " UUID: " + uid);
    }

    @CallLogger
    @Transactional
    public Application createApplication(ApplicationDto applicationDto) {
        Student student = studentBean.getStudentById(applicationDto.getStudentId());
        Consultation consultation = consultationBean.getConsultationById(applicationDto.getConsultationId());

        if (student == null) {
            log.info("Cannot create an application: Student does not exist");
            return null;
        }
        if (consultation == null) {
            log.info("Cannot create an application: Consultation does not exist");
            return null;
        }

        Application application = new Application();
        application.setStudent(student);
        application.setConsultation(consultation);
        application.setAccepted(applicationDto.isAccepted());

        application = applicationBean.addApplication(application);

        return application;
    }

}
