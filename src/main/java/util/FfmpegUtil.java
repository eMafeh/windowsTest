package util;

import java.io.File;
import java.io.IOException;

@Deprecated
public class FfmpegUtil {
    private static final String EXE = "D:\\tools\\ffmpeg-win64-static\\bin\\ffmpeg ";
    private static final String OPTIONS = "-allowed_extensions ALL -protocol_whitelist \"file,http,crypto,tcp\" ";

    public static synchronized void m3u8(String m3u8, String mp4, String dir) {
        final File file = new File(mp4);
        if (file.exists()) return;

        String command = EXE + OPTIONS + "-i " + m3u8 + " -c copy " + mp4;
        System.out.println(dir + "\n" + command + "\n");
        file.getParentFile()
                .mkdirs();
        try {
            Runtime.getRuntime()
                    .exec(command, null, new File(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // D:\tools\ffmpeg-win64-static\bin\ffmpeg  -i F:\Downloads\m3u8result\test.ts -c copy F:\Downloads\m3u8result\test.mp4
//    @Deprecated
//    public void ffmpeg() {
//        if (success.intValue() != subSize) {
//            return;
//        }
//        if (!BigDecimal.ZERO.equals(time)) {
//            try {
//                String mp4 = mp4();
//                String trans;
//                {
//                    String m3u8 = httpUrl.local(Constant.LOCAL);
//                    trans = m3u8.substring(0, m3u8.length() - 10) + "trans.m3u8";
//                }
//                List<String> lines = FileUtil.readAllLines(SerializableUtil.unZip(ALL_M3U8.get(httpUrl.url)));
//                String dir = lines.stream()
//                        .filter(line -> !line.startsWith("#"))
//                        .allMatch(line -> line.contains("/")) ?
//                        (RESULT + httpUrl.domain.replaceAll(":", "."))
//                        : new File(trans).getParent();
//                FileUtil.Writer.async(trans, lines.stream()
//                        .map(line -> line.startsWith("/") ? line.substring(1) : line)
//                        .collect(Collectors.joining("\n"))
//                        .getBytes(), () -> FfmpegUtil.m3u8(trans, mp4, dir));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        work.remove(this);
//    }
    public static void main(String[] args) {
        m3u8("F:\\Downloads\\m3u8result\\test.ts", "F:\\Downloads\\m3u8result\\test.mp4", "F:\\Downloads\\m3u8result\\");
    }
}