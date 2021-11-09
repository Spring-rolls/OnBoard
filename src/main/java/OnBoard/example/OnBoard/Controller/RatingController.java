package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Model.Event;
import OnBoard.example.OnBoard.Model.Rating;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import OnBoard.example.OnBoard.Repository.NotificationRepository;
import OnBoard.example.OnBoard.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class RatingController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    RatingRepository ratingRepository;

    @PostMapping("/feedBack/{id}")
    public RedirectView feedBack(@PathVariable Integer id,
                                        @RequestParam String feedBack,
                                       @RequestParam float starNumber,
                                       Principal p){
        ApplicationUser authUser =appUserRepository.findByUsername(p.getName());
        ApplicationUser applicationUser =appUserRepository.findById(id).get();
        Rating rating=new Rating(starNumber,authUser.getFirstName()+' '+feedBack,applicationUser);
        ratingRepository.save(rating);
        authUser.getReviewers().add(appUserRepository.findById(id).get());
        appUserRepository.save(authUser);
        return new RedirectView("/user/"+id);
    }


}
