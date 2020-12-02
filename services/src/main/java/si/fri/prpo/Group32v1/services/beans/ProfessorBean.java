package si.fri.prpo.Group32v1.services.beans;


import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.entities.Professor;
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
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ProfessorBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(ProfessorBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + ProfessorBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + ProfessorBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;

    @CallLogger
    public List<Professor> getProfessors() {
        List<Professor> profs = em.createNamedQuery("Professor.getAll").getResultList();
        return profs;
    }

    public List<Professor> getProfessorsCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Professor> query = cb.createQuery(Professor.class);
        Root<Professor> from = query.from(Professor.class);
        query.select(from);
        List<Professor> list = em.createQuery(query).getResultList();

        return list;

    }

    @CallLogger
    public Professor getProfessorById(int professorId) {
        Professor prof = em.find(Professor.class, professorId);
        return prof;
    }

    @CallLogger
    @Transactional
    public Professor addProfessor(Professor professor) {
        if (professor != null) {
            em.persist(professor);
        }

        return professor;
    }

    @CallLogger
    @Transactional
    public Professor updateProfessor(int professorId, Professor professor) {
        Professor p = getProfessorById(professorId);
        professor.setId(p.getId());
        em.merge(professor);

        return professor;
    }

    @CallLogger
    @Transactional
    public boolean removeProfessor(int professorId) {
        Professor prof = getProfessorById(professorId);
        if (prof != null) {
            em.remove(prof);
            return true;
        }

        return false;
    }
}