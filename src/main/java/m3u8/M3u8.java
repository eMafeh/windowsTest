package m3u8;

import cache.Resource1114sp;
import cache.ResourceGood;
import cache.ResourceGozz5;
import fun.qianrui.staticUtil.computer.AesUtil;
import fun.qianrui.staticUtil.computer.StringUtil;
import fun.qianrui.staticUtil.data.BigFile;
import fun.qianrui.staticUtil.file.FileUtil;
import fun.qianrui.staticUtil.sys.ExceptionUtil;
import fun.qianrui.staticUtil.sys.ThreadUtil;
import http.HttpHelper;
import http.HttpUrl;

import javax.crypto.Cipher;
import java.io.File;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static m3u8.Constant.M3U8_TO_RESOURCE;
import static m3u8.Constant.RESULT;

public class M3u8 {
    public static final BigFile KEY_LOGGER = new BigFile(Constant.KEY_LINE, 1_000_000, 100);
    private static final BigFile M3U8_RESOURCE = new BigFile(M3U8_TO_RESOURCE, 1_000_000, 35_000);

    private static volatile boolean isPre = false;

    private static final LongAdder inQueue = new LongAdder();
    private static final LongAdder outQueue = new LongAdder();
    private static final LongAdder Queue404 = new LongAdder();

    private static class Down {
        public static final LinkedBlockingQueue<M3u8> queue = new LinkedBlockingQueue<>();
        public static final Thread update = ThreadUtil.createLoopThread("down m3u8 file", () -> {
            final M3u8 take = queue.take();
            HttpHelper.notLocal(take.httpUrl, bytes -> {
//                System.out.println("success " + take.httpUrl.url);
                handNew(take.httpUrl.url, FileUtil.readAllLines(bytes));
                take.subInit();
                outQueue.increment();
            }, (a, b, c) -> {
//                System.out.println(c + " " + take.httpUrl.url + " 失败次数:" + take.httpUrl.fail);
                if (c == 404) Queue404.increment();
                else queue.add(take);
            }, 10);
        });

        static {
            update.start();
        }
    }

    //自身网络资源位置
    public final HttpUrl<M3u8> httpUrl;

    //生成视频位置需要的信息
    public final String dir;
    public final String name;
    public final String dirAndName;
    private int subSize;
    private volatile BigDecimal time;

    public static void waitPrepare() throws InterruptedException {
        System.out.println("wait m3u8 prepare");
        while (true) {
            final int i = inQueue.intValue(), o = outQueue.intValue(), o404 = Queue404.intValue();
            if (i == o + o404) break;
            System.out.println("m3u8 file need down " + i + " - ok:" + o + " - notFound:" + o404 + " = " + (i - o - o404));
            Thread.sleep(1000);
        }
        if (inQueue.intValue() != 0) {
            Down.update.interrupt();
        }
        isPre = true;
        System.out.println("m3u8 is prepare");
    }

    private static void checkPre() {
        ExceptionUtil.isTrue(isPre, () -> "need M3u8.waitPrepare();");
    }

    public M3u8(String dir0, String name0, String url) {
        ExceptionUtil.isTrue(url.endsWith(".m3u8"));
        this.httpUrl = new HttpUrl<>(url, this);

        this.dir = FileUtil.goodPath(dir0);
        this.name = FileUtil.goodPath(name0);
        this.dirAndName = this.dir + "\\" + this.name;

        if (M3U8_RESOURCE.exist(httpUrl.url)) {
            subInit();
        } else {
            inQueue.increment();
            Down.queue.add(this);
        }
    }

    public static boolean exist(String url) {
        return M3U8_RESOURCE.exist(url);
    }

    public List<HttpUrl<M3u8>> subInit() {
        final ArrayList<String> trans = FileUtil.readAllLines(M3U8_RESOURCE.get(httpUrl.url));
        this.time = new BigDecimal(trans.get(0));
        final String keyLine = trans.get(1);
        final String pre = trans.get(2);
        List<HttpUrl<M3u8>> sub = new ArrayList<>();
        if (!keyLine.isEmpty()) {
            //#EXT-X-KEY:METHOD=AES-128,URI="key.key"
            Stream.of(keyLine.split(","))
                    .filter(line -> line.startsWith("URI"))
                    .map(line -> line.split("=")[1].replaceAll("\"", ""))
                    .forEach(line -> sub.add(httpUrl.sub(line)));
            KEY_LOGGER.put(httpUrl.url, keyLine.getBytes());
        }
        if (trans.size() == 3) trans.add("");
        trans.stream()
                .skip(3)
                .map(end -> pre + end)
                .filter(url -> !url.endsWith("/777.ts"))
                .forEach(url -> {
                    if (url.endsWith(".m3u8")) {
                        sub.add(new M3u8(dir, name, url).httpUrl);
                    } else {
                        sub.add(new HttpUrl<>(url, this));
                    }
                });
        this.subSize = sub.size();
        return sub;
    }

