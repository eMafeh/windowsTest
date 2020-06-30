package m3u8;

import fun.qianrui.staticUtil.ExceptionUtil;
import fun.qianrui.staticUtil.ThreadUtil;
import http.HttpDownload;
import http.HttpUrl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * https://gozz5.com/
 * console.log("queue.add(new M3u8(\""+document.getElementsByTagName('title')[0].innerHTML.split("-Yellow字幕网在线-91porn国产资源、手机网站免费观看")[0]+"\",\""+url+"\").httpUrl);")
 * http://www.1114sp.com/
 * console.log("queue.add(new M3u8(\""+document.getElementsByTagName('title')[0].innerHTML.split("第1集在线播放_淫色视频网")[0]+"\",\""+document.getElementsByTagName("iframe")[0].src.split("=")[1]+"\").httpUrl);")
 * <p>
 * <p>
 * //        queue.add(new M3u8("","").httpUrl);
 */
public class M3U8Download {
    public static final LinkedBlockingQueue<HttpUrl<M3u8>> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
//台湾SWAG  https://gozz5.com/
//        queue.addAll(CheckSuccess.queue);
        queue.add(new M3u8("平胸", "", "https://video1.posh-hotels.com:8091/99920191228/5526id00046-D/index.m3u8").httpUrl);
        queue.add(new M3u8("平胸", "", "https://video1.posh-hotels.com:8091/99920191228/5526id00046-C/index.m3u8").httpUrl);
        queue.add(new M3u8("平胸", "", "https://video1.posh-hotels.com:8091/99920191228/5526id00046-B/index.m3u8").httpUrl);
        queue.add(new M3u8("平胸", "", "https://video1.posh-hotels.com:8091/99920191228/5526id00046-A/index.m3u8").httpUrl);


//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮35", "https://video2.ddbtss.com:8091/99920180131/9992018013100168/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮32", "https://video2.ddbtss.com:8091/99920180131/9992018013100165/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮33", "https://video2.ddbtss.com:8091/99920180131/9992018013100166/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮31", "https://video2.ddbtss.com:8091/99920180131/9992018013100164/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮34", "https://video2.ddbtss.com:8091/99920180131/9992018013100167/650kb/hls/index.m3u8").httpUrl);
//        queue.add(new M3u8("更衣室偷拍", "三浦海岸海の家シャワー更衣室盗撮30", "https://video2.ddbtss.com:8091/99920180131/9992018013100163/650kb/hls/index.m3u8").httpUrl);


