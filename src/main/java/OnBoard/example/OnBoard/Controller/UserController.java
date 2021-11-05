package OnBoard.example.OnBoard.Controller;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import OnBoard.example.OnBoard.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AppUserRepository appUserRepository;

    //endpoin from the home page
    @GetMapping("/")
    public String homePage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser authUser = appUserRepository.findByUsername(p.getName());
            m.addAttribute("authUser", authUser);
        }
        m.addAttribute("userList",appUserRepository.findAll());
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
                               @RequestParam String workingHour) {
        /**
         * we have to create if condition if the condition isBusiness we implement the full constructor else we implement
         * the other constructor that will be without the business.
         */
        ApplicationUser applicationUser = new ApplicationUser(encoder.encode(password), username, firstName, lastName, location, placeName, workingHour, "ROLE_USER","business");
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }
    @PostMapping("/signupNormal")
    public RedirectView signupNormal(@RequestParam String password,
                                     @RequestParam String username,
                                     @RequestParam String firstName,
                                     @RequestParam String lastName) {
        /**
         * we have to create if condition if the condition isBusiness we implement the full constructor else we implement
         * the other constructor that will be without the business.
         */
        ApplicationUser applicationUser = new ApplicationUser(encoder.encode(password), username, firstName, lastName, "ROLE_USER","normal");
        appUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @GetMapping("/profile")
    public String profilePage(Model model,Principal principal){
        ApplicationUser authUser=appUserRepository.findByUsername(principal.getName());
        model.addAttribute("authUser",authUser);
        return "profile";
    }
}
