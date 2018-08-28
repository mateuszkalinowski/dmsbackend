package pl.dms.dmsbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import pl.dms.dmsbackend.enums.UserRoleEnum;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Worker extends User implements Serializable {

    private String phoneNumber;

    public Worker(String email, String password, String firstname, String lastname, String phoneNumber) {
        super(email, password, firstname, lastname, new UserRole(UserRoleEnum.WORKER));
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    List<Announcement> announcementList= new ArrayList<>();
}
