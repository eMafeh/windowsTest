package cache;

import fun.qianrui.staticUtil.computer.SerializableUtil;
import fun.qianrui.staticUtil.data.BigFile;
import fun.qianrui.staticUtil.sys.ExceptionUtil;
import http.HttpUrl;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

import static m3u8.Constant.RESOURCE_1114SP_PIC;
import static m3u8.Constant.RESOURCE_1114SP_VIDEO;

public class Resource1114sp implements Serializable, HtmlToResource {
    private static final long serialVersionUID = 1L;
    public static final BigFile VIDEO_CACHE = new BigFile(RESOURCE_1114SP_VIDEO, 250000, 300);
    public static final BigFile PIC_CACHE = new BigFile(RESOURCE_1114SP_PIC, 250000, 100);
    public final String id;
    public final String type;
    public final String m3u8;
    public final String name;
    private transient String pic;

    private Resource1114sp(String id, String type, String m3u8, String name) {
        this.id = id;
        this.type = type;
        this.m3u8 = m3u8;
        this.name = name;
    }

    private static String videoUrl(String id) {
        return "http://www.1114sp.com/play/" + id + "-0-1.html";
    }

    private static String picUrl(String id) {
        return "http://www.1114sp.com/vod/" + id + ".html";
    }

    public static void update(int start, int end, LinkedBlockingQueue<HttpUrl<Function<ArrayList<String>, Boolean>>> queue) {
        for (int i = start; i < end; i++) {
            final String id = i + "";
            if (!PIC_CACHE.exist(id))
                queue.add(new HttpUrl<>(picUrl(id), list -> {
                    final String pic = pic(list);
                    if (pic != null) {
                        PIC_CACHE.put(id, pic.getBytes());
                        return true;
                    }
                    return false;
                }));
            if (!VIDEO_CACHE.exist(id))
                queue.add(new HttpUrl<>(videoUrl(id), list -> {
                    Resource1114sp trans = video(id, list);
                    if (trans != null) {
                        VIDEO_CACHE.put(id, SerializableUtil.serializable(trans));
                        return true;
                    }
                    return false;
                }));
        }
    }

    private static String pic(ArrayList<String> list) {
        return list.stream()
                .filter(line -> line.startsWith("                <dt><img class=\"lazy\" data-original="))
                .map(line -> "http:" + line.split("\"", 5)[3])
                .findFirst()
                .orElse(null);
    }

    public static Resource1114sp video(String id, List<String> list) {
        String m3u8 = null, type = null, name = null;
        for (String s : list) {
            if (s.startsWith("<script>var player=unescape(")) {
                m3u8 = ExceptionUtil.throwT(() -> URLDecoder.decode(s.split("\"", 3)[1], "UTF-8"))
                        .split("=", 2)[1];
            } else if (s.startsWith("        <span class=\"cat_pos_l\">您的位置：")) {
                final String[] split = s.split("</a>");
                type = split[1].substring(split[1].lastIndexOf('>') + 1);
                name = split[2].substring(split[2].lastIndexOf('>') + 1, split[2].length() - 3);
            }
        }
        return m3u8 == null ? null : new Resource1114sp(id, type, m3u8, name);
    }

    @Override
    public String toString() {
        return id + '\t' + type + '\t' + name + '\t' + m3u8;
    }

    @Override
    public String url() {
        return videoUrl(id);
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String m3u8() {
        return m3u8;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String pic() {
        if (pic == null) {
            final byte[] bytes = PIC_CACHE.get(id);
            pic = bytes == null ? "" : new String(bytes);
        }
        return pic;
    }

    public static List<Resource1114sp> getAll() {
        final HashMap<String, byte[]> all = PIC_CACHE.getAll();
        return VIDEO_CACHE.getAll()
                .values()
                .stream()
                .map(bytes -> (Resource1114sp) SerializableUtil.deSerializable(bytes))
                .peek(r -> {
                    final byte[] bytes = all.get(r.id);
                    r.pic = bytes == null ? "" : new String(bytes);
                })
                .collect(Collectors.toList());
    }
}
