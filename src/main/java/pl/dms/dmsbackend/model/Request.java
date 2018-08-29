package pl.dms.dmsbackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import pl.dms.dmsbackend.enums.RequestStatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Request implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inhabitant_id")
    private Inhabitant inhabitant;

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    private String title;

    @Column(length = 512)
    private String content;

    @Column(length = 512)
    private String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

}
