package ytdownloadonline.com.ytdownloadonline.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ytdownloadonline.com.ytdownloadonline.entity.User;
import ytdownloadonline.com.ytdownloadonline.repositories.UserRepository;
import ytdownloadonline.com.ytdownloadonline.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User findUserByEmail(String email) {
		return 	userRepo.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
        user.setActive(1);
		userRepo.save(user);
	}

}
