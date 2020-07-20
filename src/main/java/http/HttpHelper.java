package http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import util.HttpUtil;

import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class HttpHelper {

    public static <C> void notLocal(HttpUrl<C> url, Consumer<byte[]> success, Fail<C> fail, int max404) {
        if (url.fail > max404) fail.fail(null, url, 404);
        else HttpUtil.execute(new HttpGet(url.url), result -> {
            try {
                int code = result.getStatusLine()
                        .getStatusCode();
                if (code != 200) {
                    url.fail++;
                    fail.fail(null, url, code);
                    return;
                }
                byte[] bytes = EntityUtils.toByteArray(result.getEntity());
                success.accept(bytes);
            } catch (Exception e) {
                url.fail++;
                fail.fail(e, url, -1);
            }
        }, e -> {
            if (!(e instanceof TimeoutException)) url.fail++;
            fail.fail(e, url, -1);
        });
    }

    public interface Fail<C> {
        void fail(Exception e, HttpUrl<C> url, int code);
    }
}
