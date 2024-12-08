/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.qmx.smedicinebox.constant;

public class Constant {

    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";
    /**
     * 分页参数描述
     */
    public static final String ISREAD = "1";

    /**
     * 基本分页参数描述
     */
    public static final String Params =
            "可选参数：（参数均为字符串）<br> " +
                    "page（当前页）<br> " +
                    "limit（每页显示记录数）<br>" +
                    "sidx（排序字段）<br> " +
                    "order（排序方式）<br> " +
                    "asc（升序）";

    /**
     * 消息分页参数是参数描述
     */
    public static final String MessageParams =Params +
                    "<br>isread（是否已读，0：未读，1：已读）";
    /**
     * 消息读取状态
     */
    public static final Integer MSG_READ = 1;

    public static final Integer MSG_NOT_READ = 0;

    /**
     * 默认的发送信息方
     */
    public static final String SENDER_DEFAULT = "未知";

    /**
     * App发送信息方
     */
    public static final Integer SENDER_APP = 3;

    /**
     * 斜线
     */
    public static final String SLASH = "/";

    /**
     * 连字符
     */
    public static final String HYPHEN = "-";
    /**
     * 冒号
     */
    public static final String COLON = ":";

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 下划线
     */
    public static final String UNDERSCORE = "_";

    /**
     * 早中晚用药提醒
     */
    public static final String MORNING = "早安！记得按时吃药哦，保持健康！";
    public static final String NOON = "中午好！别忘了吃药，按时服用哦！";
    public static final String EVENING = "晚安！别忘了今天的药物，好好休息！";
    public static final String TWICE_UNTREATED = "根据我们的记录，您早上和中午都未按时服用药物。";
    public static final String THREE_TIMES_UNTREATED = "根据我们的记录，您今天未按时服用药物。";
    public static final String DEFAULT_DAYS_UNTREATED = "根据我们的记录，您这几天未按时服用药物。";

    public static final Integer REMIND_DEFAULT_DAYS = 2;
}
