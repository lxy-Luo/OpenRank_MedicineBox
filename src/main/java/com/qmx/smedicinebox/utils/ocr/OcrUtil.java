package com.qmx.smedicinebox.utils.ocr;

import java.util.List;

/**
 * ocr 工具类
 * @author qmx
 */
public interface OcrUtil {
    /*
     * 通用文字识别
     * @param filePath 文件路径
     * @return 识别结果(json格式)
     */
    String generalBasic(String filePath) ;

    /**
     * 通用文字识别(返回word_list)
     * @param json
     * @return
     */
    List<String> generalWordList(String json) ;
}
