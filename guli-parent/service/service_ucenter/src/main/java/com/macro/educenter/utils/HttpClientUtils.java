package com.macro.educenter.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author macro
 * @description
 * @date 2024/1/2 19:35
 * @github https://github.com/bugstackss
 */
public class HttpClientUtils {

    public static final int CONN_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 10000;
    public static final String CHARSET = "UTF-8";
    private static final HttpClient client;

    static {
        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String postParameters(final String url, final String parameterStr) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        return post(url, parameterStr, "application/x-www-form-urlencoded", CHARSET, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String postParameters(final String url, final String parameterStr, final String CHARSET, final Integer CONN_TIMEOUT, final Integer READ_TIMEOUT) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        return post(url, parameterStr, "application/x-www-form-urlencoded", CHARSET, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String postParameters(final String url, final Map<String, String> params) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {
        return postForm(url, params, null, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String postParameters(final String url, final Map<String, String> params, final Integer CONN_TIMEOUT, final Integer READ_TIMEOUT) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {
        return postForm(url, params, null, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String get(final String url) throws Exception {
        return get(url, CHARSET, null, null);
    }

    public static String get(final String url, final String CHARSET) throws Exception {
        return get(url, CHARSET, CONN_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 发送一个 Post 请求, 使用指定的字符集编码.
     *
     * @param url          请求地址
     * @param body         RequestBody
     * @param mimeType     例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
     * @param CHARSET      编码
     * @param CONN_TIMEOUT 建立链接超时时间,毫秒.
     * @param READ_TIMEOUT 响应超时时间,毫秒.
     * @return ResponseBody, 使用指定的字符集编码.
     * @throws ConnectTimeoutException 建立链接超时异常
     * @throws SocketTimeoutException  响应超时
     * @throws Exception               异常
     */
    public static String post(final String url, final String body, final String mimeType, final String CHARSET, final Integer CONN_TIMEOUT, final Integer READ_TIMEOUT)
            throws ConnectTimeoutException, SocketTimeoutException, Exception {
        HttpClient client = null;
        final HttpPost post = new HttpPost(url);
        String result = "";
        try {
            if (StringUtils.isNotBlank(body)) {
                final HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, CHARSET));
                post.setEntity(entity);
            }
            // 设置参数
            final RequestConfig.Builder customReqConf = RequestConfig.custom();
            if (CONN_TIMEOUT != null) {
                customReqConf.setConnectTimeout(CONN_TIMEOUT);
            }
            if (READ_TIMEOUT != null) {
                customReqConf.setSocketTimeout(READ_TIMEOUT);
            }
            post.setConfig(customReqConf.build());

            final HttpResponse res;
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(post);
            }
            result = IOUtils.toString(res.getEntity().getContent(), CHARSET);
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }


    /**
     * 提交form表单
     *
     * @param url          请求地址
     * @param params       请求参数
     * @param CONN_TIMEOUT 连接超时时间
     * @param READ_TIMEOUT 读取超时时间
     * @return 响应结果
     * @throws ConnectTimeoutException 连接超时
     * @throws SocketTimeoutException  读取超时
     * @throws Exception               异常
     */
    public static String postForm(final String url, final Map<String, String> params, final Map<String, String> headers, final Integer CONN_TIMEOUT, final Integer READ_TIMEOUT) throws ConnectTimeoutException,
            SocketTimeoutException, Exception {

        HttpClient client = null;
        final HttpPost post = new HttpPost(url);
        try {
            if (params != null && !params.isEmpty()) {
                final List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                final Set<Entry<String, String>> entrySet = params.entrySet();
                for (final Entry<String, String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }

            if (headers != null && !headers.isEmpty()) {
                for (final Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // 设置参数
            final Builder customReqConf = RequestConfig.custom();
            if (CONN_TIMEOUT != null) {
                customReqConf.setConnectTimeout(CONN_TIMEOUT);
            }
            if (READ_TIMEOUT != null) {
                customReqConf.setSocketTimeout(READ_TIMEOUT);
            }
            post.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(post);
            }
            return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null
                    && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }


    /**
     * 发送一个 GET 请求
     *
     * @param url
     * @param CHARSET
     * @param CONN_TIMEOUT 建立链接超时时间,毫秒.
     * @param READ_TIMEOUT 响应超时时间,毫秒.
     * @return
     * @throws ConnectTimeoutException 建立链接超时
     * @throws SocketTimeoutException  响应超时
     * @throws Exception
     */
    public static String get(final String url, final String CHARSET, final Integer CONN_TIMEOUT, final Integer READ_TIMEOUT)
            throws ConnectTimeoutException, SocketTimeoutException, Exception {

        HttpClient client = null;
        final HttpGet get = new HttpGet(url);
        String result = "";
        try {
            // 设置参数
            final Builder customReqConf = RequestConfig.custom();
            if (CONN_TIMEOUT != null) {
                customReqConf.setConnectTimeout(CONN_TIMEOUT);
            }
            if (READ_TIMEOUT != null) {
                customReqConf.setSocketTimeout(READ_TIMEOUT);
            }
            get.setConfig(customReqConf.build());

            HttpResponse res = null;

            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(get);
            }

            result = IOUtils.toString(res.getEntity().getContent(), CHARSET);
        } finally {
            get.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }


    /**
     * 从 response 里获取 CHARSET
     *
     * @param ressponse
     * @return
     */
    @SuppressWarnings("unused")
    private static String getCHARSETFromResponse(final HttpResponse ressponse) {
        // Content-Type:text/html; CHARSET=GBK
        if (ressponse.getEntity() != null && ressponse.getEntity().getContentType() != null && ressponse.getEntity().getContentType().getValue() != null) {
            final String contentType = ressponse.getEntity().getContentType().getValue();
            if (contentType.contains("CHARSET=")) {
                return contentType.substring(contentType.indexOf("CHARSET=") + 8);
            }
        }
        return null;
    }


    /**
     * 创建 SSL连接
     *
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            final SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    return true;
                }
            }).build();

            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(final String arg0, final SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(final String host, final SSLSocket ssl)
                        throws IOException {
                }

                @Override
                public void verify(final String host, final X509Certificate cert)
                        throws SSLException {
                }

                @Override
                public void verify(final String host, final String[] cns,
                                   final String[] subjectAlts) throws SSLException {
                }

            });

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (final GeneralSecurityException e) {
            throw e;
        }
    }

    public static void main(final String[] args) {
        try {
            final String str = post("https://localhost:443/ssl/test.shtml", "name=12&page=34", "application/x-www-form-urlencoded", "UTF-8", 10000, 10000);
            // String str= get("https://localhost:443/ssl/test.shtml?name=12&page=34","GBK");
            /*Map<String,String> map = new HashMap<String,String>();
            map.put("name", "111");
            map.put("page", "222");
            String str= postForm("https://localhost:443/ssl/test.shtml",map,null, 10000, 10000);*/
            System.out.println(str);
        } catch (final ConnectTimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final SocketTimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
