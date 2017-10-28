package ytdownloadonline.com.ytdownloadonline.downloader;

import com.github.axet.vget.VGet;

import java.io.File;
import java.net.URL;

public class JavaYoutubeDownloader {

    public static void download(String videoId, String directory){
        try {
            String url = "https://www.youtube.com/watch?v=s10ARdfQUOY";
            String path = "C:\\";
            VGet v = new VGet(new URL(videoId), new File(directory));
            v.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}