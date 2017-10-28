package ytdownloadonline.com.ytdownloadonline.services.impl;

import com.google.api.client.auth.oauth2.Credential;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ytdownloadonline.com.ytdownloadonline.entity.PlaylistItemInfo;
import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylist;
import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylistInfo;
import ytdownloadonline.com.ytdownloadonline.repositories.UsermadePlaylistRepository;
import ytdownloadonline.com.ytdownloadonline.repositories.YoutubeUserRepository;
import ytdownloadonline.com.ytdownloadonline.security.Auth;
import ytdownloadonline.com.ytdownloadonline.services.UserService;
import ytdownloadonline.com.ytdownloadonline.services.UsermadePlaylistService;

import java.io.IOException;
import java.util.*;

@Service
public class UsermadePlaylistServiceImpl implements UsermadePlaylistService {

	@Autowired
	private UsermadePlaylistRepository usermadePlaylistRepo;
	@Autowired
	private UserService userService;

	@Override
	public List<UsermadePlaylist> findByPlaylistName(String playlistName) throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
		String channelId = YoutubeUserRepository.getChannelId(credential, Auth.getUserId(userService));

		List<UsermadePlaylist> usermadePlaylistList = new ArrayList<UsermadePlaylist>();
		
		for (UsermadePlaylist usermadePlaylist : this.usermadePlaylistRepo.findByChannelIdAndPlaylistName(channelId, playlistName)) {
			if(usermadePlaylist.getLink() != null){
				usermadePlaylistList.add(usermadePlaylist);
			}
		}
		
		return usermadePlaylistList;
	}
	
	@Override
	public UsermadePlaylist saveUsermadePlaylist(UsermadePlaylist usermadePlaylist){
		return this.usermadePlaylistRepo.save(usermadePlaylist);
	}

	@Override
	public List<UsermadePlaylistInfo> findDistinctPlaylistName() throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
		String channelId = YoutubeUserRepository.getChannelId(credential, Auth.getUserId(userService));

		List<UsermadePlaylist> userList = usermadePlaylistRepo.findDistinctPlaylistNameByChannelId(channelId);
		List<UsermadePlaylistInfo> userPlaylistList = new ArrayList<>();
		Iterator<UsermadePlaylist> itr = userList.iterator();
		Set<String> namesUniqueSet = new HashSet<>();
		List<String> userNameList = new ArrayList<String>();
				
		while(itr.hasNext()){
			UsermadePlaylist u = itr.next();
			userNameList.add(u.getPlaylistName());			
			if(!namesUniqueSet.contains(u.getPlaylistName())){
				namesUniqueSet.add(u.getPlaylistName());
			}
		}
		
		for(String playlistName : namesUniqueSet){
			userPlaylistList.add(new UsermadePlaylistInfo(playlistName, Collections.frequency(userNameList, playlistName)));
		}

		return userPlaylistList;
	}

	@Override
	public void deleteByPlaylistNameAndChannelId(String playlistName) throws IOException {
		Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
		String channelId = YoutubeUserRepository.getChannelId(credential, Auth.getUserId(userService));

		this.usermadePlaylistRepo.deleteByChannelIdAndPlaylistName(channelId, playlistName);
	}

	@Override
	public void deleteById(Long id) {
		this.usermadePlaylistRepo.deleteById(id);
	}

	@Override
	public void add(Credential credential, ArrayList<String> itemsInfoList, String playlistName){
		String channelId = YoutubeUserRepository.getChannelId(credential, Auth.getUserId(userService));
		
		List<PlaylistItemInfo> playlistItemInfoList = new ArrayList<>();
		List<UsermadePlaylist> usermadePlaylists = new ArrayList<UsermadePlaylist>();
		
		for (String itemInfo : itemsInfoList){
				if(itemInfo != null){
					String link = itemInfo.substring(itemInfo.length() - 11);
					String videoTitle = itemInfo.substring(0, itemInfo.length() - 11);
					playlistItemInfoList.add(new PlaylistItemInfo(videoTitle, link));
					usermadePlaylists.add(new UsermadePlaylist(
							10L,
							channelId,
							playlistName, 
							link,
							0,
							videoTitle
							));
			}			
		}	
		
 		for (UsermadePlaylist usermadePlaylist : usermadePlaylists){
			this.usermadePlaylistRepo.save(usermadePlaylist);
		}
	}
	
	@Override
	public void delete(ArrayList<UsermadePlaylist> usermadePlaylists, String playlistName) {
		for (UsermadePlaylist usermadePlaylist : usermadePlaylists){
			if(usermadePlaylist.getId() != null){
				this.usermadePlaylistRepo.deleteById(usermadePlaylist.getId());
			}
		}
	}

	@Override
	public void addAll(ArrayList<UsermadePlaylist> usermadePlaylists) {
		for (UsermadePlaylist usermadePlaylist : usermadePlaylists){
			this.saveUsermadePlaylist(usermadePlaylist);
		}
	}

	@Override
	public List<UsermadePlaylist> compare(String[] filesNamesToCompare, List<UsermadePlaylist> usermadePlaylist) {
		List<UsermadePlaylist> comparedPlaylist = new ArrayList<>();

		ListIterator<UsermadePlaylist> usermadePlaylistIterator = usermadePlaylist.listIterator();
		
		for(String name : filesNamesToCompare){
			usermadePlaylistIterator = usermadePlaylist.listIterator();
			while(usermadePlaylistIterator.hasNext()){
				UsermadePlaylist tempUsermadePlaylist = usermadePlaylistIterator.next();
				String diff = StringUtils.difference(name, tempUsermadePlaylist.getVideoTitle());
				int namesCollectivelyLength = name.length() + tempUsermadePlaylist.getVideoTitle().length();

				// if difference between names is higher than 30% delete it
				if((( (diff.length() * 2) * 100) / namesCollectivelyLength) < 30){
					usermadePlaylistIterator.remove();
				}
			}
		}		

		Iterator<UsermadePlaylist> usermadePlaylistItr = usermadePlaylist.listIterator();
		while(usermadePlaylistItr.hasNext()){
			comparedPlaylist.add(usermadePlaylistItr.next());
		}
		
		return comparedPlaylist;
	}	
}
