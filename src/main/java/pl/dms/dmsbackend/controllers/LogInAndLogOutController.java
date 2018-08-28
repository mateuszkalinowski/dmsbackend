package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.dataOutput.AuthenticationResponseDTO;
import pl.dms.dmsbackend.dataInput.CredentialsDTO;
import pl.dms.dmsbackend.dataOutput.MessageDTO;
import pl.dms.dmsbackend.dataInput.PasswordChangeDTO;
import pl.dms.dmsbackend.model.Inhabitant;
import pl.dms.dmsbackend.model.User;
import pl.dms.dmsbackend.model.Worker;
import pl.dms.dmsbackend.repositories.InhabitantRepository;
import pl.dms.dmsbackend.repositories.UserRepository;
import pl.dms.dmsbackend.repositories.WorkerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "api")
public class LogInAndLogOutController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody CredentialsDTO credentialsDTO, HttpServletRequest request) {

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(token);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        final HttpSession httpSession = request.getSession(true);

        httpSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok().body(new AuthenticationResponseDTO(httpSession.getId()));

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/info")
    public ResponseEntity info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Inhabitant inhabitant = inhabitantRepository.findTopByEmail(currentEmail);
        if(inhabitant==null) {
            Worker worker = workerRepository.findTopByEmail(currentEmail);
            if(worker==null) {
                return ResponseEntity.badRequest().body(new MessageDTO("Nie znaleziono takie użytkownika"));
            }
            else {
                return ResponseEntity.ok().body(worker);
            }
        }
        else {
            return ResponseEntity.ok().body(inhabitant);
        }
    }

    @PutMapping(value = "/password")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentEmail = authentication.getName();
            User currentUser = userRepository.findByEmail(currentEmail);
            if (currentUser == null && passwordChangeDTO.getOldPassword().length()>0 && passwordChangeDTO.getNewPassword().length()>0) {
                return ResponseEntity.badRequest().build();
            }
            if( passwordEncoder.matches(passwordChangeDTO.getOldPassword(),currentUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
                userRepository.save(currentUser);
                return ResponseEntity.ok().body(new MessageDTO("Hasło zostało poprawnie zmienione"));
            }
            else {
                return ResponseEntity.badRequest().body(new MessageDTO("Obecne hasło nie jest poprawne"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
