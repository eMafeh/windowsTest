package cache;


import fun.qianrui.staticUtil.file.FileUtil;
import fun.qianrui.staticUtil.sys.ThreadUtil;
import http.HttpHelper;
import http.HttpUrl;
import m3u8.M3u8;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CacheBoot {
    public static void main(String[] args) throws InterruptedException {
        htmlDown();
        m3u8Down();
        picDown();
    }

    private static void htmlDown() throws InterruptedException {
        LinkedBlockingQueue<HttpUrl<Function<ArrayList<String>, Boolean>>> queue = new LinkedBlockingQueue<>();
        ResourceGozz5.update(1, 38000, queue);
        Resource1114sp.update(21357, 110000, queue);
        int size = queue.size();
        LongAdder bad = new LongAdder();
        LongAdder good = new LongAdder();
        final Thread downHtml = ThreadUtil.createLoopThread("downHtml", () -> {
            HttpUrl<Function<ArrayList<String>, Boolean>> url = queue.take();
            HttpHelper.notLocal(url, bytes -> {
                ArrayList<String> list = FileUtil.readAllLines(bytes);
                if (url.context.apply(list)) {
                    System.out.println("good html :" + url.url);
                    good.increment();
                } else bad.increment();
            }, (e, u, code) -> {
                if (code == 404) bad.increment();
                else queue.add(url);
            }, 10);
        });
        downHtml.start();
        while (true) {
            int s = good.intValue();
            int b = bad.intValue();
            final int now = s + b;
            System.out.println("总数:" + size + " 成功:" + s + " 失败:" + b + " 剩余: " + (size - now));
            if (now == size) {
                downHtml.interrupt();
                break;
            }
            Thread.sleep(3000);
        }
    }

    private static void m3u8Down() throws InterruptedException {
        final List<HtmlToResource> resources = Stream.concat(ResourceGozz5.getAll()
                .stream(), Resource1114sp.getAll()
                .stream())
                .filter(r -> !ResourceGood.CACHE.exist(r.url()))
                .filter(r -> r.m3u8()
                        .endsWith(".m3u8"))
                .peek(r -> new M3u8("", "", r.m3u8()))
                .collect(Collectors.toList());
        M3u8.waitPrepare();
        System.out.println("未转化html数量: " + resources.size());
        resources.forEach(r -> {
            String m3u8Str = r.m3u8();
            BigDecimal time = null;
            while (true) {
                M3u8 sub = M3u8.cacheSubM3u8(m3u8Str);
                if (sub != null) {
                    m3u8Str = sub.httpUrl.url;
                    time = sub.getTime();
                    if (time != null) break;
                } else break;
            }
            if (time != null)
                new ResourceGood(r.url(), r.type(), m3u8Str, r.name(), r.pic(), time).log();
        });
    }

    private static void picDown() throws InterruptedException {
        LinkedBlockingQueue<HttpUrl<String>> queue = new LinkedBlockingQueue<>();
        System.out.println("开始图片下载");
        final long l = System.currentTimeMillis();
        ResourceGood.getAll()
                .map(ResourceGood::picHttpUrl)
                .filter(Objects::nonNull)
                .filter(h -> !new File(h.context).exists())
                .forEach(queue::add);
        int size = queue.size();
        System.out.println("本地图片对比完毕(" + (System.currentTimeMillis() - l) + "ms),待下载:" + size);
        LongAdder success = new LongAdder();
        LongAdder c404 = new LongAdder();
        ThreadUtil.createLoopThread("down pic", () -> {
            final HttpUrl<String> take = queue.take();
            HttpHelper.notLocal(take, bytes -> {
                try {
                    FileUtil.Writer.async(take.context, bytes, success::increment);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, (e, url, code) -> {
                if (code == 404) c404.increment();
                else queue.add(url);
            }, 10);
        })
                .start();
        while (true) {
            Thread.sleep(3000);
            int s = success.intValue();
            final int fail = c404.intValue();
            final int less = size - (s + fail);
            System.out.println("总数:" + size + "成功:" + s + "失败:" + fail + "剩余:" + less);
            if (less == 0) System.exit(0);
        }
    }
}
