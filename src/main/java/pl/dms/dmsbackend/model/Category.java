package pl.dms.dmsbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Category implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Request> requestList = new ArrayList<>();
    public Category(String category) {
        this.category = category;
    }
}
