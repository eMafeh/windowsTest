package com.gozz5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class M3u8Searcher {

    public static void main(String[] args) {
        List<HtmlTran.Trans> collect = HtmlTran.tran();
        queue(collect, "三上");//台湾SWAG
    }

    private static void reduce(List<HtmlTran.Trans> collect) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (HtmlTran.Trans trans : collect) {
            map.computeIfAbsent(trans.getTitle(), k -> new ArrayList<>())
                    .add(trans.getUrl());
        }
        map.entrySet()
                .stream()
                .filter(l -> l.getValue()
                        .size() > 1)
                .forEach(l -> System.out.println(l));
    }

    private static void queue(List<HtmlTran.Trans> collect, String target) {
        collect.stream()
                .filter(s -> s.getTitle()
                        .contains(target))
                .map(s -> s.toString(target))
                .sorted()
                .forEach(System.out::println);
        System.out.println(collect.size());
    }
}
