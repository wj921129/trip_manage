package com.jeesite.modules.commom.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

public class ApiUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ApiUtils.class);

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    private static final RequestConfig CONFIG = RequestConfig.custom().setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000).build();

    private static final List<Header> headerList = Lists.newArrayList();

    static {
        headerList.add(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        headerList.add(new BasicHeader("custId", "8kl87wgttq"));
        headerList.add(new BasicHeader("token", "JCCWmIr91490341211445"));
        headerList.add(new BasicHeader("sign", ""));
        headerList.add(new BasicHeader("language", ""));
        headerList.add(new BasicHeader("tenantId", "borgrdm8mv7f"));
        headerList.add(new BasicHeader("userId", "1"));
    }

    /**
     * 解析结果对象
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> ApiResult<T> parse(String result, Class<T> clazz) {
        ApiResult<T> apiResult = new ApiResult<T>();
        try {
            if (!Strings.isNullOrEmpty(result)) {
                JSONObject resultObject = JSON.parseObject(result);
                if (null != resultObject) {
                    apiResult.setCode(resultObject.getString("code"));
                    apiResult.setMsg(resultObject.getString("msg"));
                    apiResult.setValidateInfo(resultObject.getJSONObject("validateInfo"));
                    apiResult.setData(resultObject.getObject("data", clazz));
                }
            }
        } catch (Exception e) {
            LOG.error("解析数据异常", e);
        }
        return apiResult;
    }

    /**
     * 解析结果集对象
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> ApiResult<List<T>> parseList(String result, Class<T> clazz) {
        ApiResult<List<T>> apiResult = new ApiResult<List<T>>();
        try {
            if (!Strings.isNullOrEmpty(result)) {
                JSONObject resultObject = JSON.parseObject(result);
                if (null != resultObject) {
                    apiResult.setCode(resultObject.getString("code"));
                    apiResult.setMsg(resultObject.getString("msg"));
                    apiResult.setValidateInfo(resultObject.getJSONObject("validateInfo"));
                    List<T> apiList = Lists.newArrayList();
                    JSONArray jsonArray = resultObject.getJSONArray("data");
                    if (null != jsonArray && !jsonArray.isEmpty()) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject entity = jsonArray.getJSONObject(i);
                            apiList.add(JSON.parseObject(entity.toJSONString(), clazz));
                        }
                    }
                    apiResult.setData(apiList);
                }
            }
        } catch (Exception e) {
            LOG.error("解析数据异常", e);
        }
        return apiResult;
    }

    /**
     * 解析分页结果对象
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> ApiResult<ApiPage<T>> parsePage(String result, Class<T> clazz) {
        ApiResult<ApiPage<T>> apiResult = new ApiResult<ApiPage<T>>();
        try {
            if (!Strings.isNullOrEmpty(result)) {
                JSONObject resultObject = JSON.parseObject(result);
                if (null != resultObject) {
                    apiResult.setCode(resultObject.getString("code"));
                    apiResult.setMsg(resultObject.getString("msg"));
                    apiResult.setValidateInfo(resultObject.getJSONObject("validateInfo"));
                    JSONObject pageObject = resultObject.getJSONObject("data");
                    ApiPage<T> apiPage = new ApiPage<T>();
                    if (null != pageObject) {
                        if (pageObject.containsKey("currentPage"))
                            apiPage.setCurrentPage(pageObject.getIntValue("currentPage"));
                        if (pageObject.containsKey("pageSize"))
                            apiPage.setPageSize(pageObject.getIntValue("pageSize"));
                        if (pageObject.containsKey("count"))
                            apiPage.setCount(pageObject.getIntValue("count"));
                        if (pageObject.containsKey("entities")) {
                            List<T> entities = Lists.newArrayList();
                            JSONArray jsonArray = pageObject.getJSONArray("entities");
                            if (null != jsonArray && !jsonArray.isEmpty()) {
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject entity = jsonArray.getJSONObject(i);
                                    entities.add(JSON.parseObject(entity.toJSONString(), clazz));
                                }
                            }
                            apiPage.setEntities(entities);
                        }
                    }
                    apiResult.setData(apiPage);
                }
            }
        } catch (Exception e) {
            LOG.error("解析数据异常", e);
        }
        return apiResult;
    }

    public static void buildHeaders(List<Header> headers) {
        if (CollectionUtils.isNotEmpty(headers)) {
            headerList.clear();
            headerList.addAll(headers);
        }
    }

    /**
     * GET请求URL数据
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            httpGet.setConfig(CONFIG);
            httpGet.setHeaders(headerList.toArray(new Header[0]));
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity == null)
                return null;
            BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(), "utf-8")));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            response.close();
            return buffer.toString();
        } catch (Exception e) {
            LOG.error("调用API接口异常：" + url, e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                LOG.error("关闭流异常：" + url, e);
            }
        }
        return null;
    }

    /**
     * 组装Body
     *
     * @param data
     * @return
     */
    private static String createJson(Object data) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        return gson.toJson(data);
    }

    /**
     * POST请求URL
     *
     * @param url
     * @param data
     * @return
     */
    public static String post(String url, Object data) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));
            httpPost.setConfig(CONFIG);
            httpPost.setHeaders(headerList.toArray(new Header[0]));
            if (null != data) {
                String json = createJson(data);
                StringEntity se = new StringEntity(json);
                se.setContentType(CONTENT_TYPE_TEXT_JSON);
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
                httpPost.setEntity(new StringEntity(json, "UTF-8"));
            }
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity == null)
                return null;
            BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(), "utf-8")));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            response.close();
            return buffer.toString();
        } catch (Exception e) {
            LOG.error("调用API接口异常：" + url, e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                LOG.error("关闭流异常：" + url, e);
            }
        }
        return null;
    }

    /**
     * PUT请求URL
     *
     * @param url
     * @param data
     * @return
     */
    public static String put(String url, Object data) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPut httpPut = new HttpPut();
            httpPut.setURI(new URI(url));
            httpPut.setConfig(CONFIG);
            httpPut.setHeaders(headerList.toArray(new Header[0]));
            if (null != data) {
                String json = createJson(data);
                StringEntity se = new StringEntity(json);
                se.setContentType(CONTENT_TYPE_TEXT_JSON);
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
                httpPut.setEntity(new StringEntity(json, "UTF-8"));
            }

            CloseableHttpResponse response = client.execute(httpPut);
            HttpEntity entity = response.getEntity();
            if (entity == null)
                return null;
            BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(), "utf-8")));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            response.close();
            return buffer.toString();
        } catch (Exception e) {
            LOG.error("调用API接口异常：" + url, e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                LOG.error("关闭流异常：" + url, e);
            }
        }
        return null;
    }

    /**
     * DELETE请求URL
     *
     * @param url
     * @return
     */
    public static String delete(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpDelete httpDelete = new HttpDelete();
            httpDelete.setURI(new URI(url));
            httpDelete.setConfig(CONFIG);
            httpDelete.setHeaders(headerList.toArray(new Header[0]));
            CloseableHttpResponse response = client.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            if (entity == null)
                return null;
            BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(), "utf-8")));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            response.close();
            return buffer.toString();
        } catch (Exception e) {
            LOG.error("调用API接口异常：" + url, e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                LOG.error("关闭流异常：" + url, e);
            }
        }
        return null;
    }

    private static String escapeParam(String param) {
        return param.replaceAll("%", "%25")
                .replaceAll("\\+", "%2B")
                .replaceAll(" ", "%20")
                .replaceAll("/", "%2F")
                .replaceAll("\\?", "%3F")
                .replaceAll("#", "%23")
                .replaceAll("&", "%26")
                .replaceAll("=", "%3D");
    }

    /**
     * 组装GET参数
     *
     * @param url
     * @param queryParam
     * @return
     */
    private static String buildGetUrl(String url, Object queryParam) {
        StringBuffer stringBuffer = new StringBuffer(url);
        try {
            if (null != queryParam) {
                if (!url.contains("?")) {
                    stringBuffer.append("?1=1");
                }
                JSONObject params = JSON.parseObject(JSON.toJSONString(queryParam));
                params.forEach((s, o) -> {
                    if (null != o) {
                        if (o instanceof String) {
                            if (!Strings.isNullOrEmpty((String) o)) {
                                stringBuffer.append("&").append(s).append("=").append(escapeParam((String) o));
                            }
                        } else {
                            stringBuffer.append("&").append(s).append("=").append(o);
                        }
                    }
                });
            }
        } catch (Exception e) {
            LOG.error("组装GET参数异常", e);
        }
        return stringBuffer.toString();
    }

    /**
     * GET请求
     *
     * @param url
     * @param queryParam
     * @return
     */
    public static String get(String url, Object queryParam) {
        return get(buildGetUrl(url, queryParam));
    }

    /**
     * GET请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<List<T>> get(String url, Class<T> clazz) {
        return get(url, null, clazz);
    }

    /**
     * GET请求
     *
     * @param url
     * @param queryParam
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<List<T>> get(String url, Object queryParam, Class<T> clazz) {
        return parseList(get(buildGetUrl(url, queryParam)), clazz);
    }

    /**
     * GET请求(单条查询)
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> getSingle(String url, Class<T> clazz) {
        return getSingle(url, null, clazz);
    }

    /**
     * GET请求(单条查询)
     *
     * @param url
     * @param queryParam
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> getSingle(String url, Object queryParam, Class<T> clazz) {
        return parse(get(buildGetUrl(url, queryParam)), clazz);
    }

    /**
     * GET分页请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<ApiPage<T>> getPage(String url, Class<T> clazz) {
        return getPage(url, null, clazz);
    }

    /**
     * GET分页请求
     *
     * @param url
     * @param queryParam
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<ApiPage<T>> getPage(String url, Object queryParam, Class<T> clazz) {
        return parsePage(get(buildGetUrl(url, queryParam)), clazz);
    }

    /**
     * POST请求
     *
     * @param url
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> post(String url, Object object, Class<T> clazz) {
        return parse(post(url, object), clazz);
    }

    /**
     * PUT请求
     *
     * @param url
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> put(String url, Object object, Class<T> clazz) {
        return parse(put(url, object), clazz);
    }

    /**
     * DELETE请求
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> delete(String url, Class<T> clazz) {
        return parse(delete(url), clazz);
    }

}
