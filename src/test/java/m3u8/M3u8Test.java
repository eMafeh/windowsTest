package m3u8;

import cache.M3u8Searcher;
import cache.ResourceGood;
import fun.qianrui.staticUtil.data.BigFile;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class M3u8Test {
    @Test
    public void showKeys() {
        M3u8.KEY_LOGGER.getAll()
                .values()
                .stream()
                .map(String::new)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void bigfileToTs() throws InterruptedException {
        M3u8 m3u8 = new M3u8("巨乳中文av", "SSNI-589 骑乘位爽翻天骑乘位 三上悠亚", "https://videozmcdn.stz8.com:8091/20191105/KFpg2H0E/1000kb/hls/index.m3u8");
        M3u8.waitPrepare();
        final BigFile bigFile = m3u8.bigFile();
        m3u8.tsJoin(bigFile);
    }

    @Test
    public void showList() {
        final Set<String> m3u8s = DownloadList.list.stream()
                .map(m -> m.httpUrl.url)
                .collect(Collectors.toSet());
        final List<ResourceGood> resourceGoods = ResourceGood.getAll()
                .filter(r -> m3u8s.contains(r.m3u8))
                .collect(Collectors.toList());
        M3u8Searcher.distinctShow(resourceGoods, "");
        System.out.println(m3u8s.size());
    }
}