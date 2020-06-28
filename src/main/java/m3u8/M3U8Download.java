package m3u8;

import http.HttpDownload;
import http.HttpUrl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class M3U8Download {
    public static final LinkedBlockingQueue<HttpUrl<M3u8>> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        queue.add(new M3u8("https://2.ddyunbo.com/20200429/1dKV3TwG/index.m3u8", "妈妈教导儿子安全套是干嘛用的").httpUrl);
        queue.add(new M3u8("https://videozmcdn.stz8.com:8091/20200422/0PpGwOlV/index.m3u8", "母子野外黑丝乱伦无套内射").httpUrl);
        queue.add(new M3u8("https://videozm.whqhyg.com:8091/20200612/j1v0VRhc/index.m3u8", "被洋男友的大弯屌后入").httpUrl);
//        queue.add(new M3u8("","").httpUrl);
        queue.add(new M3u8("https://2.ddyunbo.com/20200619/tjpWXLHY/index.m3u8", "对白有趣的姐弟乱仑秀").httpUrl);
        queue.add(new M3u8("https://videozm.whqhyg.com:8091/20200610/3wWmVOno/index.m3u8", "不嫌弃我们汗臭味的女经理三上悠亚").httpUrl);
        queue.add(new M3u8("https://videozm.whqhyg.com:8091/20200612/EY06JiKD/index.m3u8", "和炮友户外小树林啪啪").httpUrl);

        download();
    }

    private static void download() throws InterruptedException {
        if (queue.size() == 0) return;
        while (true) {
            HttpUrl<M3u8> take = queue.take();

            //m3u8文件
            if (take.context.httpUrl == take) {
                HttpDownload.downLoad(take, bytes -> {
                    String string = new String(bytes, StandardCharsets.UTF_8);
                    // #EXTM3U
                    // #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=600000,RESOLUTION=720x1280
                    // #EXTINF:5.8,
                    take.context.lines = Arrays.asList(string.split("\n"));
                    take.context.lines.stream()
                            .filter(line -> !line.startsWith("#"))
                            .forEach(line -> {
                                if (line.endsWith(".m3u8"))
                                    queue.add(new M3u8(take.sub(line).url, take.context.name).httpUrl);
                                else {
                                    queue.add(take.sub(line));
                                    take.context.add();
                                }
                            });
                    take.context.time = take.context.lines.stream()
                            .filter(line -> line.startsWith("#EXTINF"))
                            .map(line -> new BigDecimal(line.substring(8, line.length() - 1)))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    System.out.println("视频m3u8 " + take.context.time + " " + take.url + " " + take.local);
                    take.context.subSuccess();
                }, true, e -> queue.add(take));
            } else {
                HttpDownload.downLoad(take, bytes -> take.context.subSuccess(), false, e -> queue.add(take));
            }
        }
    }
}