        queue.add(new M3u8("盗撮", "SHKD-436	完全盗撮美人妻", "https://videozm.whqhyg.com:8091/20200605/hYD7pb7A/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "1919gogo-7952 盗撮作品 热门美容沙龙 3", "https://videocdn.dlyilian.com:8091/20190420/hd_1919gogo-7952/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "1919gogo-7951 盗撮作品 美容沙龙 3", "https://videocdn.dlyilian.com:8091/20190420/hd_1919gogo-7951/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "1919gogo-7850 女達の羞恥便所盗撮167", "https://videocdn.dlyilian.com:8091/20190105/hd_1919gogo-7850/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "NANP-023  素人ナンパ持ち帰り！盗撮SEX横流し映像 8", "https://video1.feimanzb.com:8091/20180131/NANP-023-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "NANP-022  素人ナンパ持ち帰り！盗撮SEX横流し映像 7", "https://video1.feimanzb.com:8091/20180131/NANP-022-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "NANP-021  素人ナンパ持ち帰り！盗撮SEX横流し映像 6", "https://video1.feimanzb.com:8091/20180131/NANP-021-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "KKJ-036  本気（マジ）口说き 人妻编 16 ナンパ→连れ込み→SEX盗撮→无断で投稿", "https://video1.feimanzb.com:8091/20180115/KKJ-036-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "KKJ-035  本気（マジ）口说き 専门学生?女子大生编 ナンパ→连れ込み→SEX盗撮→无断で投稿", "https://video1.feimanzb.com:8091/20180115/KKJ-035-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "KKJ-034  本気（マジ）口说き 人妻编 15 ナンパ→连れ込み→SEX盗撮→无断で投稿", "https://video1.feimanzb.com:8091/20180115/KKJ-034-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "JKSR-194  温泉レポートだけのはずが… 素人妻ほろ酔い温泉ダマし撮り！ 露天で口说いて浮気SEX完全盗撮！ Case11", "https://video1.feimanzb.com:8091/20180115/JKSR-194-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "ITSR-023  ダマで中出し ナンパ连れ込み素人妻 ガチで盗撮无断で発売 16", "https://video1.feimanzb.com:8091/20180115/ITSR-023-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "ITSR-021  ダマで中出し ナンパ连れ込み素人妻 ガチで盗撮无断で発売 14", "https://video1.feimanzb.com:8091/20180115/ITSR-021-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "HHPR-00282  子供に内绪のお受験ママ里肉体交渉盗撮", "https://video1.feimanzb.com:8091/20180115/HHPR-00282-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "CLUB-188  フィットネスジム通いの美人妻ばかりを寝取る 悪徳インストラクター盗撮", "https://video1.feimanzb.com:8091/20180115/CLUB-188-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "CLUB-180  ヤレる人妻回春マッサージ4 中出し交渉盗撮", "https://video1.feimanzb.com:8091/20180115/CLUB-180-C/550kb/hls/index.m3u8").httpUrl);
        queue.add(new M3u8("盗撮", "BDSR-215  人妻リアル不伦 流出ラブホ盗撮 10", "https://video1.feimanzb.com:8091/20180115/BDSR-215-C/550kb/hls/index.m3u8").httpUrl);


        download();
        show();
    }

    private static void download() {
        ThreadUtil.createLoopThread(() -> {
            HttpUrl<M3u8> take = queue.take();
            //m3u8文件
            if (take.context.httpUrl != take) {
                HttpDownload.downLoad(take, bytes -> take.context.subSuccess(), false, e -> queue.add(take));
                return;
            }
            HttpDownload.downLoad(take, bytes -> {
                String string = new String(bytes, StandardCharsets.UTF_8);
                // #EXTM3U
                ExceptionUtil.isTrue(string.startsWith("#EXTM3U"));
                take.context.lines = Arrays.stream(string.split("\n"))
                        .map(line -> line.replace("\r", ""))
                        .collect(Collectors.toList());
                take.context.lines.stream()
                        .filter(line -> !line.startsWith("#"))
                        .forEach(line -> {
                            if (line.endsWith(".m3u8"))
                                queue.add(take.context.sub(line).httpUrl);
                            else if (M3u8.downTs) {
                                queue.add(take.sub(line));
                                take.context.add();
                            }
                        });
                //#EXT-X-KEY:METHOD=AES-128,URI="key.key"
                take.context.lines.stream()
                        .filter(line -> line.startsWith("#EXT-X-KEY"))
                        .flatMap(line -> Stream.of(line.split(",")))
                        .filter(line -> line.startsWith("URI"))
                        .map(line -> line.split("=")[1].replaceAll("\"", ""))
                        .forEach(line -> {
                            queue.add(take.sub(line));
                            take.context.add();
                        });
                // #EXTINF:5.8,
                take.context.time = take.context.lines.stream()
                        .filter(line -> line.startsWith("#EXTINF"))
                        .map(line -> new BigDecimal(line.substring(8, line.length() - 1)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (!BigDecimal.ZERO.equals(take.context.time))
                    System.out.println(take.context.time + " " + take.context);
                take.context.subSuccess();
            }, true, e -> queue.add(take));
        }, "down tasks")
                .start();
    }

    private static void show() {
        ThreadUtil.createLoopThread(() -> {
            Thread.sleep(10000L);
            int show = 0;
            int live = 0;
            for (M3u8 task : M3u8.tasks) {
                int change = task.change();
                if (change != 0) {
                    live++;
                    System.out.println(change + "  " + task);
                }
                show += change;
            }
            int size = M3u8.tasks.size();
            System.out.println("m3u8:" + size + " 活动" + live + " 完成任务:" + show);
            if (size == 0) {
                System.exit(0);
            }
        }, "show tasks")
                .start();
    }
}
