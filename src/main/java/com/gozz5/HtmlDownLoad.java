package com.gozz5;

import http.HttpUrl;

import java.util.concurrent.LinkedBlockingQueue;

public class HtmlDownLoad {
    public static final LinkedBlockingQueue<HttpUrl<?>> queue = new LinkedBlockingQueue<>();
    public static final LinkedBlockingQueue<HttpUrl<?>> success = new LinkedBlockingQueue<>();

    static {
        for (int i = 1; i < 35200; i++) {
            queue.add(new HttpUrl<>("https://gozz5.com/video/show/id/" + i, null));
        }
    }

    public static void main(String[] args) {
        ResourceDownLoad.down(queue, success, 10000);
    }
}
