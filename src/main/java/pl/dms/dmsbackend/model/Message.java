package pl.dms.dmsbackend.model;

import lombok.Data;
import pl.dms.dmsbackend.model.users.Worker;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Message implements Serializable, Comparable<Message> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 512)
    private String content;

    private LocalDateTime timeStamp;

    @ManyToOne
    private Worker sender;

    @Override
    public int compareTo(Message o) {
        return this.timeStamp.compareTo(o.timeStamp);
    }

    public Message(String title, String content, Worker sender) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.timeStamp = LocalDateTime.now();
    }
}
