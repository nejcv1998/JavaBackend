package si.fri.prpo.Group32v1.services.beans;


import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.entities.Consultation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ConsultationBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ConsultationBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + ConsultationBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + ConsultationBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;

    public List<Consultation> getConsultations() {
        List<Consultation> cons = em.createNamedQuery("Consultation.getAll").getResultList();
        return cons;
    }

    public List<Consultation> getConsultationsCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consultation> query = cb.createQuery(Consultation.class);
        Root<Consultation> from = query.from(Consultation.class);
        query.select(from);
        List<Consultation> list = em.createQuery(query).getResultList();

        return list;

    }

    public Consultation getConsultationById(int consultationId) {
        Consultation cons = em.find(Consultation.class, consultationId);
        return cons;
    }

    @Transactional
    public Consultation addConsultation(Consultation consultation) {
        if (consultation != null) {
            em.persist(consultation);
        }

        return consultation;
    }

    @Transactional
    public Consultation updateConsultation(int consultationId, Consultation consultation) {
        Consultation c = getConsultationById(consultationId);
        consultation.setId(c.getId());
        em.merge(consultation);
        return consultation;
    }

    @Transactional
    public boolean removeConsultation(int consultationId) {
        Consultation cons = getConsultationById(consultationId);

        if (cons != null) {
            em.remove(cons);
            return true;
        }
        return false;
    }
}