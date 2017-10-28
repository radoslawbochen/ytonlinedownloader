package ytdownloadonline.com.ytdownloadonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ytdownloadonline.com.ytdownloadonline.downloader.JavaYoutubeDownloader;
import ytdownloadonline.com.ytdownloadonline.services.UserService;

import java.io.File;
import java.io.IOException;

@Controller
public class LandingController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/testHere", method = RequestMethod.GET)
	public String video(
			@RequestParam(name = "directory") String directory,
			@RequestParam(name = "videoId") String videoId
	){
		JavaYoutubeDownloader.download(videoId, directory);
		return "";
	}

	@RequestMapping(value = "/")
	public String index() {
		
			return "index";
	}	    	
			
	@RequestMapping(value = "/login")
    String redirect(){
		
		return "redirect:https://accounts.google.com/o/oauth2/auth?client_id=252777349769-slb56loumaivmrri04oqfua5mfu16oi8.apps.googleusercontent.com&redirect_uri=http://localhost:8080/oauth2callback&scope=https://gdata.youtube.com&response_type=code&access_type=offline";
	}
	
    @RequestMapping(value = "/oauth2callback", method = RequestMethod.GET)
    public String recieveCode(@RequestParam("code") String code) throws IOException {
    	//playlist.security.Auth.receiveCode(userService, code);
    	
    	return "redirect:/user";
    }

}
    