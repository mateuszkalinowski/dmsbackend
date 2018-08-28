package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.dataOutput.AnnouncementDTO;
import pl.dms.dmsbackend.model.Announcement;
import pl.dms.dmsbackend.repositories.AnnouncementRepository;
import pl.dms.dmsbackend.utils.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @GetMapping
    public ResponseEntity getAllAnnouncements(@RequestParam int page, @RequestParam int size){

        if(size==0)
            return ResponseEntity.badRequest().build();

        List<Announcement> announcementList = announcementRepository.findAll();

        List<AnnouncementDTO> announcementDTOList = new ArrayList<>();

        for(Announcement announcement: announcementList) {
            AnnouncementDTO announcementDTO = new AnnouncementDTO(announcement.getTitle(),announcement.getContent(),announcement.getTimeStamp(),announcement.getSender().getEmail(),announcement.getSender().getFirstname(),announcement.getSender().getLastname());
            announcementDTOList.add(announcementDTO);
        }

        Collections.sort(announcementDTOList);

        return ResponseEntity.ok().body(Page.createPage(page,size,announcementDTOList));
    }

}
