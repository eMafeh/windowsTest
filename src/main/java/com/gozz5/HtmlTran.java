package com.gozz5;

import fun.qianrui.staticUtil.ExceptionUtil;
import fun.qianrui.staticUtil.SerializableUtil;
import m3u8.M3u8;

import java.io.Serializable;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HtmlTran {
    private static final Path path = Paths.get("F:\\Downloads\\m3u8\\gozz5.com\\video\\mapping.txt");

    public static class Trans implements Serializable {
        private static final long serialVersionUID = 1L;
        private String url;
        private final List<String> pic = new ArrayList<>();
        private String title;

        public String toString(String dir) {
            return M3u8.toQueue(dir, title, url);
        }

        private void setPath(String path) {
            if (this.url == null && path.endsWith("m3u8")) this.url = path;
            else if (this.title == null && path.endsWith("jpg")) this.pic.add(path);
        }

        public String getUrl() {
            return url;
        }

        public List<String> getPic() {
            return pic;
        }

        public String getTitle() {
            return title;
        }

        public boolean pathOk() {
            return url != null && pic.size() != 0;
        }
    }

    //url, pic, title
    public static List<Trans> tran() {
        List<Trans> list = ExceptionUtil.hidden(() -> SerializableUtil.deSerializable(Files.readAllBytes(path)), null);
        if (list != null) return list;
        ArrayList<Trans> collect = (ArrayList<Trans>) HtmlDownLoad.queue.stream()
                .parallel()
                .map(httpUrl -> {
                    if (!httpUrl.local.toFile()
                            .exists()) return null;
                    List<String> strings = ExceptionUtil.hidden(() -> Files.readAllLines(httpUrl.local), Collections.emptyList());
                    Trans trans = new Trans();
                    for (String s : strings) {
                        if (s.startsWith("\tvar ybUrl") || s.startsWith("\tvar url") || s.startsWith("\tvar picurl")) {
                            s = s.split("'")[1];
                            if (!s.contains("//")) s = s.replaceAll(" ", "/");
                            for (String s1 : s.split("[\t ]")) {
                                trans.setPath(s1);
                            }
                        }
                        if (trans.pathOk()) break;
                    }
                    if (trans.url != null) {
                        trans.title = strings.get(4)
                                .substring(7, strings.get(4)
                                        .length() - 8)
                                .replaceAll("-Yellow字幕网在线-91porn国产资源、手机网站免费观看", "");
                        return trans;
                    }
                    ExceptionUtil.hidden(() -> {
                        Files.delete(httpUrl.local);
                        return null;
                    }, null);
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        ExceptionUtil.hidden(() -> Files.write(path, SerializableUtil.serializable(collect), StandardOpenOption.CREATE, StandardOpenOption.WRITE), null);
        return collect;
    }
}
