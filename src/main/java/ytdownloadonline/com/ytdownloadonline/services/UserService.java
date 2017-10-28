package ytdownloadonline.com.ytdownloadonline.services;


import ytdownloadonline.com.ytdownloadonline.entity.User;

public interface UserService {

	public User findUserByEmail(String email);
	
	public void saveUser(User user);
	
}
