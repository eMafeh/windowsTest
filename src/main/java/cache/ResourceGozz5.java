package cache;

import fun.qianrui.staticUtil.computer.SerializableUtil;
import fun.qianrui.staticUtil.data.BigFile;
import fun.qianrui.staticUtil.sys.ExceptionUtil;
import http.HttpUrl;
import m3u8.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResourceGozz5 implements Serializable, HtmlToResource {
    private static final long serialVersionUID = 1L;
    public static final BigFile CACHE = new BigFile(Constant.RESOURCE_GOZZ5, 50000, 400);
    public final String id;
    public final String type;
    public final String m3u8;
    public final String name;
    public final String pic;

    private ResourceGozz5(String id, String type, String m3u8, String name, String pic) {
        this.id = id;
        this.type = type;
        this.m3u8 = m3u8;
        this.name = name;
        this.pic = pic;
    }

    private static String url(String id) {
        return "https://gozz5.com/video/show/id/" + id;
    }

    public static void update(int start, int end, LinkedBlockingQueue<HttpUrl<Function<ArrayList<String>, Boolean>>> queue) {
        for (int i = start; i < end; i++) {
            final String id = i + "";
            final String url = url(id);
            if (!CACHE.exist(id))
                queue.add(new HttpUrl<>(url, list -> {
                    ResourceGozz5 trans = gozz5(id, list);
                    if (trans != null) {
                        CACHE.put(trans.id, SerializableUtil.serializable(trans));
                        return true;
                    }
                    return false;
                }));
        }
    }

    public static ResourceGozz5 gozz5(String id, ArrayList<String> list) {
        ExceptionUtil.isTrue(list.size() > 500);
        String m3u8 = null, type = null, name = null, pic = null;
        for (String s : list) {
            if (s.startsWith("\tvar ybUrl") || s.startsWith("\tvar url") || s.startsWith("\tvar picurl")) {
                s = s.split("'")[1];
                if (!s.contains("//")) s = s.replaceAll(" ", "/");
                for (String path : s.split("[\t ]")) {
                    if (m3u8 == null && path.endsWith("m3u8")) m3u8 = path;
                    else if (path.endsWith("jpg")) pic = path;
                }
            } else if (s.startsWith("                    <a href=\"/video/index/cid/")) {
                type = s.split("<")[1].split(">")[1].trim();
            } else if (s.startsWith("                    <a href=\"/video/show/id/")) {
                //a href="/video/show/id/35126" class="select">JPGM-8139	[台湾SWAG] 有着最大亮点美乳18岁女孩
                String s1 = s.split("<")[1];
                String[] split = s1.split(">");
                //JPGM-8139	[台湾SWAG] 有着最大亮点美乳18岁女孩
                name = split[1].trim();
            }
        }
        return m3u8 == null ? null : new ResourceGozz5(id, type, m3u8, name, pic);
    }

    @Override
    public String toString() {
        return id + '\t' + type + '\t' + name + '\t' + m3u8;
    }

    @Override
    public String url() {
        return url(id);
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
        return pic;
    }

    public static List<ResourceGozz5> getAll() {
        return CACHE.getAll()
                .values()
                .stream()
                .map(bytes -> (ResourceGozz5) SerializableUtil.deSerializable(bytes))
                .collect(Collectors.toList());
    }
}
