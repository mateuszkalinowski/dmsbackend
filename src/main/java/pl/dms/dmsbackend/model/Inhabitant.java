package pl.dms.dmsbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import pl.dms.dmsbackend.enums.UserRoleEnum;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Inhabitant extends User {

    public int roomNumber;

    public Inhabitant(String email, String password, String firstname, String lastname, int roomNumber) {
        super(email, password, firstname, lastname, new UserRole(UserRoleEnum.INHABITANT));
        this.roomNumber = roomNumber;
    }

    @OneToMany(mappedBy = "inhabitant")
    List<Request> requestList= new ArrayList<>();

}
