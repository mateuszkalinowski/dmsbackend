package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.dataOutput.InhabitantDTO;
import pl.dms.dmsbackend.dataOutput.MessageDTO;
import pl.dms.dmsbackend.model.Inhabitant;
import pl.dms.dmsbackend.repositories.InhabitantRepository;


@RestController
@RequestMapping(value = "api/inhabitant")
public class InhabitantController {

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/add")
    public ResponseEntity addInhabitant(@RequestBody InhabitantDTO inhabitantDTO) {
        try {
            Inhabitant check = inhabitantRepository.findTopByEmail(inhabitantDTO.getEmail());
            if(check!=null)
                return ResponseEntity.badRequest().body(new MessageDTO("Ten adres email jest już użyty"));
            Inhabitant inhabitant = new Inhabitant(inhabitantDTO.getEmail(),passwordEncoder.encode(inhabitantDTO.getPassword()),inhabitantDTO.getFirstName(),inhabitantDTO.getLastName(),inhabitantDTO.getRoomNumber());
            inhabitantRepository.save(inhabitant);
            return ResponseEntity.ok(new MessageDTO("Użytkownik został dodany"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageDTO("Podczas dodawania mieszkańca wystąpił błąd"));
        }
    }


}
