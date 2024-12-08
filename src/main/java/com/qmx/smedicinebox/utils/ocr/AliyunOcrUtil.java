package com.qmx.smedicinebox.utils.ocr;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qmx.smedicinebox.utils.Base64Util;
import com.qmx.smedicinebox.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 阿里云OCR工具类
 * @author qmx
 * @date 2023/3/10 16:55
 */
public class AliyunOcrUtil implements OcrUtil {
    @Override
    public String generalBasic(String filePath) {
        String host = "https://tysbgpu.market.alicloudapi.com";
        String path = "/api/predict/ocr_general";
        String method = "POST";
        String appcode = "1f64dca08c624b83b23aedf87bc0e60f";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        // 读取本地图片并转换为 Base64 字符串
        String imagePath = filePath; // 替换为本地图片路径

        String base64Image = Base64Util.convertImageToBase64(imagePath);

        Map<String, String> querys = new HashMap<String, String>();
        String bodys = "{\"image\":\"" + base64Image + "\",\"configure\":{\"output_prob\":true,\"output_keypoints\":false,\"skip_detection\":false,\"without_predicting_direction\":false}}";

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            return responseBody;
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
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

            // 检查 "ret" 是否存在且是一个数组
            if (jsonObject.containsKey("ret") && jsonObject.get("ret") instanceof JSONArray) {
                JSONArray retArray = jsonObject.getJSONArray("ret");

                for (int i = 0; i < retArray.size(); i++) {
                    JSONObject resultObject = retArray.getJSONObject(i);

                    // 检查 "word" 是否存在且是一个字符串
                    if (resultObject.containsKey("word") && resultObject.get("word") instanceof String) {
                        String word = resultObject.getStr("word");
                        wordsList.add(word);
                    } else {
                        System.out.println("Invalid 'word' field in result object at index " + i);
                    }
                }
            } else {
                System.out.println("Invalid 'ret' field in JSON object.");
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return wordsList;
    }

    public String generalBasicBase64(String base64Image) {
        String host = "https://tysbgpu.market.alicloudapi.com";
        String path = "/api/predict/ocr_general";
        String method = "POST";
        String appcode = "1f64dca08c624b83b23aedf87bc0e60f";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, String> querys = new HashMap<String, String>();
        String bodys = "{\"image\":\"" + base64Image + "\",\"configure\":{\"output_prob\":true,\"output_keypoints\":false,\"skip_detection\":false,\"without_predicting_direction\":false}}";

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            return responseBody;
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
