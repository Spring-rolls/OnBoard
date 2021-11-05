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

    @ManyToMany(mappedBy = "events")
    List<ApplicationUser> applicationUserList;

    public Event(String gameName, int numberOfPlayer, String dateTime, String place) {
        this.gameName = gameName;
        this.numberOfPlayer = numberOfPlayer;
        this.dateTime = dateTime;
        this.place = place;
    }

    public Event() {

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
}
