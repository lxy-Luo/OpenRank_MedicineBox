package com.qmx.smedicinebox;


import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.qmx.smedicinebox.utils.ocr.AliyunOcrUtil;
import com.qmx.smedicinebox.utils.ocr.BaiduOcrUtil;
import com.qmx.smedicinebox.utils.ocr.OcrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootTest
public class AliyunOcrTest {
    @Test
    public void test() throws TeaException {
        String path = "E:\\Image\\14.jpg";
        OcrUtil aliyunOcrUtil = new AliyunOcrUtil();
        String s = aliyunOcrUtil.generalBasic(path);
        OcrUtil baiduOcrUtil = new BaiduOcrUtil();
        String s1 = baiduOcrUtil.generalBasic(path);
        System.out.println("AliyunOcrUtil\n"+s);
        System.out.println("BaiduOcrUtil\n"+s1);
    }
}