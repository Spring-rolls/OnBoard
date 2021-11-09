package OnBoard.example.OnBoard.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String gameName;
    private int numberOfPlayer;
    private String dateTime;
    private String place;
    private String description;

    @ManyToMany(mappedBy = "events")
    List<ApplicationUser> applicationUserList;

    @ManyToOne
    private ApplicationUser applicationUser;
//(cascade = CascadeType.ALL)
    @OneToOne(mappedBy = "event")
//    @JoinColumn(name = "photo_event", referencedColumnName = "id")
    private Photo photo;

    public Event(String gameName, int numberOfPlayer, String dateTime, String place,ApplicationUser applicationUser) {
        this.gameName = gameName;
        this.numberOfPlayer = numberOfPlayer;
        this.dateTime = dateTime;
        this.place = place;
        this.applicationUser=applicationUser;
    }

    public Event(String gameName, String dateTime, String place, String description, ApplicationUser applicationUser) {
        this.gameName = gameName;
        this.dateTime = dateTime;
        this.place = place;
        this.description = description;
        this.applicationUser = applicationUser;
    }

    public Event() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<ApplicationUser> getApplicationUserList() {
        return applicationUserList;
    }

    public void setApplicationUserList(List<ApplicationUser> applicationUserList) {
        this.applicationUserList = applicationUserList;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
