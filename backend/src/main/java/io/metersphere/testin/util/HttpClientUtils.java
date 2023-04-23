package io.metersphere.testin.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    public static final String CHARSET = "UTF-8";

    public static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null, CHARSET);
    }

    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        return doGet(url, params, headers, CHARSET);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null, CHARSET);
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        return doPost(url, params, headers, CHARSET);
    }

    public static String doJsonPost(String url, String body) {
        return doJsonPost(url, body, null);
    }

    public static String doJsonPost(String url, String body, Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            headers = Collections.singletonMap("Content-Type", "application/json");
        } else {
            if (!headers.containsKey("Content-Type")) {
                headers.put("Content-Type", "application/json");
            }
        }
        return doPost(url, body, headers, CHARSET);
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param headers 请求的header
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<>(params.size());
                fillParams(params, pairs);
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            return doHttpRequest(httpGet, headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void fillParams(Map<String, String> params, List<NameValuePair> pairs) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value != null) {
                pairs.add(new BasicNameValuePair(entry.getKey(), value));
            }
        }
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param headers
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpEntity requestEntity = null;
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<>(params.size());
                fillParams(params, pairs);
            }
            if (pairs != null && pairs.size() > 0) {
                requestEntity = new UrlEncodedFormEntity(pairs, CHARSET);
            }
            return doPost(url, requestEntity, headers, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param body    通过body传参
     * @param headers
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, String body, Map<String, String> headers, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpEntity requestEntity = null;
            if (body != null) {
                requestEntity = new StringEntity(body, CHARSET);
            }
            return doPost(url, requestEntity, headers, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doPost(String url, HttpEntity requestEntity, Map<String, String> headers, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            if (requestEntity != null) {
                httpPost.setEntity(requestEntity);
            }
            return doHttpRequest(httpPost, headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String doHttpRequest(HttpRequestBase httpRequestBase, Map<String, String> headers) throws IOException {
        setHeaders(httpRequestBase, headers);
        CloseableHttpClient httpClient = null;

        try {
            httpClient = getHttpClient();
            CloseableHttpResponse response = null;

            try {
                response = httpClient.execute(httpRequestBase);

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    httpRequestBase.abort();
                    throw new RuntimeException("HttpClient,error status code :" + statusCode);
                }

                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, "utf-8");
                }
                EntityUtils.consume(entity);

                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    private static void setHeaders(HttpRequestBase req, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (String name : headers.keySet()) {
                req.setHeader(name, headers.get(name));
            }
        }
    }

    public static byte[] getUrlBytes(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);

            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static String doBinaryPost(String url, byte[] data) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new ByteArrayEntity(data));
            return doHttpRequest(httpPost, Collections.singletonMap("Content-Type", "application/json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}