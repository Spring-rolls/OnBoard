
package OnBoard.example.OnBoard.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String location;
    private String placeName;
    private String workingHour;
    private String authority;
    private String userType;
    private String image;
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    List<Event> events;
    @OneToMany(mappedBy = "applicationUser")
    private List<Event> eventList;

    @OneToMany(mappedBy = "appUser")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "appU")
    private List<Rating> ratings;
    @ManyToMany
    @JoinTable(
            name="user_userid",
            joinColumns = { @JoinColumn(name = "primaryUser") },
            inverseJoinColumns = { @JoinColumn(name = "raterUser") }
    )
    public List<ApplicationUser> reviewers;

    @ManyToMany(mappedBy = "reviewers")
    public List<ApplicationUser> reviewsUser;

    public ApplicationUser(String password, String username, String firstName, String lastName, String location, String placeName, String workingHour, String authority,String userType,String image) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.placeName = placeName;
        this.workingHour = workingHour;
        this.authority = authority;
        this.userType = userType;
        this.image = image;
    }

    public ApplicationUser(String password, String username, String firstName, String lastName, String authority,String userType,String image) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authority = authority;
        this.userType = userType;
        this.image = image;
    }

    public ApplicationUser() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(String workingHour) {
        this.workingHour = workingHour;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Integer getId() {
        return id;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<ApplicationUser> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<ApplicationUser> reviewers) {
        this.reviewers = reviewers;
    }

    public List<ApplicationUser> getReviewsUser() {
        return reviewsUser;
    }

    public void setReviewsUser(List<ApplicationUser> reviewsUser) {
        this.reviewsUser = reviewsUser;
    }
    public String starRate(){
        float sum=0;
        for (Rating rate:this.ratings) {
            sum+=rate.getStarNumber();
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return  df.format(sum/(float)ratings.size());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(authority);
        List<SimpleGrantedAuthority> grantedAuthorities=new ArrayList<SimpleGrantedAuthority>();
        grantedAuthorities.add(simpleGrantedAuthority);
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
