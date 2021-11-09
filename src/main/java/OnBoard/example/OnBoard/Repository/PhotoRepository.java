package OnBoard.example.OnBoard.Repository;

import OnBoard.example.OnBoard.Model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository  extends CrudRepository<Photo,Integer> {
}
