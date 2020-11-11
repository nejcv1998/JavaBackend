package si.fri.prpo.Group32v1.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Professor.getAll",
                        query = "SELECT p FROM Professor p"),
                @NamedQuery(name = "Professor.findByName",
                        query = "SELECT p FROM Professor p WHERE p.name = :name AND p.surname = :surname")
        })

public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String Cabinet;

    @Column(name = "user_name")
    private String userName;

    @ManyToMany
    @JoinColumn(name = "consultation_list")
    private List<Consultation> consultationList;

    //getter setter methods


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCabinet() {
        return Cabinet;
    }

    public void setCabinet(String cabinet) {
        Cabinet = cabinet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }
}
