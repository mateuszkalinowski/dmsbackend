package pl.dms.dmsbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Announcement implements Serializable, Comparable<Announcement> {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "inhabitant_id")
    private Worker sender;

    @Override
    public int compareTo(Announcement o) {
        return this.timeStamp.compareTo(o.timeStamp);
    }

    public Announcement(String title, String content, Worker sender) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.timeStamp = LocalDateTime.now();
    }
}
