package m3u8;


import java.io.File;

public class CheckSuccess {

    public static void main(String[] args) {
        for (M3u8 url : DownloadList.list) {
            String file = url.mp4();
            if (!new File(file).exists())
                System.out.println(file);
        }
        System.out.println(DownloadList.list.size());
    }
}
