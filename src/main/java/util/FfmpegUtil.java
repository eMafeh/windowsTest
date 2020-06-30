package util;

import java.io.File;
import java.nio.file.Files;

public class FfmpegUtil {
    private static final String EXE = "D:\\tools\\ffmpeg-win64-static\\bin\\ffmpeg ";
    private static final String OPTIONS = "-allowed_extensions ALL -protocol_whitelist \"file,http,crypto,tcp\" ";

    public static synchronized void m3u8(String m3u8, String mp4, String dir) throws Exception {
        File file = new File(mp4);
        if (file.exists()) return;
        Files.createDirectories(file.toPath()
                .getParent());
        String command = EXE + OPTIONS + "-i " + m3u8 + " -c copy " + mp4;
        System.out.println(dir + "\n" + command);
        Runtime.getRuntime()
                .exec(command, null, new File(dir));
    }
}