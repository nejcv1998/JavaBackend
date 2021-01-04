package si.fri.prpo.Group32v1.services.beans;


import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.Group32v1.entities.Channel;
import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.services.annotations.CallLogger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ConsultationBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ConsultationBean.class.getName());

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + ConsultationBean.class.getSimpleName() + " UUID: " + uid);

        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("integrations.channels.url").orElse("http://localhost:8081/v1/communication");
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + ConsultationBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;

    @CallLogger
    public List<Consultation> getConsultations() {
        List<Consultation> cons = em.createNamedQuery("Consultation.getAll").getResultList();
        return cons;
    }

    @CallLogger
    public List<Consultation> getConsultations(QueryParameters query) {
        return JPAUtils.queryEntities(em, Consultation.class, query);
    }

    @CallLogger
    public Long getConsultationsCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Consultation.class, query);
    }

    public List<Consultation> getConsultationsCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Consultation> query = cb.createQuery(Consultation.class);
        Root<Consultation> from = query.from(Consultation.class);
        query.select(from);
        List<Consultation> list = em.createQuery(query).getResultList();

        return list;

    }

    @CallLogger
    public Consultation getConsultationById(int consultationId) {
        Consultation cons = em.find(Consultation.class, consultationId);
        return cons;
    }

    @CallLogger
    @Transactional
    public Consultation addConsultation(Consultation consultation) {
        if (consultation != null) {
            em.persist(consultation);
        }

        return consultation;
    }

    @CallLogger
    @Transactional
    public Consultation updateConsultation(int consultationId, Consultation consultation) {
        Consultation c = getConsultationById(consultationId);
        consultation.setId(c.getId());
        em.merge(consultation);
        return consultation;
    }

    @CallLogger
    @Transactional
    public boolean removeConsultation(int consultationId) {
        Consultation cons = getConsultationById(consultationId);

        if (cons != null) {
            em.remove(cons);
            return true;
        }
        return false;
    }

    @CallLogger
    public List<Channel> getChannels() {
        return httpClient.target(baseUrl).request().get(new GenericType<List<Channel>>(){});
    }
}