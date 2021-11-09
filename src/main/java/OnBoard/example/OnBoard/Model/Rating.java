package OnBoard.example.OnBoard.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float starNumber;
    private  String feedBack;
    @ManyToOne
    private ApplicationUser appU; /*applicationUser*/



    public Rating(float starNumber,String feedBack ,ApplicationUser appU) {
        this.starNumber = starNumber;
        this.appU = appU;
        this.feedBack=feedBack;
    }
    public Rating(){};

    public float getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(float starNumber) {
        this.starNumber = starNumber;
    }

    public ApplicationUser getAppU() {
        return appU;
    }

    public void setAppU(ApplicationUser appU) {
        this.appU = appU;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Integer getId() {
        return id;
    }

}
