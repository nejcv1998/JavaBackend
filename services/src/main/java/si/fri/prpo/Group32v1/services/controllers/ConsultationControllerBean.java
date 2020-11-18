package si.fri.prpo.Group32v1.services.controllers;

import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.entities.Professor;
import si.fri.prpo.Group32v1.services.beans.ApplicationBean;
import si.fri.prpo.Group32v1.services.beans.ConsultationBean;
import si.fri.prpo.Group32v1.services.beans.ProfessorBean;
import si.fri.prpo.Group32v1.services.dtos.ConsultationDto;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ConsultationControllerBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ApplicationControllerBean.class.getName());

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private ProfessorBean professorBean;

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

    @Transactional
    public Consultation createConsultation(ConsultationDto consultationDto) {
        Professor prof = professorBean.getProfessorById(consultationDto.getProfessorId());

        if (prof == null) {
            log.info("Cannot create consultation. Professor does not exist");
            return null;
        }

        Consultation cons = new Consultation();
        cons.setName(consultationDto.getName());
        cons.setDescription(consultationDto.getDescription());
        cons.setTimeStart(consultationDto.getTimeStart());
        cons.setTimeEnd(consultationDto.getTimeEnd());
        cons.setMaxParticipants(consultationDto.getMaxParticipants());
        cons.setProfessor(prof);
        cons.setApplicationList(consultationDto.getApplicationList());

        return cons;
    }

    @Transactional
    public boolean createConsultationsFromTo(ConsultationDto consultationDto, int consLengthMinutes) {
        Professor prof = professorBean.getProfessorById(consultationDto.getProfessorId());
        LocalDateTime time = LocalDateTime.parse(consultationDto.getTimeStart());
        LocalDateTime timeEnd = LocalDateTime.parse(consultationDto.getTimeEnd());

        if(prof == null) {
            log.info("Cannot create consultation sequence. Professor does not exist");
            return false;
        }

        for(LocalDateTime i = time; i.isBefore(timeEnd); i = i.plusMinutes(consLengthMinutes)) {
            Consultation cons = new Consultation();
            cons.setName(consultationDto.getName());
            cons.setDescription(consultationDto.getDescription());
            cons.setMaxParticipants(consultationDto.getMaxParticipants());
            cons.setProfessor(prof);
            cons.setApplicationList(consultationDto.getApplicationList());

            cons.setTimeStart(i.toString());
            cons.setTimeEnd(i.plusMinutes(consLengthMinutes).toString());

            cons = consultationBean.addConsultation(cons);
        }
        return true;
    }
}
