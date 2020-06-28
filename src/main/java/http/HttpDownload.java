package http;

import fun.qianrui.staticUtil.ExceptionUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import util.HttpUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

public class HttpDownload {
    public static void downLoad(HttpUrl<?> url, Consumer<byte[]> consumer, boolean needResult, Consumer<Exception> fail) {
        Path path = url.local;
        File file = path.toFile();
        if (file.exists()) {
//            System.out.println("本地已有 " + url.url);
            consumer.accept(needResult ? ExceptionUtil.throwT(() -> Files.readAllBytes(path)) : null);
        } else {
//            System.out.println("下载 " + url.url);
            HttpUtil.execute(new HttpGet(url.url),
                    result -> {
                        System.out.println(result.getStatusLine() + " " + url.url);
                        try {
                            Files.createDirectories(path.getParent());
                            byte[] bytes = EntityUtils.toByteArray(result.getEntity());
                            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                            consumer.accept(bytes);
                        } catch (Exception e) {
                            fail(e, url, fail);
                        }
                    }, e -> fail(e, url, fail));
        }
    }

    private static void fail(Exception e, HttpUrl<?> url, Consumer<Exception> fail) {
//        System.out.println("失败 " + url.url);
        fail.accept(e);
    }
}