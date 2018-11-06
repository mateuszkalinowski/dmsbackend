package pl.dms.dmsbackend.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.dms.dmsbackend.model.Category;
import pl.dms.dmsbackend.model.Message;
import pl.dms.dmsbackend.model.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "Worker")
public class Worker extends User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    public Worker(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    List<Message> messageList = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable( name = "Worker_Category",
            joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_category") }
    )
    private List<Category> categories = new ArrayList<>();

    public Worker(String email, String password, String firstname, String lastname, UserRole role, String phoneNumber) {
        super(email, password, firstname, lastname, role);
        this.phoneNumber = phoneNumber;
    }
}
