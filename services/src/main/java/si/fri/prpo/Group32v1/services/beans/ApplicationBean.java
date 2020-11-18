package si.fri.prpo.Group32v1.services.beans;

import si.fri.prpo.Group32v1.entities.Application;

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
public class ApplicationBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ApplicationBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + ApplicationBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + ApplicationBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;

    public List<Application> getApplications() {
        List<Application> apps = em.createNamedQuery("Application.getAll").getResultList();
        return apps;
    }

    public List<Application> getApplicationsCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Application> query = cb.createQuery(Application.class);
        Root<Application> from = query.from(Application.class);
        query.select(from);
        List<Application> list = em.createQuery(query).getResultList();

        return list;

    }

    public Application getApplicationById(int applicationId) {
        Application application = em.find(Application.class, applicationId);
        return application;
    }

    @Transactional
    public Application addApplication(Application application) {
        if (application != null) {
            em.persist(application);
        }
        return application;
    }

    @Transactional
    public Application updateApplication(int applicationId, Application application) {
        Application a = getApplicationById(applicationId);
        application.setId(a.getId());

        em.merge(application);

        return application;
    }

    @Transactional
    public boolean removeApplication(int applicationId) {
        Application application = getApplicationById(applicationId);

        if (application != null) {
            em.remove(application);
            return true;
        }

        return false;
    }

}