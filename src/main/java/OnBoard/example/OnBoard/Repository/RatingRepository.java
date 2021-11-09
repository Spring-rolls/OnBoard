package OnBoard.example.OnBoard.Repository;

import OnBoard.example.OnBoard.Model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating,Integer> {
}
