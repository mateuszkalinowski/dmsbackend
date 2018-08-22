package pl.dms.dmsbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    private String description;

    public UserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }
}
