package OnBoard.example.OnBoard.Model;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String url;

    @OneToOne
    private Event event;

    public Photo(String url, Event event) {
        this.url = url;
        this.event = event;
    }

    public Photo(){
    }

    public Integer getId() {
        return id;
    }



    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
