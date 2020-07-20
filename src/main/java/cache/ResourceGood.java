package cache;

import fun.qianrui.staticUtil.computer.SerializableUtil;
import fun.qianrui.staticUtil.computer.StringUtil;
import fun.qianrui.staticUtil.data.BigFile;
import fun.qianrui.staticUtil.file.FileUtil;
import fun.qianrui.staticUtil.sys.ExceptionUtil;
import http.HttpUrl;
import m3u8.Constant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static m3u8.Constant.GOZZ5_PIC_CACHE;

public class ResourceGood implements Serializable, HtmlToResource {
    private static final long serialVersionUID = 1L;
    public static final BigFile CACHE = new BigFile(Constant.RESOURCE_GOOD, 250000, 400);
    public final String id;
    public final String type;
    public final String m3u8;
    public final String name;
    public final String pic;
    public final BigDecimal time;
    public final int resolution;
    private final String date;

    public ResourceGood(String id, String type, String m3u8, String name, String pic, BigDecimal time) {
        this.id = id;
        this.type = type;
        this.m3u8 = m3u8;
        this.name = name;
        this.pic = pic;
        this.time = time;
        this.resolution = ExceptionUtil.hidden(() -> Integer.parseInt(m3u8.split("/", 7)[5].replaceAll("kb", "")), 0);
        this.date = m3u8.split("/", 5)[3];
    }

    @Override
    public String url() {
        return id;
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

    public void log() {
        CACHE.put(id, SerializableUtil.serializable(this));
    }

    public static Stream<ResourceGood> getAll() {
        return CACHE.getAll()
                .values()
                .stream()
                .map(SerializableUtil::deSerializable);
    }

    static ResourceGood best(ArrayList<ResourceGood> list) {
        return list.stream()
                .max((a, b) -> {
                    final int i = a.time.compareTo(b.time);
                    if (i != 0) return i;
                    final int size = a.resolution - b.resolution;
                    return size != 0 ? size : b.date.compareTo(a.date);
                })
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceGood that = (ResourceGood) o;
        return m3u8.equals(that.m3u8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m3u8);
    }

    public HttpUrl<String> picHttpUrl() {
        final String pic = pic();
        return StringUtil.isEmpty(pic) ? null : new HttpUrl<>(pic, picPath());
    }

    public String picPath() {
        return GOZZ5_PIC_CACHE + resolution + "\\" + type + "\\" + FileUtil.goodPath(name) + ".jpg";
    }

    public String addListString() {
        return "list.add(new M3u8(\"" + type + "\",\"" + name + "\",\"" + m3u8 + "\"));/*" + time + "*/";
    }
}
