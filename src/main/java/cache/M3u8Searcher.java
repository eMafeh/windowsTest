package cache;


import m3u8.DownloadList;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class M3u8Searcher {

    public static void main(String[] args) {
//        downM3u8();
//        reduce();
        queue("母子");//台湾SWAG 無毛宣言 翘臀 苍井空 同事 怀孕 母子 三上悠亚 裸贷门 刘玥 闺蜜 学姐偷情 男友 盗撮作品
    }

    private static void queue(String target) {
        final Set<String> thisM3u8 = DownloadList.list.stream()
                .map(m -> m.httpUrl.url)
                .collect(Collectors.toSet());
        System.out.println(DownloadList.list.size() + " " + thisM3u8.size());
        //不和历史M3U8重复，自身M3U8唯一的列表，可能还是存在重复
        final List<ResourceGood> goods = ResourceGood.getAll()
                .filter(s -> s.name
                        .contains(target))
                .filter(s -> !thisM3u8.contains(s.m3u8))
                .distinct()
                .collect(Collectors.toList());
        distinctShow(goods, target);
    }

    public static void distinctShow(List<ResourceGood> goods, String target) {
        final HashMap<BigDecimal, ArrayList<ResourceGood>> lists = goods.stream()
                .collect(Collectors.groupingBy(html -> html.time, HashMap::new, Collectors.toCollection(ArrayList::new)));
        System.out.println("/*" + target + "*/\nstatic{");
        lists.values()
                .stream()
                .filter(l -> l.size() == 1)
                .map(l -> l.get(0))
                .map(ResourceGood::addListString)
                .sorted()
                .forEach(System.out::println);
        lists.values()
                .stream()
                .filter(l -> l.size() > 1)
                .forEach(l -> {
                    System.out.println();
                    l.stream()
                            .map(ResourceGood::addListString)
                            .forEach(System.out::println);
                });
        System.out.println("}");
        System.out.println(goods.size());
    }
}
