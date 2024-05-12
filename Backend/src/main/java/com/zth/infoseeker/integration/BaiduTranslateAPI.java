/*
author: 请问
2024/1/19  14:10

*/
package com.zth.infoseeker.integration;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class BaiduTranslateAPI {

    private static final String API_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    private final String appId;
    private final String securityKey;

    public BaiduTranslateAPI(@Value("${baidu.api.id}") String appId,
                             @Value("${baidu.security.key}") String securityKey) {
        this.appId = appId;
        this.securityKey = securityKey;
    }

    public String translate(String query, String fromLang, String toLang) throws Exception {
        if (Objects.equals(query, "") || query == null){
            return "";
        }
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = md5(appId + query + salt + securityKey);

        String params = "q=" + encode(query) + "&from=" + fromLang + "&to=" + toLang
                + "&appid=" + appId + "&salt=" + salt + "&sign=" + sign;

        // 获取响应JSON字符串
        String jsonResponse = sendPostRequest(API_URL, params);

        // 解析JSON响应，并返回翻译后的文本
        return parseJson(jsonResponse);
    }

    private String sendPostRequest(String requestUrl, String payload) throws Exception {
        HttpURLConnection connection = null;
        OutputStream os = null;
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(payload.getBytes("UTF-8").length));

            os = connection.getOutputStream();
            os.write(payload.getBytes("UTF-8"));
            os.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            } else {
                throw new Exception("HTTP error code: " + connection.getResponseCode());
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (os != null) {
                os.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }


    private String parseJson(String json) {
    JSONObject jsonObject = new JSONObject(json);
    //System.out.println(jsonObject);
    JSONArray transResult = jsonObject.getJSONArray("trans_result");
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < transResult.length(); i++) {
        // 使用getString方法得到翻译后的文本，不会返回Unicode编码
        result.append(transResult.getJSONObject(i).getString("dst"));
    }
    return result.toString();
}

    private String encode(String value) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(value, "UTF-8");
    }

    private String md5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String hex = Integer.toHexString(0xff & aMessageDigest);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

