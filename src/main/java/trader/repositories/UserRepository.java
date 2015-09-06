package trader.repositories;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import trader.models.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
}
