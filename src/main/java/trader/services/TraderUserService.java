package trader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import trader.models.User;
import trader.repositories.UserRepository;

@Service
public class TraderUserService {
	@Autowired
	private UserRepository userRepository;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
}
