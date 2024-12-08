package com.qmx.smedicinebox.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    /**
     * 生成指定范围内的随机整数
     *
     * @param min 最小值（包括）
     * @param max 最大值（不包括）
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
