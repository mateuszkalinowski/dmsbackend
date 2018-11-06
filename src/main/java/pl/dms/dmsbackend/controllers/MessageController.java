package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.utils.dataOutput.AnnouncementDTO;
import pl.dms.dmsbackend.model.Message;
import pl.dms.dmsbackend.repositories.MessageRepository;
import pl.dms.dmsbackend.utils.Page;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/announcement")
@Transactional
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public ResponseEntity getAllAnnouncements(@RequestParam int page, @RequestParam int size){

        if(size==0)
            return ResponseEntity.badRequest().build();

        List<Message> messageList = messageRepository.findAll();

        List<AnnouncementDTO> announcementDTOList = new ArrayList<>();


        for(Message message : messageList) {
            AnnouncementDTO announcementDTO = new AnnouncementDTO(message.getTitle(), message.getContent(), message.getTimeStamp(), message.getSender().getEmail(), message.getSender().getFirstName(), message.getSender().getLastName());
            announcementDTOList.add(announcementDTO);
        }

        Collections.sort(announcementDTOList);

        return ResponseEntity.ok().body(Page.createPage(page,size,announcementDTOList));
    }

}
