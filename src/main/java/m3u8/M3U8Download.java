package m3u8;

import fun.qianrui.staticUtil.ExceptionUtil;
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

        queue.add(new M3u8("学姐偷情", "学姐偷情_第一季02.480p完整版", "https://videozm.whqhyg.com:8091/20200524/qEP6CFse/index.m3u8").httpUrl);
        queue.add(new M3u8("学姐偷情", "学姐偷情_第二季03.720p完整版", "https://videozm.whqhyg.com:8091/20200524/cIDKnbxx/index.m3u8").httpUrl);
        queue.add(new M3u8("学姐偷情", "学姐偷情_第二季02.480p完整版", "https://videozm.whqhyg.com:8091/20200524/5iZVbrUb/index.m3u8").httpUrl);
        queue.add(new M3u8("学姐偷情", "学姐偷情_第一季03.480p完整版", "https://videozm.whqhyg.com:8091/20200524/5YOiXFu4/index.m3u8").httpUrl);

        queue.add(new M3u8("短视频", "胸大腰细的清纯白嫩大学美女", "https://videozm.whqhyg.com:8091/20200524/eQwla3qK/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "华裔眼镜骚妹大奶无毛逼", "https://videozm.whqhyg.com:8091/20200524/QcH4TycZ/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "火车露点自慰不怕旁边旅客醒了被强操吗", "https://videozm.whqhyg.com:8091/20200524/KJip0OKd/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "火辣的嫩妹自拍抵押视频", "https://videozm.whqhyg.com:8091/20200524/ECpbwlBw/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "性感美女露脸自慰", "https://videozm.whqhyg.com:8091/20200524/xnHnNeeS/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "高颜值爆乳健身教练约炮健身房学生啪啪", "https://videozm.whqhyg.com:8091/20200524/hWf1n093/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "女神级清纯性感师院美女酒店啪啪", "https://videozm.whqhyg.com:8091/20200524/eI7E3fkA/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "美留学生思静和大屌炮友们啪啪", "https://videozm.whqhyg.com:8091/20200524/Hva4pG5D/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "韵味级别的极品阿姨享受无套中出", "https://videozm.whqhyg.com:8091/20200524/IgfU2Fqi/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "大三情侣翘课宾馆啪啪纪实", "https://videozmcdn.stz8.com:8091/20200508/uyooEPAi/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "极品美女和男友性爱私拍流出", "https://videozmcdn.stz8.com:8091/20200508/vCG3Zgkg/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "酒店约炮牛仔裤苗条学生妹", "https://videozmcdn.stz8.com:8091/20200508/DY5V9N67/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "奔驰哥吃了药啪啪小嫩妹", "https://videozmcdn.stz8.com:8091/20200508/OgsQTek6/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "极品魔鬼身材女神 口交后入啪啪", "https://videozmcdn.stz8.com:8091/20200508/ae42Xrq1/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "极品湖南学院派美女和男朋友", "https://videozmcdn.stz8.com:8091/20200508/DZ8o7djA/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "兄妹乱伦2淫荡骚货妹妹诱惑看片哥哥", "https://videozmcdn.stz8.com:8091/20200508/xNmdVNuY/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "风韵表姐相亲失败寂寞已久魔爪伸向处男弟弟", "https://videozmcdn.stz8.com:8091/20200508/0XtAG9n4/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "情装睡的外甥女每天都勾引我乱伦", "https://videozmcdn.stz8.com:8091/20200508/jj6JWfLj/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "新搬来的白领女邻居套路我出轨", "https://videozmcdn.stz8.com:8091/20200508/m82Y2sMk/index.m3u8").httpUrl);
        queue.add(new M3u8("短视频", "婚内出轨出差约炮女同学老婆查岗遇危机", "https://videozmcdn.stz8.com:8091/20200508/iirCuOf4/index.m3u8").httpUrl);

        download();
    }

    private static void download() throws InterruptedException {
        if (queue.size() == 0) return;
        while (true) {
            HttpUrl<M3u8> take = queue.take();
            //m3u8文件
            if (take.context.httpUrl != take) {
                HttpDownload.downLoad(take, bytes -> take.context.subSuccess(), false, e -> queue.add(take));
                continue;
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
        }
    }
}
