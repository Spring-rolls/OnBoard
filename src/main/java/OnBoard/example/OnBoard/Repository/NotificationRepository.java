package OnBoard.example.OnBoard.Repository;

import OnBoard.example.OnBoard.Model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification,Integer> {
}
