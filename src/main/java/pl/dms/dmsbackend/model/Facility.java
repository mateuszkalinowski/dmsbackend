package pl.dms.dmsbackend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Facility {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

}
