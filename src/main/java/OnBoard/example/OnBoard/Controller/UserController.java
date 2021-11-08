package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Model.Event;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import OnBoard.example.OnBoard.Repository.EventRepository;
import OnBoard.example.OnBoard.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    EventRepository eventRepository;
    //endpoin from the home page
    @GetMapping("/")
    public String homePage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser authUser = appUserRepository.findByUsername(p.getName());
            m.addAttribute("authUser", authUser);
        }
        m.addAttribute("eventlist",eventRepository.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }


    @GetMapping("/signupNormal")
    public String signupNormal() {
        return "signupNormal";
    }

    @PostMapping("/signup")
    public RedirectView singUp(@RequestParam String password,
                               @RequestParam String username,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String location,
                               @RequestParam String placeName,
                               @RequestParam String workingHour,
                               @RequestParam String image) {
        /**
         * we have to create if condition if the condition isBusiness we implement the full constructor else we implement
         * the other constructor that will be without the business.
         */

        ApplicationUser applicationUser = new ApplicationUser(encoder.encode(password), username, firstName, lastName, location, placeName, workingHour, "ROLE_USER","business",image);
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }
    @PostMapping("/signupNormal")
    public RedirectView signupNormal(@RequestParam String password,
                                     @RequestParam String username,
                                     @RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam String image) {
        /**
         * we have to create if condition if the condition isBusiness we implement the full constructor else we implement
         * the other constructor that will be without the business.
         */
        ApplicationUser applicationUser = new ApplicationUser(encoder.encode(password), username, firstName, lastName, "ROLE_USER","normal",image);
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @GetMapping("/user/{id}")
    public String userPage(@PathVariable Integer id,Model model,Principal principal){
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        ApplicationUser user=appUserRepository.findById(id).get();
        model.addAttribute("authUser",authUser);
        model.addAttribute("user", user);
        return "user";
    }


    @GetMapping("/profile")
    public String profilePage(Model model,Principal principal){
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        model.addAttribute("authUser",authUser);
        return "profile";
    }
    @GetMapping("/editProfileUser")
    public String editProfile(Model model,Principal principal){
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        model.addAttribute("authUser",authUser);
        return "updateProfilePage";
    }

    @PostMapping("/editProfileUsersBusiness")
    public RedirectView editProfileUser(
                                        @RequestParam String username,
                                        @RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String location,
                                        @RequestParam String placeName,
                                        @RequestParam String workingHour,
                                        @RequestParam String image,
                                        Principal principal) {

        ApplicationUser applicationUser=appUserRepository.findByUsername(principal.getName());
        applicationUser.setUsername(username);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setImage(image);
        applicationUser.setLocation(location);
        applicationUser.setPlaceName(placeName);
        applicationUser.setWorkingHour(workingHour);
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @PostMapping("/editProfileUsers")
    public RedirectView editProfileUsersBusiness(
                                        @RequestParam String username,
                                        @RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String image,
                                        Principal principal) {

        ApplicationUser applicationUser=appUserRepository.findByUsername(principal.getName());
        applicationUser.setUsername(username);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setImage(image);
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }
}
