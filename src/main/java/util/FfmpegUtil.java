package util;

import fun.qianrui.staticUtil.ThreadUtil;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

public class FfmpegUtil {
    private static final ThreadPoolExecutor pool = ThreadUtil.createPool(2, 2, "cmdInputStream");
    private static final String EXE = "D:\\tools\\ffmpeg-win64-static\\bin\\ffmpeg ";
    private static final String OPTIONS = "-allowed_extensions ALL -protocol_whitelist \"file,http,crypto,tcp\" ";

    public static synchronized void m3u8(String m3u8, String mp4, String dir) throws Exception {
        String command = EXE + OPTIONS + "-i " + m3u8 + " -c copy " + mp4;
        System.out.println(dir);
        System.out.println(command);
        Process exec = Runtime.getRuntime()
                .exec(command, null, new File(dir));
//
//        pool.execute(() -> {
//            try (InputStream inputStream = exec.getInputStream();
//                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"))) {
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        pool.execute(() -> {
//            try (InputStream inputStream = exec.getErrorStream();
//                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"))) {
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    System.err.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        exec.waitFor(3, TimeUnit.SECONDS);
    }
}