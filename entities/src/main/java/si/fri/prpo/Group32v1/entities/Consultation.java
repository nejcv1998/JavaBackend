package si.fri.prpo.Group32v1.entities;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Consultation.getAll",
                        query = "SELECT c FROM Consultation c"),
                @NamedQuery(name = "Consultation.findByName",
                        query = "SELECT c FROM Consultation c WHERE c.name = :name"),
                @NamedQuery(name = "Consultation.getUnderMax",
                        query = "SELECT c FROM Consultation c WHERE c.maxParticipants < :max"),
                @NamedQuery(name = "Consultation.findByProfessor",
                        query = "SELECT c FROM Consultation c WHERE c.professor = :professor")
        })

public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column
    private Integer maxParticipants;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @JsonbTransient
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<Application> applicationList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public String toString() {
        return "Consultation [Id: " + id + ", Name: " + name + ", Description: " + description + ", Time_start: " + timeStart + ", Time_end: " + timeEnd +
                ", MaxParticipants: " + maxParticipants + ", Professor_id: " + professor.getId() + "]";
    }
}
