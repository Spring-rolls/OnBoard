package OnBoard.example.OnBoard.Repository;

import OnBoard.example.OnBoard.Model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<ApplicationUser,Integer> {
    ApplicationUser findByUsername(String username);

}
