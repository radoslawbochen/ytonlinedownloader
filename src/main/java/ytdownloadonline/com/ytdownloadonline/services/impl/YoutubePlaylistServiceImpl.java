package ytdownloadonline.com.ytdownloadonline.services.impl;

import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ytdownloadonline.com.ytdownloadonline.entity.User;
import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylist;
import ytdownloadonline.com.ytdownloadonline.entity.youtubePlaylist.YoutubePlaylist;
import ytdownloadonline.com.ytdownloadonline.entity.youtubePlaylist.YoutubePlaylistInfo;
import ytdownloadonline.com.ytdownloadonline.repositories.YoutubeUserRepository;
import ytdownloadonline.com.ytdownloadonline.security.Auth;
import ytdownloadonline.com.ytdownloadonline.services.UserService;
import ytdownloadonline.com.ytdownloadonline.services.UsermadePlaylistService;
import ytdownloadonline.com.ytdownloadonline.services.YoutubeAccountService;
import ytdownloadonline.com.ytdownloadonline.services.YoutubePlaylistService;

import java.io.IOException;
import java.util.List;

@Service
public class YoutubePlaylistServiceImpl implements YoutubePlaylistService {
	
	
	@Autowired
	UsermadePlaylistService usermadePlaylistService;
	@Autowired
	YoutubeAccountService youtubeAccountService;
	@Autowired
	UserService userService;
	
	@Override
	public List<YoutubePlaylistInfo> findYoutubePlaylistsInfo() throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		String userId = String.valueOf(user.getId());
		String channelId = YoutubeUserRepository.getChannelId(credential, userId);
		return YoutubeUserRepository.fetchPlaylistsInfoList(credential, channelId, userId);
	}

	@Override
	public List<YoutubePlaylist> findYoutubePlaylists(List<UsermadePlaylist> usermadePlaylists) throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
		List<YoutubePlaylist> youtubePlaylists = YoutubeUserRepository.fetchPlaylistList(credential, youtubeAccountService.getChannelId());
				
		for (UsermadePlaylist usermadePlaylist : usermadePlaylists){
			for (YoutubePlaylist youtubePlaylist : youtubePlaylists){
				for (int i3 = 0; i3 < youtubePlaylist.getPlaylistItemsInfoList().size(); ++i3){
					if (usermadePlaylist.getLink().equals(youtubePlaylist.getPlaylistItemsInfoList().get(i3).getVideoId())){
						youtubePlaylist.getPlaylistItemsInfoList().remove(i3);
					}
				}
			}
		}

		return youtubePlaylists;
	}

	@Override
	public UsermadePlaylist findbyLink(String link) {
		UsermadePlaylist usermadePlaylist = new UsermadePlaylist();
		
		return usermadePlaylist;
	}

}
