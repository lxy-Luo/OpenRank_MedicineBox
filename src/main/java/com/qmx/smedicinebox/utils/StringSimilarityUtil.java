package com.qmx.smedicinebox.utils;


import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringSimilarityUtil {
    // 计算编辑距离
    public static int calculateEditDistance(String str1, String str2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        return levenshteinDistance.apply(str1, str2);
    }

    // 计算相似度（相似度 = 1 / (1 + 编辑距离)）
    public static double calculateSimilarity(String str1, String str2) {
        int editDistance = calculateEditDistance(str1, str2);
        return 1.0 / (1.0 + editDistance);
    }
}
