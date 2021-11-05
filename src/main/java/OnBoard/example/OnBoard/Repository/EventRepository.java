package OnBoard.example.OnBoard.Repository;

import OnBoard.example.OnBoard.Model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,Integer> {
}
