package m3u8;

import fun.qianrui.staticUtil.data.BigFile;
import fun.qianrui.staticUtil.sys.ExceptionUtil;
import fun.qianrui.staticUtil.sys.ThreadUtil;
import http.HttpHelper;
import http.HttpUrl;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * https://gozz5.com/
 * console.log("queue.add(new M3u8(\""+document.getElementsByTagName('title')[0].innerHTML.split("-Yellow字幕网在线-91porn国产资源、手机网站免费观看")[0]+"\",\""+url+"\").httpUrl);")
 * http://www.1114sp.com/
 * console.log("queue.add(new M3u8(\""+document.getElementsByTagName('title')[0].innerHTML.split("第1集在线播放_淫色视频网")[0]+"\",\""+document.getElementsByTagName("iframe")[0].src.split("=")[1]+"\").httpUrl);")
 * <p>
 * <p>
 * //        queue.add(new M3u8("","").httpUrl);
 */
public class M3U8Download {
    public static final LinkedBlockingQueue<HttpUrl<M3u8>> queue = new LinkedBlockingQueue<>();
    public static final ConcurrentHashMap<M3u8, BigFile> MAP = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮35", "https://video2.ddbtss.com:8091/99920180131/9992018013100168/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮32", "https://video2.ddbtss.com:8091/99920180131/9992018013100165/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮33", "https://video2.ddbtss.com:8091/99920180131/9992018013100166/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮31", "https://video2.ddbtss.com:8091/99920180131/9992018013100164/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮34", "https://video2.ddbtss.com:8091/99920180131/9992018013100167/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮30", "https://video2.ddbtss.com:8091/99920180131/9992018013100163/650kb/hls/index.m3u8").httpUrl);

        final List<String> blackList = Arrays.asList(
//                "videocdn2.quweikm.com:8091"
        );

        M3u8.waitPrepare();
        System.out.println(DownloadList.list.size());
        DownloadList.list.stream()
                .filter(m3u8 -> !new File(m3u8.ts()).exists())
                .filter(m3u8 -> {
                    final boolean b = !blackList.contains(m3u8.httpUrl.domain);
                    if (!b) System.out.println(m3u8);
                    return b;
                })
                .forEach(m3u8 -> queue.add(m3u8.httpUrl));
        start();
    }

    public static void start() {
        ThreadUtil.createLoopThread("down tasks", () -> {
            HttpUrl<M3u8> take = queue.take();
            final BigFile bigFile = MAP.computeIfAbsent(take.context, M3u8::bigFile);
            if (take.context.httpUrl == take) {
                /*m3u8文件*/
                take.context.subInit()
                        .stream()
                        .filter(h -> !bigFile.exist(h.url))
                        .forEach(queue::add);
                return;
            }
            ExceptionUtil.isTrue(!bigFile.exist(take.url), () -> "不可能发生" + take);
            HttpHelper.notLocal(take,
                    bytes -> bigFile.put(take.url, bytes),
                    (a, b, c) -> queue.add(take),
                    Integer.MAX_VALUE);
        })
                .start();

//        final ThreadPoolExecutor bigfileToTs = ThreadUtil.createPool(1, 1, "bigfileToTs");
        final ConcurrentHashMap<M3u8, Integer> lastSuccess = new ConcurrentHashMap<>();
        ThreadUtil.createLoopThread("show tasks", () -> {
            final Map<M3u8, BigFile> temp = new HashMap<>(MAP);
            int m3u8_d = 0, ts_ok = 0, ts_d = 0, m3u8_n = temp.size(),
                    m3u8_a = temp.keySet()
                            .stream()
                            .mapToInt(M3u8::getSubSize)
                            .sum();
            final ArrayList<String> list = new ArrayList<>();
            for (Map.Entry<M3u8, BigFile> entry : temp.entrySet()) {
                M3u8 task = entry.getKey();
                final int subSize = task.getSubSize();
                final BigFile value = entry.getValue();
                final int success = value.getSize();
                if (subSize <= success) {
                    MAP.remove(task);
                    try {
                        task.tsJoin(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    bigfileToTs.execute(() -> );
                }
                final Integer put = lastSuccess.put(task, success);
                final int last = put == null ? 0 : put;
                final int change = success - last;
                if (change != 0) {
                    m3u8_d++;
                    list.add(change + "  " + success + "/" + subSize + " " + task);
                }
                ts_ok += success;
                ts_d += change;
            }
            list.stream()
                    .sorted((a, b) -> -a.compareTo(b))
                    .limit(5)
                    .forEach(System.out::println);
            System.out.println("m3u8(活跃 还剩 总ts):" + m3u8_d + " " + m3u8_n + " " + m3u8_a + " ts(新增 完成 还剩):" + ts_d + " " + ts_ok + " " + (m3u8_a - ts_ok));
            Thread.sleep(10000L);
        })
                .start();
    }
}
