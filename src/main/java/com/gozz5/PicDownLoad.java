package com.gozz5;

import fun.qianrui.staticUtil.FileUtil;
import http.HttpUrl;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class PicDownLoad {
    private static final LinkedBlockingQueue<HttpUrl<?>> queue = new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<HttpUrl<?>> success = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        List<HtmlTran.Trans> list = HtmlTran.tran();
        for (HtmlTran.Trans trans : list) {
            if (trans.getPic()
                    .size() == 0) continue;
            for (String s : trans.getPic()) {
                HttpUrl<?> tran = new HttpUrl<>(
                        s,
                        Paths.get("F:\\Downloads\\m3u8\\gozz5.com\\video\\"
                                + FileUtil.goodPath(trans.getTitle()) + ".jpg"));
                queue.add(tran);
            }
            queue.add(new HttpUrl<>(trans.getUrl()));
        }

        ResourceDownLoad.down(queue, success, 1);

    }
}
