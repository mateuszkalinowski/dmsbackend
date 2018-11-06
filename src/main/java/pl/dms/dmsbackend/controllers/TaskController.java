package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.enumTranslation.RequestStatusTranslation;
import pl.dms.dmsbackend.model.users.User;
import pl.dms.dmsbackend.repositories.UserRepository;
import pl.dms.dmsbackend.utils.dataInput.NewRequestDTO;
import pl.dms.dmsbackend.utils.dataOutput.RequestDTO;
import pl.dms.dmsbackend.enums.RequestStatusEnum;
import pl.dms.dmsbackend.model.users.Inhabitant;
import pl.dms.dmsbackend.model.Task;
import pl.dms.dmsbackend.repositories.CategoryRepository;
import pl.dms.dmsbackend.repositories.InhabitantRepository;
import pl.dms.dmsbackend.repositories.TaskRepository;
import pl.dms.dmsbackend.utils.Page;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/request")
@Transactional
public class TaskController {

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity addRequest(@RequestBody NewRequestDTO newRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            Inhabitant inhabitant = inhabitantRepository.findByEmail(currentEmail);
            if(inhabitant==null)
                return ResponseEntity.badRequest().build();
            Task task = new Task();
            task.setInhabitant(inhabitant);
            task.setCategory(categoryRepository.getTopByCategory(newRequestDTO.getCategory()));
            task.setStatus(RequestStatusEnum.REQUEST_WAITING);
            task.setContent(newRequestDTO.getDescription());
            task.setTitle(newRequestDTO.getTitle());
            task.setComment("");
            task.setTimeStamp(LocalDateTime.now());

            taskRepository.save(task);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRequest(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User inhabitant = userRepository.findByEmail(currentEmail);
        if(inhabitant==null)
            return ResponseEntity.badRequest().build();

        Task task = taskRepository.findById(id).orElse(null);

        if(task.getInhabitant().equals(inhabitant)) {
            taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.badRequest().build();

    }

    @GetMapping
    public ResponseEntity getAllRequests(@RequestParam int page, @RequestParam int size) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            Inhabitant inhabitant = inhabitantRepository.findByEmail(currentEmail);
            if (inhabitant == null)
                return ResponseEntity.badRequest().build();

            List<RequestDTO> requestDTOList = new ArrayList<>();

            for (Task task : inhabitant.getTaskList()) {
                requestDTOList.add(new RequestDTO(task.getId(), task.getTitle(), task.getContent(), task.getTimeStamp(), task.getComment(), task.getCategory().getCategory(), task.getStatus().name()));
            }

            return ResponseEntity.ok().body(Page.createPage(page, size,requestDTOList));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getRequest(@PathVariable Long id){

        Task task = taskRepository.findById(id).orElse(null);
        HashMap<String,Object> taskMap = new HashMap<>();
        taskMap.put("id",task.getId());
        taskMap.put("title",task.getTitle());
        taskMap.put("timeStamp",task.getTimeStamp().format(DateTimeFormatter.ofPattern("mm:hh dd/MM/yyyy")));
        taskMap.put("comment",task.getComment());
        taskMap.put("content",task.getContent());
        taskMap.put("category",task.getCategory().getCategory());
        taskMap.put("status", RequestStatusTranslation.translateRequestStatus(task.getStatus()));

        return ResponseEntity.ok(taskMap);
    }
}
