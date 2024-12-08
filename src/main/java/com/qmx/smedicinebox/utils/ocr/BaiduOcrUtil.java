package com.qmx.smedicinebox.utils.ocr;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qmx.smedicinebox.config.GetToken;
import com.qmx.smedicinebox.utils.Base64Util;
import com.qmx.smedicinebox.utils.FileUtil;
import com.qmx.smedicinebox.utils.HttpUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 百度OCR工具类
 * @author qmx
 */
public class BaiduOcrUtil implements OcrUtil{

    @Override
    public String generalBasic(String filePath){
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        try {
            // 本地文件路径z`
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> generalWordList(String json) {
        List<String> wordsList = new ArrayList<>();

        if (json == null || json.trim().isEmpty()) {
            System.out.println("Input JSON string is null or empty.");
            return wordsList;
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(json);

            // 检查 "words_result" 是否存在且是一个数组
            if (jsonObject.containsKey("words_result") && jsonObject.get("words_result") instanceof JSONArray) {
                JSONArray wordsResultArray = jsonObject.getJSONArray("words_result");

                for (int i = 0; i < wordsResultArray.size(); i++) {
                    JSONObject resultObject = wordsResultArray.getJSONObject(i);

                    // 检查 "words" 是否存在且是一个字符串
                    if (resultObject.containsKey("words") && resultObject.get("words") instanceof String) {
                        String word = resultObject.getStr("words");
                        wordsList.add(word);
                    } else {
                        System.out.println("Invalid 'words' field in result object at index " + i);
                    }
                }
            } else {
                System.out.println("Invalid 'words_result' field in JSON object.");
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return wordsList;
    }

}
