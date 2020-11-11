package si.fri.prpo.Group32v1.services;

import si.fri.prpo.Group32v1.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class StudentBean {

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;
    private Student Student;

    public List<Student> getStudents() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Student> students = cb.createQuery(Student.class);
        Root<Student> from = students.from(Student.class);
        students.select(from);

        TypedQuery<Student> query = em.createQuery(students);
        List<Student> list = query.getResultList();

        return list;

    }
}