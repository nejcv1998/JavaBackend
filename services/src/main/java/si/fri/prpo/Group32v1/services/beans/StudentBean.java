package si.fri.prpo.Group32v1.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.Group32v1.entities.Student;
import si.fri.prpo.Group32v1.services.annotations.CallLogger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
public class StudentBean {

    private UUID uid = UUID.randomUUID();

    private Logger log = Logger.getLogger(StudentBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Bean initialized: " + StudentBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Bean destroyed: " + StudentBean.class.getSimpleName() + " UUID: " + uid);
    }

    @PersistenceContext(unitName = "consultations-jpa")
    private EntityManager em;

    @CallLogger
    public List<Student> getStudents() {
        List<Student> students = em.createNamedQuery("Student.getAll").getResultList();
        return students;
    }

    @CallLogger
    public List<Student> getStudents(QueryParameters query) {
        return JPAUtils.queryEntities(em, Student.class, query);
    }

    @CallLogger
    public Long getStudentsCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Student.class, query);
    }

    public List<Student> getStudentsCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> from = query.from(Student.class);
        query.select(from);
        List<Student> list = em.createQuery(query).getResultList();

        return list;

    }

    @CallLogger
    public Student getStudentById(int studentId) {
        Student student = em.find(Student.class, studentId);
        return student;
    }

    @CallLogger
    @Transactional
    public Student addStudent(Student student) {
        if (student != null) {
            em.persist(student);
        }
        return student;
    }

    @CallLogger
    @Transactional
    public Student updateStudent(int studentId, Student student) {
        Student s = getStudentById(studentId);
        student.setId(s.getId());

        em.merge(student);

        return student;
    }

    @CallLogger
    @Transactional
    public boolean removeStudent(int studentId) {
        Student student = getStudentById(studentId);

        if (student != null) {
            em.remove(student);
            return true;
        }

        return false;
    }

}