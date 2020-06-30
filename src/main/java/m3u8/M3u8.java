package m3u8;

import fun.qianrui.staticUtil.ExceptionUtil;
import fun.qianrui.staticUtil.FileUtil;
import http.HttpUrl;
import util.FfmpegUtil;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

public class M3u8 {
    public static final boolean downTs = true;
    public static final ConcurrentLinkedQueue<M3u8> tasks = new ConcurrentLinkedQueue<>();
    public final HttpUrl<M3u8> httpUrl;
    private final LongAdder times = new LongAdder();
    private final LongAdder success = new LongAdder();
    public final String dir;
    public final String dirAndName;
    public BigDecimal time = BigDecimal.ZERO;
    public List<String> lines;
    public final String name;
    private int lastSuccess;

    public M3u8(String name, String url) {
        this(null, name, url);
    }

    public M3u8(String dir0, String name0, String url) {
        ExceptionUtil.isTrue(url.endsWith(".m3u8"));
        this.dir = dir0 == null ? null : FileUtil.goodPath(dir0);
        this.name = FileUtil.goodPath(name0);
        this.httpUrl = new HttpUrl<>(url, this);
        this.dirAndName = this.dir == null ? this.name : this.dir + "\\" + this.name;
        add();
        tasks.add(this);
    }

    public static String toQueue(String dir, String title, String url) {
        return "queue.add(new M3u8(\"" + dir + "\",\"" + title + "\",\"" + url + "\").httpUrl);";
    }

    public void subSuccess() {
        success.increment();
        ffmpeg();
    }

    public void add() {
        times.increment();
    }

    public void ffmpeg() {
        if (success.intValue() != times.intValue()) {
            return;
        }
        if (downTs && !BigDecimal.ZERO.equals(time)) {
            try {
                String m1 = httpUrl.local.toString();
                Path m2 = Paths.get(m1.substring(0, m1.length() - 10) + "trans.m3u8");
                Files.write(m2, lines.stream()
                        .map(line -> line.startsWith("/") ? line.substring(1) : line)
                        .collect(Collectors.toList()));
                String m3u8 = m2.toString();
                String mp4 = HttpUrl.RESULT + "\\" + dirAndName
                        + httpUrl.domain.replaceAll(":", ".")
                        + httpUrl.mapping.substring(0, httpUrl.mapping.length() - 10)
                        .replaceAll("/", "-") + ".mp4";
                String dir = lines.stream()
                        .filter(line -> !line.startsWith("#"))
                        .allMatch(line -> line.contains("/")) ?
                        (HttpUrl.LOCAL + httpUrl.domain.replaceAll(":", "."))
                        : m2.getParent()
                        .toString();
                FfmpegUtil.m3u8(m3u8, mp4, dir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tasks.remove(this);
    }

    public M3u8 sub(String line) {
        return new M3u8(dir, name, httpUrl.sub(line).url);
    }

    @Override
    public String toString() {
        return success + "/" + times
                + "\t" + dirAndName
                + "\t" + this.httpUrl.url;
    }

    public int change() {
        int lastSuccess = this.lastSuccess;
        this.lastSuccess = success.intValue();
        return this.lastSuccess - lastSuccess;
    }
}