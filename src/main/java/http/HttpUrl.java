package http;

public class HttpUrl<T> {
    public final String url;
    public final T context;
    public final String httpTitle;
    public final String domain;
    public final String mapping;
    public int fail;

    //    public final String param;
    public HttpUrl(String url, T context) {
        this.context = context;
        //追加头
        url = !url.startsWith("http") ? "http://" + url : url;
        this.url = url;

        //?分割出param
        String[] strings = url.split("\\?", 2);
//        this.param = strings.length == 2 ? "?" + strings[1] : "";
        url = strings.length == 2 ? strings[0] : url;

        //  / 分割出三段
        String[] split = url.split("/", 4);
        this.httpTitle = split[0] + "//";
        this.domain = split[2];
        this.mapping = "/" + (split.length == 4 ? split[3] : "");
    }

    public HttpUrl<T> sub(String mapping) {
        return new HttpUrl<>(httpTitle + domain
                + (mapping.startsWith("/") ? "" : this.mapping.substring(0, this.mapping.lastIndexOf('/') + 1))
                + mapping
                , context);
    }

    public String local(String root) {
        return root + domain.replaceAll(":", ".") + mapping;
    }
}
