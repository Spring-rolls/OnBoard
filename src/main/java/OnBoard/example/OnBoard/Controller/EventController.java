package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Model.Event;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/update/{id}")
    public String updateEvent(@PathVariable Integer id,Model model,Principal principal){
        Event event =eventRepository.findById(id).get();
        model.addAttribute("event",event);
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        model.addAttribute("authUser",authUser);
        return "update";
    }
    @PostMapping("/updateform/{id}")
    public RedirectView updateNormal(@RequestParam String gameName,
                                       @RequestParam int numberOfPlayer,
                                       @RequestParam String dateTime,
                                       @RequestParam String place,
                                     @PathVariable Integer id){
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setNumberOfPlayer(numberOfPlayer);
        event.setDateTime(dateTime);
        event.setPlace(place);
        eventRepository.save(event);
        return new RedirectView("/");
    }
    @PostMapping("/updateformbusiness/{id}")
    public RedirectView updateBusiness(@RequestParam String gameName,
                                     @RequestParam String description,
                                     @RequestParam String dateTime,
                                     @RequestParam String place,
                                     @PathVariable Integer id){
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setPlace(place);
        eventRepository.save(event);
        return new RedirectView("/");
    }
    @GetMapping("/updatefromprofile/{id}")
    public String updateFromEvent(@PathVariable Integer id,Model model,Principal principal){
        Event event =eventRepository.findById(id).get();
        model.addAttribute("event",event);
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        model.addAttribute("authUser",authUser);
        return "updateProfile";
    }
    @PostMapping("/updateformprofile/{id}")
    public RedirectView updateNormalFromProfile(@RequestParam String gameName,
                                       @RequestParam int numberOfPlayer,
                                       @RequestParam String dateTime,
                                       @RequestParam String place,
                                     @PathVariable Integer id){
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setNumberOfPlayer(numberOfPlayer);
        event.setDateTime(dateTime);
        event.setPlace(place);
        eventRepository.save(event);
        return new RedirectView("/profile");
    }
    @PostMapping("/updateformbusinessprofile/{id}")
    public RedirectView updateBusinessFromProfile(@RequestParam String gameName,
                                     @RequestParam String description,
                                     @RequestParam String dateTime,
                                     @RequestParam String place,
                                     @PathVariable Integer id){
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setPlace(place);
        eventRepository.save(event);
        return new RedirectView("/profile");
    }


}
