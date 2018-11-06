package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.enums.UserRoleEnum;
import pl.dms.dmsbackend.model.users.User;
import pl.dms.dmsbackend.model.UserRole;
import pl.dms.dmsbackend.repositories.UserRepository;
import pl.dms.dmsbackend.utils.dataOutput.InhabitantDTO;
import pl.dms.dmsbackend.utils.dataOutput.MessageDTO;
import pl.dms.dmsbackend.model.users.Inhabitant;
import pl.dms.dmsbackend.repositories.InhabitantRepository;

import javax.transaction.Transactional;


@RestController
@RequestMapping(value = "api/inhabitant")
@Transactional
public class InhabitantController {

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/add")
    public ResponseEntity addInhabitant(@RequestBody InhabitantDTO inhabitantDTO) {
        try {
            User check = userRepository.findByEmail(inhabitantDTO.getEmail());
            if(check!=null)
                return ResponseEntity.badRequest().body(new MessageDTO("Ten adres email jest już użyty"));
            Inhabitant inhabitant = new Inhabitant(inhabitantDTO.getEmail(),passwordEncoder.encode(inhabitantDTO.getPassword()),inhabitantDTO.getFirstName(),inhabitantDTO.getLastName(),new UserRole(UserRoleEnum.INHABITANT),inhabitantDTO.getRoomNumber());
           // user.setInhabitant(new Inhabitant(inhabitantDTO.getRoomNumber()));
            inhabitantRepository.save(inhabitant);
//
//            Inhabitant inhabitant = new Inhabitant(inhabitantDTO.getEmail(),passwordEncoder.encode(inhabitantDTO.getPassword()),inhabitantDTO.getFirstName(),inhabitantDTO.getLastName(),inhabitantDTO.getRoomNumber());
//            inhabitantRepository.save(inhabitant);
            return ResponseEntity.ok(new MessageDTO("Użytkownik został dodany"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageDTO("Podczas dodawania mieszkańca wystąpił błąd"));
        }
    }
}
