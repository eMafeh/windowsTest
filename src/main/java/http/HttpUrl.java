package http;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpUrl<T> {
    public static final String LOCAL = "F:\\Downloads\\m3u8\\";
    public static final String RESULT = "F:\\Downloads\\m3u8result";
    public final String url;
    public final String httpTitle;
    public final String domain;
    public final String mapping;
    public final String param;
    public final T context;
    public final Path local;

    public HttpUrl(String url) {
        this(url, null, null);
    }

    public HttpUrl(String url, T context) {
        this(url, null, context);
    }

    public HttpUrl(String url, Path local, T context) {
        this.context = context;
        //追加头
        url = !url.startsWith("http") ? "http://" + url : url;
        this.url = url;

        //?分割出param
        String[] strings = url.split("\\?", 2);
        this.param = strings.length == 2 ? "?" + strings[1] : "";
        url = strings.length == 2 ? strings[0] : url;

        //  / 分割出三段
        String[] split = url.split("/", 4);
        this.httpTitle = split[0] + "//";
        this.domain = split[2];
        this.mapping = "/" + (split.length == 4 ? split[3] : "");
        this.local = local == null ? Paths.get(LOCAL + domain.replaceAll(":", ".") + mapping) : local;
    }

    public HttpUrl<T> sub(String mapping) {
        return mapping.startsWith("/") ?
                new HttpUrl<>(httpTitle + domain + mapping, context) :
                new HttpUrl<>(httpTitle + domain + this.mapping.substring(0, this.mapping.lastIndexOf('/') + 1) + mapping, context);
    }

    @Override
    public String toString() {
        return "HttpUrl{" +
                "url='" + url + '\'' +
                ", httpTitle='" + httpTitle + '\'' +
                ", domain='" + domain + '\'' +
                ", mapping='" + mapping + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
