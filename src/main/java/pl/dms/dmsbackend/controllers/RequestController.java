package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.dataInput.NewRequestDTO;
import pl.dms.dmsbackend.dataOutput.RequestDTO;
import pl.dms.dmsbackend.enums.RequestStatusEnum;
import pl.dms.dmsbackend.model.Inhabitant;
import pl.dms.dmsbackend.model.Request;
import pl.dms.dmsbackend.repositories.CategoryRepository;
import pl.dms.dmsbackend.repositories.InhabitantRepository;
import pl.dms.dmsbackend.repositories.RequestRepository;
import pl.dms.dmsbackend.utils.Page;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/request")
@Transactional
public class RequestController {

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity addRequest(@RequestBody NewRequestDTO newRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            Inhabitant inhabitant = inhabitantRepository.findTopByEmail(currentEmail);
            if(inhabitant==null)
                return ResponseEntity.badRequest().build();
            Request request = new Request();
            request.setInhabitant(inhabitant);
            request.setCategory(categoryRepository.getTopByCategory(newRequestDTO.getCategory()));
            request.setStatus(RequestStatusEnum.REQUEST_WAITING);
            request.setContent(newRequestDTO.getDescription());
            request.setTitle(newRequestDTO.getTitle());
            request.setComment("");
            request.setTimeStamp(LocalDateTime.now());

            requestRepository.save(request);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRequest(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Inhabitant inhabitant = inhabitantRepository.findTopByEmail(currentEmail);
        if(inhabitant==null)
            return ResponseEntity.badRequest().build();

        Request request = requestRepository.findById(id).orElse(null);

        if(request.getInhabitant().equals(inhabitant)) {
            requestRepository.delete(request);
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
            Inhabitant inhabitant = inhabitantRepository.findTopByEmail(currentEmail);
            if (inhabitant == null)
                return ResponseEntity.badRequest().build();

            List<RequestDTO> requestDTOList = new ArrayList<>();

            for (Request request : inhabitant.getRequestList()) {
                requestDTOList.add(new RequestDTO(request.getId(),request.getTitle(),request.getContent(),request.getTimeStamp(),request.getComment(),request.getCategory().getCategory(),request.getStatus().name()));
            }

            return ResponseEntity.ok().body(Page.createPage(page, size,requestDTOList));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getRequest(@PathVariable Long id){

        Request request = requestRepository.findById(id).orElse(null);

        return ResponseEntity.ok(request);
    }
}
