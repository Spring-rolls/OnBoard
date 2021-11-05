package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Model.Event;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/event")
    public String getEvent(Principal p , Model m){
        m.addAttribute("authUser",appUserRepository.findByUsername(p.getName()));
        return "event";
    }
    @PostMapping("/event")
    public RedirectView createEvent(@RequestParam String gameName,
                                    @RequestParam int numberOfPlayer,
                                    @RequestParam String dateTime,
                                    @RequestParam String place,
                                    Principal p){
        Event event =new Event(gameName,numberOfPlayer,dateTime,place);
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        eventRepository.save(event);
        applicationUser.getEvents().add(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
}
