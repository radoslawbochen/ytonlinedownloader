package ytdownloadonline.com.ytdownloadonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ytdownloadonline.com.ytdownloadonline.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
}