    public static M3u8 cacheSubM3u8(String m3u8Url) {
        if (!M3u8.exist(m3u8Url)) {
            return null;
        }
        final M3u8 m3u8 = new M3u8("", "", m3u8Url);
        //全部子集
        return m3u8.subInit()
                .stream()
                .filter(url -> url.context != m3u8)
                .map(url -> url.context)
                .max((a, b) -> {
                    final String au = a.httpUrl.url;
                    final String bu = b.httpUrl.url;
                    final int i = au.length() - bu.length();
                    if (i != 0) return i;
                    return au.compareTo(bu);
                })
                .orElse(null);
    }

    public void tsJoin(BigFile bigFile) {
        final int size = bigFile.getSize();
        ExceptionUtil.isTrue(size >= subSize, () -> "subSize:" + subSize + " bigFile just:" + size);
        bigFile.perfectLength();
        final String ts = ts();
        if (new File(ts).exists()) return;
        final HashMap<String, byte[]> all = bigFile.getAll();
        final List<HttpUrl<M3u8>> subs = subInit();

        Stream<byte[]> stream = subs.stream()
                .map(httpUrl -> httpUrl.url)
                .filter(url -> url.endsWith(".ts"))
                .map(all::get);
        final Cipher decrypt = subs.stream()
                .map(httpUrl -> httpUrl.url)
                .filter(url -> url.endsWith("key.key"))
                .map(url -> AesUtil.decrypt(all.get(url)))
                .findFirst()
                .orElse(null);
        if (decrypt != null) stream = stream.map(b -> ExceptionUtil.throwT(() -> decrypt.doFinal(b)));
        List<byte[]> collect = stream.collect(Collectors.toList());
        final long length = collect.stream()
                .mapToLong(b -> b.length)
                .sum();
        System.out.println("create:" + length + " " + ts);
        final MappedByteBuffer map = FileUtil.write(ts, length);
        collect.forEach(map::put);
    }

    public String mp4() {
        return RESULT + dirAndName
                + httpUrl.domain.replaceAll(":", ".")
                + httpUrl.mapping.substring(0, httpUrl.mapping.length() - 10)
                .replaceAll("/", "-") + ".mp4";
    }

    public String ts() {
        return RESULT + dirAndName
                + httpUrl.domain.replaceAll(":", ".")
                + httpUrl.mapping.substring(0, httpUrl.mapping.length() - 10)
                .replaceAll("/", "-") + ".ts";
    }

    @Override
    public String toString() {
        return time + dirAndName + "\t" + this.httpUrl.url;
    }

    public int getSubSize() {
        return subSize;
    }

    public BigDecimal getTime() {
        return time;
    }

    public BigFile bigFile() {
        checkPre();
        return new BigFile(bigPath(), subSize, 150_000L);
    }

    public String bigPath() {
        return Constant.BIG_TS + httpUrl.domain.replaceAll(":", ".") + "\\"
                + httpUrl.mapping.replaceAll("/hls/index\\.m3u8", "") + "cache";
    }

    private static void handNew(String url, final ArrayList<String> lines) {
        final ArrayList<String> goodLine = new ArrayList<>();
        final HttpUrl<M3u8> httpUrl = new HttpUrl<>(url, null);
        // #EXTM3U
        ExceptionUtil.isTrue(lines.get(0)
                .startsWith("#EXTM3U"));
        String keyLine = "";
        BigDecimal time = BigDecimal.ZERO;
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.length() == 0) continue;
            if (line.startsWith("#")) {
                //#EXT-X-KEY:METHOD=AES-128,URI="key.key"
                if (line.startsWith("#EXT-X-KEY")) keyLine = line;
                    // #EXTINF:5.8,
                else if (line.startsWith("#EXTINF:"))
                    time = time.add(new BigDecimal(line.substring("#EXTINF:".length(), line.length() - 1)));
            } else goodLine.add(httpUrl.sub(line).url);
        }
        final String pre = StringUtil.samePre(goodLine);
        final List<String> preList = goodLine.stream()
                .map(s -> s.substring(pre.length()))
                .collect(Collectors.toList());
        final ArrayList<String> trans = new ArrayList<>();
        trans.add(time.toString());
        trans.add(keyLine);
        trans.add(pre);
        trans.addAll(preList);
        M3U8_RESOURCE.put(url, String.join("\n", trans)
                .getBytes());
    }

    public static void main(String[] args) {
        final Set<String> cache = ResourceGood.getAll()
                .map(r -> r.m3u8)
                .collect(Collectors.toSet());
        final Set<String> set = Resource1114sp.getAll()
                .stream()
                .map(r -> r.m3u8)
                .collect(Collectors.toSet());
        final Set<String> set2 = ResourceGozz5.getAll()
                .stream()
                .map(r -> r.m3u8)
                .collect(Collectors.toSet());
        final Set<String> collect = M3U8_RESOURCE.getKeys()
                .stream()
                .filter(s -> !cache.contains(s) && !set.contains(s) && !set2.contains(s))
                .collect(Collectors.toSet());
        for (String s : collect) {
            System.out.println(s);
        }
        System.out.println(collect.size());
    }
}