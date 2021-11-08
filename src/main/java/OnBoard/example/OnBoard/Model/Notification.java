package OnBoard.example.OnBoard.Model;

import javax.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String notification;
    @ManyToOne
    private ApplicationUser appUser;

    public Notification(String notification, ApplicationUser appUser) {
        this.notification = notification;
        this.appUser = appUser;
    }
Notification(){}
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }

    public Integer getId() {
        return id;
    }

}
