package com.qmx.smedicinebox.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.dashscope.common.Message;
import java.util.Arrays;

public class QWenPlusUtil {
    private final Generation generation = new Generation();

    @Value("${qwen-plus.api-key}")
    private static String ACCESS_KEY_ID;


    public String chat(String text) {
        try {
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("You are a helpful assistant.")
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(text)
                    .build();

            GenerationParam param = GenerationParam.builder()
                    .apiKey(ACCESS_KEY_ID) // 如果需要的话可以改成从配置文件中读取
                    .model("qwen-plus")
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .build();

            GenerationResult result = generation.call(param);
            return getFormattedResult(result);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {

            throw new RuntimeException("调用生成服务时发生错误: " + e.getMessage(), e);
        }
    }

    public String chat(String ins, String text) {
        try {
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("您是一位专业的数据分析师。")
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(ins + text)
                    .build();


            GenerationParam param = GenerationParam.builder()
                    .apiKey(ACCESS_KEY_ID) // 如果需要的话可以改成从配置文件中读取
                    .model("qwen-plus")
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .build();

            GenerationResult result = generation.call(param);
            return getFormattedResult(result);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException("调用生成服务时发生错误: " + e.getMessage(), e);
        }
    }
    public static String getFormattedResult(GenerationResult result) {
        if (result != null && result.getOutput() != null && !result.getOutput().getChoices().isEmpty()) {
            // 提取 message 的 content 部分
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();
            return content; // 返回内容部分
        }
        return "No valid result found.";
    }
}
