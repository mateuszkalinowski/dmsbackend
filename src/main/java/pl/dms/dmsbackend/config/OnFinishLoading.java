package pl.dms.dmsbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.dms.dmsbackend.model.User;
import pl.dms.dmsbackend.repositories.UserRepository;

@Component
public class OnFinishLoading implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void onApplicationEvent(ContextRefreshedEvent event) {
            userRepository.save(new User("student@local",passwordEncoder.encode("password"),"Jan","Kowalski"));
    }
}

