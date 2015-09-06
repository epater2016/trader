package trader.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import trader.models.User;
import trader.models.UserRole;

@Repository
@Transactional
public interface UserRoleRepository  extends CrudRepository<UserRole, Long> {
	Iterable<UserRole> findByUser(User user);
}
