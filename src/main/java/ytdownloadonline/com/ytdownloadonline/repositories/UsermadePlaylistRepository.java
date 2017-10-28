package ytdownloadonline.com.ytdownloadonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ytdownloadonline.com.ytdownloadonline.entity.usermadePlaylist.UsermadePlaylist;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsermadePlaylistRepository extends JpaRepository<UsermadePlaylist, String> {
		
	List<UsermadePlaylist> findByChannelIdAndPlaylistName(String channelId, String playlistName);

	@Query("SELECT u FROM usermadePlaylist u WHERE u.channelId = ?1")
    List<UsermadePlaylist> findDistinctPlaylistNameByChannelId(String channelId);

	@Transactional
	@Modifying
	@Query("DELETE FROM usermadePlaylist u WHERE u.channelId = ?1 AND u.playlistName = ?2")
	void deleteByChannelIdAndPlaylistName(String channelId, String playlistName);

	@Transactional 
	@Modifying
	void deleteById(Long id);
	
}
