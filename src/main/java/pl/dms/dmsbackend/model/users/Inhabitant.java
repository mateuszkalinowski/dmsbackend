package pl.dms.dmsbackend.model.users;

import lombok.Data;
import pl.dms.dmsbackend.model.Task;
import pl.dms.dmsbackend.model.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Inhabitant extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public int roomNumber;

    public Inhabitant(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @OneToMany(mappedBy = "inhabitant")
    List<Task> taskList = new ArrayList<>();

    public Inhabitant(String email, String password, String firstname, String lastname, UserRole role, int roomNumber) {
        super(email, password, firstname, lastname, role);
        this.roomNumber = roomNumber;
    }
}
