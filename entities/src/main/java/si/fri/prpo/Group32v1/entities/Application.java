package si.fri.prpo.Group32v1.entities;

import javax.persistence.*;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Application.getAll",
                        query = "SELECT a FROM Application a"),
                @NamedQuery(name = "Application.getAccepted",
                        query = "SELECT a FROM Application a WHERE a.accepted = true")
        })

public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean accepted;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    @Override
    public String toString() {
        return "Application [Id: " + id + ", Accepted: " + accepted + ", Student_id: " + student.getId() + ", Consultation_id: " + consultation.getId() + "]";
    }
}