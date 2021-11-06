package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Model.Event;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PostMapping("/eventfornormal")
    public RedirectView eventForNormal(@RequestParam String gameName,
                                    @RequestParam int numberOfPlayer,
                                    @RequestParam String dateTime,
                                    @RequestParam String place,
                                    Principal p){
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
            Event event =new Event(gameName,numberOfPlayer,dateTime,place,applicationUser);
            eventRepository.save(event);
//        applicationUser.getEvents().add(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
    @PostMapping("/eventforbusiness")
    public RedirectView eventForBusiness(@RequestParam String gameName,
                                    @RequestParam String dateTime,
                                    @RequestParam String place,
                                    @RequestParam String description,
                                    Principal p){
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());

            Event event =new Event(gameName,dateTime,place,description,applicationUser);
            eventRepository.save(event);
//        applicationUser.getEvents().add(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
    @GetMapping("/join/{id}")
    public RedirectView joinEvent(@PathVariable Integer id,Principal p){
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().add(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
    @GetMapping("/unjoin/{id}")
    public RedirectView unJoinEvent(@PathVariable Integer id,Principal p){
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().remove(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
    @GetMapping("/unjoins/{id}")
    public RedirectView unJoinEvents(@PathVariable Integer id,Principal p){
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().remove(event);
        appUserRepository.save(applicationUser);
        return new RedirectView("/profile");
    }
    @GetMapping("/delete/{id}")
    public RedirectView deleteEventFromHomePage(@PathVariable Integer id,Principal p){
        Event event =eventRepository.findById(id).get();
        eventRepository.deleteById(id);
        return new RedirectView("/");
    }
    @GetMapping("/deleted/{id}")
    public RedirectView deleteEventFromProfile(@PathVariable Integer id,Principal p){
        Event event =eventRepository.findById(id).get();
        eventRepository.deleteById(id);
        return new RedirectView("/profile");
    }
}
