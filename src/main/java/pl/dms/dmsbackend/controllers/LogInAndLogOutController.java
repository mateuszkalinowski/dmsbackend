package pl.dms.dmsbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import pl.dms.dmsbackend.dtos.AuthenticationResponse;
import pl.dms.dmsbackend.dtos.Credentials;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "api")
public class LogInAndLogOutController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody Credentials credentials, HttpServletRequest request) {

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(token);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        final HttpSession httpSession = request.getSession(true);

        httpSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return new AuthenticationResponse(httpSession.getId());

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
