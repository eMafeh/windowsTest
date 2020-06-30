package util;

import fun.qianrui.staticUtil.ExceptionUtil;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

import java.nio.charset.CodingErrorAction;
import java.util.function.Consumer;

public class HttpUtil {
    private static int socketTimeout = 10000;// 设置等待数据超时时间0.5秒钟 根据业务调整

    private static int connectTimeout = 5000;// 连接超时

    private static int poolSize = 500;// 连接池最大连接数

    private static int maxPerRoute = 30;// 每个主机的并发最多只有1500

    private static int connectionRequestTimeout = 30; //从连接池中后去连接的timeout时间

    // http代理相关参数
    private static String host = "";
    private static int port = 0;
    private static String username = "";
    private static String password = "";

    // 异步httpclient
    public static final CloseableHttpAsyncClient asyncHttpClient = createAsyncClient(false);

    // 异步加代理的httpclient
//    public static final CloseableHttpAsyncClient proxyAsyncHttpClient = createAsyncClient(true);

    private static CloseableHttpAsyncClient createAsyncClient(boolean proxy) {

//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
//                username, password);

//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(
                // 设置连接池大小
                ExceptionUtil.throwT(() -> new DefaultConnectingIOReactor(
                        // 配置io线程
                        IOReactorConfig.custom()
                                .setSoKeepAlive(false)
                                .setTcpNoDelay(true)
                                .setIoThreadCount(Runtime.getRuntime()
                                        .availableProcessors())
                                .build())),
                null,
                // 设置协议http和https对应的处理socket链接工厂的对象
                RegistryBuilder.<SchemeIOSessionStrategy>create()
                        .register("http", NoopIOSessionStrategy.INSTANCE)
                        .register("https", new SSLIOSessionStrategy(SSLContexts.createDefault()))
                        .build(),
                null);

        if (poolSize > 0) {
            conMgr.setMaxTotal(poolSize);
        }

        if (maxPerRoute > 0) {
            conMgr.setDefaultMaxPerRoute(maxPerRoute);
        } else {
            conMgr.setDefaultMaxPerRoute(10);
        }

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();

        Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder
                .<AuthSchemeProvider>create()
                .register(AuthSchemes.BASIC, new BasicSchemeFactory())
                .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
                .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
                .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
                .build();
        conMgr.setDefaultConnectionConfig(connectionConfig);

        HttpAsyncClientBuilder builder = HttpAsyncClients.custom()
                .setConnectionManager(conMgr)
//                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(connectionRequestTimeout)
                        .setConnectTimeout(connectTimeout)
                        .setSocketTimeout(socketTimeout)
                        .build());
        if (proxy) {
            builder.setProxy(new HttpHost(host, port));
        }
        CloseableHttpAsyncClient client = builder.build();
        client.start();
        return client;
    }

    public static void execute(HttpUriRequest request, Consumer<HttpResponse> success, Consumer<Exception> fail) {
//        request.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//        request.addHeader("accept-language", "zh-CN,zh;q=0.9");
//        request.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Safari/537.36");
//        request.addHeader("accept-encoding","gzip, deflate, br");
//        request.addHeader("upgrade-insecure-requests","1");
//        :authority: video2.ddbtss.com:8091
//:method: GET
//        :path: /99920180131/9992018013100165/650kb/hls/index.m3u8
//:scheme: https
//sec-fetch-dest: document
//sec-fetch-mode: navigate
//sec-fetch-site: none


        //连接池执行
        HttpUtil.asyncHttpClient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                try {
                    success.accept(result);
                } catch (Exception e) {
                    System.err.println("execute completed exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Exception e) {
                try {
                    fail.accept(e);
                } catch (Exception e1) {
                    System.err.println("execute failed exception");
                    e1.printStackTrace();
                }
            }

            @Override
            public void cancelled() {
                System.err.println("execute cancelled");
            }
        });
    }
}