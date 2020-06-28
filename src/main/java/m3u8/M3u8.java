package m3u8;

import fun.qianrui.staticUtil.ExceptionUtil;
import http.HttpUrl;
import util.FfmpegUtil;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

public class M3u8 {
    private static final LongAdder count = new LongAdder();
    public final HttpUrl<M3u8> httpUrl;
    private final LongAdder times = new LongAdder();
    private final LongAdder success = new LongAdder();
    public BigDecimal time = BigDecimal.ZERO;
    public List<String> lines;
    public final String name;

    public M3u8(String url, String name) {
        ExceptionUtil.isTrue(url.endsWith(".m3u8"));
        this.httpUrl = new HttpUrl<>(url, this);
        this.name = name;
        add();
        count.increment();
    }

    public void subSuccess() {
        success.increment();
        ffmpeg();
    }

    public void add() {
        times.increment();
    }

    public void ffmpeg() {
//        System.out.println(success + " " + times);
        if (success.intValue() == times.intValue()) {
            if (!time.equals(BigDecimal.ZERO))
                try {
                    String m1 = httpUrl.local.toString();
                    Path m2 = Paths.get(m1.substring(0, m1.length() - 10) + "trans.m3u8");
                    Files.write(m2, lines.stream()
                            .map(line -> line.startsWith("/") ? line.substring(1) : line)
                            .collect(Collectors.toList()));
                    FfmpegUtil.m3u8(m2.toString(),
                            HttpUrl.LOCAL + httpUrl.domain.replaceAll(":", ".") + httpUrl.mapping.substring(0, httpUrl.mapping.length() - 10)
                                    .replaceAll("/", "-") + name + ".mp4",
                            lines.stream()
                                    .filter(line -> !line.startsWith("#"))
                                    .allMatch(line -> line.contains("/")) ? (HttpUrl.LOCAL + httpUrl.domain.replaceAll(":", ".")) : m2.getParent()
                                    .toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            System.out.println("完成");
            count.decrement();
        }
        if (count.intValue() == 0) {
            System.exit(0);
        }
    }
}