package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.*;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import OnBoard.example.OnBoard.Repository.NotificationRepository;
import OnBoard.example.OnBoard.Repository.PhotoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.Gson;


@Controller
public class EventController {


    @Autowired
    EventRepository eventRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    PhotoRepository photoRepository;

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

        /*-----API----*/

        Gson gson = new Gson();
        Splash s = null;
        HttpURLConnection conn = null;
        BufferedReader read = null;
        try {
            System.out.println("from API file");
            URL url = new URL("https://api.unsplash.com/search/photos/?client_id=5JrdpKwQXgj6389dUkJ0mwaZagbWALMWQYhHAiygjco&query="+gameName);
            conn = (HttpURLConnection) url.openConnection();
            read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            s = gson.fromJson(read, Splash.class);
            System.out.println("array of games" + s.getResults()[0].urls.raw);
            Photo photo=new Photo(s.getResults()[0].urls.raw,event);
            photoRepository.save(photo);

        } catch (Exception IOException) {
            System.out.println("from catch");
            Photo photo1=new Photo("https://images.unsplash.com/photo-1532699462982-132875fc5397?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fGJvYXJkJTIwZ2FtZXN8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",event);
            photoRepository.save(photo1);
            Reader reader = null;
        }
        eventRepository.save(event);
        /*-----------*/
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
        /*-----API----*/

        Gson gson = new Gson();
        Splash s = null;
        HttpURLConnection conn = null;
        BufferedReader read = null;
        try {
            System.out.println("from API file");
            URL url = new URL("https://api.unsplash.com/search/photos/?client_id=5JrdpKwQXgj6389dUkJ0mwaZagbWALMWQYhHAiygjco&query="+gameName);
            conn = (HttpURLConnection) url.openConnection();
            read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            s = gson.fromJson(read, Splash.class);
            System.out.println("array of games" + s.getResults()[0].urls.raw);
            Photo photo=new Photo(s.getResults()[0].urls.raw,event);
                photoRepository.save(photo);

        } catch (Exception IOException) {
            System.out.println("from catch");
            Photo photo1=new Photo("https://images.unsplash.com/photo-1532699462982-132875fc5397?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fGJvYXJkJTIwZ2FtZXN8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",event);
            photoRepository.save(photo1);
            Reader reader = null;
        }
        eventRepository.save(event);
        /*-----------*/
        appUserRepository.save(applicationUser);
        return new RedirectView("/");
    }
    @GetMapping("/join/{id}")
    public RedirectView joinEvent(@PathVariable Integer id,Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().add(event);
        appUserRepository.save(applicationUser);
        /* notification*/
        String notification= applicationUser.getFirstName() + " has joined to your " + event.getGameName()+" event at " +dtf.format(now);
        Notification notification1=new Notification(notification,event.getApplicationUser());
        notificationRepository.save(notification1);
        /*------------*/
        return new RedirectView("/");
    }
    @GetMapping("/unjoin/{id}")
    public RedirectView unJoinEvent(@PathVariable Integer id,Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().remove(event);
        appUserRepository.save(applicationUser);

        /* notification*/
        String notification= p.getName() + " has left from your " + event.getGameName()+" event at " +dtf.format(now);
        Notification notification1=new Notification(notification,event.getApplicationUser());
        notificationRepository.save(notification1);
        /*------------*/
        return new RedirectView("/");
    }
    @GetMapping("/unjoins/{id}")
    public RedirectView unJoinEvents(@PathVariable Integer id,Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event =eventRepository.findById(id).get();
        ApplicationUser applicationUser =appUserRepository.findByUsername(p.getName());
        applicationUser.getEvents().remove(event);
        appUserRepository.save(applicationUser);

        /* notification*/
        String notification= p.getName() + " has left from your " + event.getGameName()+" event at " +dtf.format(now);
        Notification notification1=new Notification(notification,event.getApplicationUser());
        notificationRepository.save(notification1);
        /*------------*/
        return new RedirectView("/profile");
    }
    @GetMapping("/delete/{id}")
    public RedirectView deleteEventFromHomePage(@PathVariable Integer id,Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event =eventRepository.findById(id).get();
        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has delete from your " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);
            user.getEvents().remove(event);
            appUserRepository.save(user);
        }
        /*-----------------------------------------------*/
        photoRepository.deleteById(event.getPhoto().getId());
        eventRepository.deleteById(id);
        return new RedirectView("/");
    }
    @GetMapping("/deleted/{id}")
    public RedirectView deleteEventFromProfile(@PathVariable Integer id,Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event =eventRepository.findById(id).get();

        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has delete his " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);

            user.getEvents().remove(event);
            appUserRepository.save(user);
        }
        /*-----------------------------------------------*/
        photoRepository.deleteById(event.getPhoto().getId());
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
                                     @PathVariable Integer id,
                                     Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setNumberOfPlayer(numberOfPlayer);
        event.setDateTime(dateTime);
        event.setPlace(place);
        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has update his " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);
        }
        /*-----------------------------------------------*/
        eventRepository.save(event);
        return new RedirectView("/");
    }
    @PostMapping("/updateformbusiness/{id}")
    public RedirectView updateBusiness(@RequestParam String gameName,
                                     @RequestParam String description,
                                     @RequestParam String dateTime,
                                     @RequestParam String place,
                                     @PathVariable Integer id,
                                       Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setPlace(place);


        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has update his " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);
        }
        /*-----------------------------------------------*/
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
                                     @PathVariable Integer id,
                                                    Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setNumberOfPlayer(numberOfPlayer);
        event.setDateTime(dateTime);
        event.setPlace(place);

        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has update his " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);
        }
        /*-----------------------------------------------*/
        eventRepository.save(event);
        return new RedirectView("/profile");
    }
    @PostMapping("/updateformbusinessprofile/{id}")
    public RedirectView updateBusinessFromProfile(@RequestParam String gameName,
                                     @RequestParam String description,
                                     @RequestParam String dateTime,
                                     @RequestParam String place,
                                     @PathVariable Integer id,
                                                  Principal p){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Event event=eventRepository.findById(id).get();
        event.setGameName(gameName);
        event.setDescription(description);
        event.setDateTime(dateTime);
        event.setPlace(place);

        /* -----------------notification---------------*/
        for (ApplicationUser user:event.getApplicationUserList()) {
            String notification= p.getName() + " has update his " + event.getGameName()+" event at " +dtf.format(now);
            Notification notification1=new Notification(notification,user);
            notificationRepository.save(notification1);
        }
        /*-----------------------------------------------*/

        eventRepository.save(event);
        return new RedirectView("/profile");
    }
}
