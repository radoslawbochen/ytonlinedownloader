package ytdownloadonline.com.ytdownloadonline.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ytdownloadonline.com.ytdownloadonline.entity.AddListWrapper;
import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylistWrapper;
import ytdownloadonline.com.ytdownloadonline.services.UserService;
import ytdownloadonline.com.ytdownloadonline.services.UsermadePlaylistService;
import ytdownloadonline.com.ytdownloadonline.services.YoutubeAccountService;
import ytdownloadonline.com.ytdownloadonline.services.YoutubePlaylistService;

import java.io.IOException;

@Controller
@RequestMapping("/playlists")
public class PlaylistsController {

    @Autowired
    private YoutubeAccountService youtubeAccountService;
    @Autowired
    private UserService userService;
	@Autowired
	private UsermadePlaylistService usermadePlaylistService;
	@Autowired
	private YoutubePlaylistService youtubePlaylistService;
	
	@RequestMapping(method = RequestMethod.GET)
    public String playlistView(
    		@RequestParam(value = "playlist", required = false) String playlistName) throws IOException {
				//if(Auth.getFlow() == null){
				//	return "redirect:/login";
				//}
				
    			if (playlistName != null){
    				//ArrayList<UsermadePlaylist> usermadePlaylistList = new ArrayList<UsermadePlaylist>(usermadePlaylistService.findByPlaylistName(playlistName));
    				//List<YoutubePlaylist> youtubePlaylistList = youtubePlaylistService.findYoutubePlaylists(usermadePlaylistList);

    				return "";
    			} else {
					return "";
    			}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String modifyUsermadePlaylist(
			@RequestParam(required = false, value = "add") String addPlaylistName,
			@RequestParam(required = false, value = "delete") String deletePlaylistName
	) throws IOException {
		if (addPlaylistName != null){
			//usermadePlaylistService.saveUsermadePlaylist(new UsermadePlaylist(10L, youtubeAccountService.getChannelId(), addPlaylistName));
		    String getPlaylist = "?playlist=" + addPlaylistName;
		    
			return "redirect:/playlists" + getPlaylist;
		} else if (deletePlaylistName != null) {
			usermadePlaylistService.deleteByPlaylistNameAndChannelId(deletePlaylistName);
			
			return "redirect:/playlists";
		}
		
		return "redirect:/playlists";
	}
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
    		@ModelAttribute(value = "users") UsermadePlaylistWrapper usermadePlaylistWrapper,
    		@RequestParam(value = "playlistName") String playlistName
    		){
    	//usermadePlaylistService.delete(usermadePlaylistWrapper.getUsermadePlaylists(), playlistName);
        	
    	return "redirect:/playlists?playlist=" + playlistName;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(
    		@ModelAttribute(value = "addList") AddListWrapper addListWrapper,
    		@RequestParam(value = "playlistName", required = false) String playlistName
    		) throws IOException {
			//Credential credential = Auth.getFlow().loadCredential(Auth.getUserId(userService));
			//usermadePlaylistService.add(credential, addListWrapper.getAddList(), playlistName);
		
    	return "";
    }    

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    @ResponseBody
    public String compareUserFilesToPlaylist(
    		@RequestParam("playlistName") String playlistName,
    		@RequestParam("files[]") String[] files
    		) throws IOException {
    	//List<UsermadePlaylist> usermadePlaylist = usermadePlaylistService.findByPlaylistName(playlistName);
    	//List<UsermadePlaylist> comparedPlaylist = usermadePlaylistService.compare(files, usermadePlaylist);
    	
    	return "";
    }
    
    @RequestMapping(value = "/links", method = RequestMethod.GET)
    String showPlaylistLinks(
    		@RequestParam("playlistName") String playlistName
    		) throws IOException {
    	
    	//model.addAttribute("playlist", usermadePlaylistService.findByPlaylistName(playlistName));
    	
    	return "links";
    }
}
