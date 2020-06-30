package com.gozz5;

import fun.qianrui.staticUtil.ExceptionUtil;
import fun.qianrui.staticUtil.ThreadUtil;
import http.HttpDownload;
import http.HttpUrl;

import java.util.concurrent.LinkedBlockingQueue;

public class ResourceDownLoad {
    public static void down(LinkedBlockingQueue<HttpUrl<?>> queue, LinkedBlockingQueue<HttpUrl<?>> success,int min) {
        int size = queue.size();
        ThreadUtil.createLoopThread(() -> {
            HttpUrl<?> take = queue.take();
            HttpDownload.downLoad(take, bytes -> {
                if (bytes != null) ExceptionUtil.isTrue(bytes.length > min);
                success.add(take);
            }, false, e -> queue.add(take));
        }, "downHtml")
                .start();
        ThreadUtil.createLoopThread(() -> {
            Thread.sleep(10000);
            System.out.println(success.size());
            if (success.size() == size) System.exit(0);
        }, "showQueue")
                .start();
    }
}
