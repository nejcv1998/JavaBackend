package si.fri.prpo.Group32v1.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll",
                        query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.findByName",
                        query = "SELECT s FROM Student s WHERE s.name = :name")
        })

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Application> applicationList;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public String toString() {
        return "Student [Name: " + name + ", Surname: " + surname + ", Username: " + userName + "]";
    }
}