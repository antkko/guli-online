package com.macro.eduorder.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2024/1/10 19:43
 * @github https://github.com/bugstackss
 */
public class HttpClient {

    private String url;

    private Map<String, String> param;

    @Getter
    private int statusCode;

    private String content;

    @Getter
    @Setter
    private String xmlParam;
    
    private boolean isHttps;

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(final boolean isHttps) {
        this.isHttps = isHttps;
    }

    public HttpClient(final String url, final Map<String, String> param) {
        this.url = url;
        this.param = param;
    }

    public HttpClient(final String url) {
        this.url = url;
    }

    public void setParameter(final Map<String, String> map) {
        param = map;
    }

    public void addParameter(final String key, final String value) {
        if (param == null) {
            param = new HashMap<String, String>();
        }
        param.put(key, value);
    }

    public void post() throws ClientProtocolException, IOException {
        final HttpPost http = new HttpPost(url);
        setEntity(http);
        execute(http);
    }

    public void put() throws ClientProtocolException, IOException {
        final HttpPut http = new HttpPut(url);
        setEntity(http);
        execute(http);
    }

    public void get() throws ClientProtocolException, IOException {
        if (param != null) {
            final StringBuilder url = new StringBuilder(this.url);
            final boolean isFirst = true;
            for (final String key : param.keySet()) {
                if (isFirst) {
                    url.append("?");
                } else {
                    url.append("&");
                }
                url.append(key).append("=").append(param.get(key));
            }
            this.url = url.toString();
        }
        final HttpGet http = new HttpGet(url);
        execute(http);
    }

    /**
     * set http post,put param
     */
    private void setEntity(final HttpEntityEnclosingRequestBase http) {
        if (param != null) {
            final List<NameValuePair> nvps = new LinkedList<NameValuePair>();
            for (final String key : param.keySet()) {
                nvps.add(new BasicNameValuePair(key, param.get(key))); // 参数
            }
            http.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8)); // 设置参数
        }
        if (xmlParam != null) {
            http.setEntity(new StringEntity(xmlParam, Consts.UTF_8));
        }
    }

    private void execute(final HttpUriRequest http) throws ClientProtocolException,
            IOException {
        CloseableHttpClient httpClient = null;
        try {
            if (isHttps) {
                final SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustStrategy() {
                            // 信任所有
                            @Override
                            public boolean isTrusted(final X509Certificate[] chain,
                                                     final String authType)
                                    throws CertificateException {
                                return true;
                            }
                        }).build();
                final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                        sslContext);
                httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                        .build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            try (final CloseableHttpResponse response = httpClient.execute(http)) {
                if (response != null) {
                    if (response.getStatusLine() != null) {
                        statusCode = response.getStatusLine().getStatusCode();
                    }
                    final HttpEntity entity = response.getEntity();
                    // 响应内容
                    content = EntityUtils.toString(entity, Consts.UTF_8);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            assert httpClient != null;
            httpClient.close();
        }
    }

    public String getContent() throws ParseException, IOException {
        return content;
    }

}