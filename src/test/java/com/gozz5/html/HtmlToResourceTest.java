package com.gozz5.html;

import cache.*;
import m3u8.CheckSuccess;
import m3u8.DownloadList;
import m3u8.M3u8;
import org.testng.annotations.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HtmlToResourceTest {

    @Test
    public void date() {
        Stream.concat(ResourceGozz5.getAll()
                .stream(), Resource1114sp.getAll()
                .stream())
                .filter(h -> !h.m3u8()
                        .endsWith(".m3u8"))
                .forEach(System.out::println);
    }

    @Test
    public void decode() throws UnsupportedEncodingException {
        final String s = "%2F%6A%73%2F%70%6C%61%79%65%72%2F%41%70%6C%61%79%65%72%2E%68%74%6D%6C%3F%75%72%6C%3D%68%74%74%70%73%3A%2F%2F%32%2E%64%64%79%75%6E%62%6F%2E%63%6F%6D%2F%73%68%61%72%65%2F%42%38%76%6A%47%70%44%78%77%31%76%70%4D%6E%6C%74";
        System.out.println(URLDecoder.decode(s, "UTF-8"));
    }

    @Test
    public void deep() {
        final Set<File> set = DownloadList.list.stream()
                .map(M3u8::bigPath)
                .map(path -> new File(path).getAbsoluteFile())
                .collect(Collectors.toSet());
        final Set<String> notList = allFile(new File("F:\\Downloads\\bigTs")).stream()
                .filter(f -> f.getName()
                        .endsWith("cache"))
                .filter(f -> !set.contains(f))
                .map(File::toString)
                .filter(s -> !s.endsWith("\\cache"))
                .map(s -> s.substring(19, s.length() - 5)
                        .replaceAll("com\\.", "com:")
                        .replaceAll("\\\\", "/") + "/hls/index.m3u8")
                .collect(Collectors.toSet());
        System.out.println(notList.size());
    }

    private static List<File> allFile(File file) {
        final ArrayList<File> result = new ArrayList<>();
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files != null)
                for (File f : files) {
                    result.addAll(allFile(f));
                }
        }
        result.add(file);
        return result;
    }
}