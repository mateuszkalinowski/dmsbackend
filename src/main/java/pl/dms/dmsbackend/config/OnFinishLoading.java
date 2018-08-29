package pl.dms.dmsbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.dms.dmsbackend.model.*;
import pl.dms.dmsbackend.repositories.*;

@Component
public class OnFinishLoading implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    public void onApplicationEvent(ContextRefreshedEvent event) {


        inhabitantRepository.save(new Inhabitant("student@local",passwordEncoder.encode("password"),"Jan","Kowalski",106));
        workerRepository.save(new Worker("worker@local",passwordEncoder.encode("password"),"Zbigniew","Nowak","505606707"));
        workerRepository.save(new Worker("worker2@local",passwordEncoder.encode("password"),"Zbigniew","Nowak","505606707"));

        categoryRepository.save(new Category("Hydraulika"));
        categoryRepository.save(new Category("Elektryka"));
        categoryRepository.save(new Category("Stolarka"));
        categoryRepository.save(new Category("Portiernia"));
        categoryRepository.save(new Category("Administracja"));
        categoryRepository.save(new Category("Internet"));
        categoryRepository.save(new Category("Rada mieszkańców"));

        announcementRepository.save(new Announcement("Brak ciepłej wody","W dniach 4-5 września 2018 nie będzie ciepłej wody",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Zamknięte główne drzwi","\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.\"",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Brak prądu1","W dniach 22-23 paźniernika nie będzie prądu",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Brak prądu2","W dniach 22-23 paźniernika nie będzie prądu",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Brak prądu3","W dniach 22-23 paźniernika nie będzie prądu",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Brak prądu4","W dniach 22-23 paźniernika nie będzie prądu",workerRepository.findTopByEmail("worker2@local")));
        announcementRepository.save(new Announcement("Brak prądu5","W dniach 22-23 paźniernika nie będzie prądu",workerRepository.findTopByEmail("worker2@local")));

    }
}

