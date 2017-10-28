package ytdownloadonline.com.ytdownloadonline.services;

import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylist;
import ytdownloadonline.com.ytdownloadonline.entity.youtubePlaylist.YoutubePlaylist;
import ytdownloadonline.com.ytdownloadonline.entity.youtubePlaylist.YoutubePlaylistInfo;

import java.io.IOException;
import java.util.List;

public interface YoutubePlaylistService {

	List<YoutubePlaylistInfo> findYoutubePlaylistsInfo() throws IOException;

	List<YoutubePlaylist> findYoutubePlaylists(List<UsermadePlaylist> usermadePlaylistList) throws IOException;

	UsermadePlaylist findbyLink(String link);

}
