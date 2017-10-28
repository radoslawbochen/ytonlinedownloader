package ytdownloadonline.com.ytdownloadonline.services.impl;

import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ytdownloadonline.com.ytdownloadonline.repositories.YoutubeUserRepository;
import ytdownloadonline.com.ytdownloadonline.security.Auth;
import ytdownloadonline.com.ytdownloadonline.services.UserService;
import ytdownloadonline.com.ytdownloadonline.services.YoutubeAccountService;

import java.io.IOException;

@Service
public class YoutubeAccountServiceImpl implements YoutubeAccountService {

	@Autowired
	UserService userService;
	
	@Override
	public String getChannelId() throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));

		String userId = Auth.getUserId(userService);
		String channelId = YoutubeUserRepository.getChannelId(credential, userId);
		return channelId;
	}	

}
