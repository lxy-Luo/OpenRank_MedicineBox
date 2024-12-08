package com.qmx.smedicinebox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 格式转化
     *
     *
     */
    public static Date TransformStringToDateTime(String now,String dateformat,String timeformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+dateformat+"MM"+dateformat+"dd HH"+timeformat+"mm"+timeformat+"ss");

        Date parse = null;
        try {
            parse = dateFormat.parse(now);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parse;
    }

    public static Date TransformStringToDateTimeNotSecond(String now,String dateformat,String timeformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+dateformat+"MM"+dateformat+"dd HH"+timeformat+"mm");

        Date parse = null;
        try {
            parse = dateFormat.parse(now);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parse;
    }
    public static String TransformDateTimeToString(Date now,String dateformat,String interval, String timeformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+dateformat+"MM"+dateformat+"dd"+interval+"HH"+timeformat+"mm"+timeformat+"ss");
        String nowAsString = dateFormat.format(now);
        return nowAsString;
    }
    public static String TransformDateTimeToString(Date now,String dateformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+dateformat+"MM"+dateformat+"dd");
        String nowAsString = dateFormat.format(now);
        return nowAsString;
    }
    public static String TransformStringSlashToDefault(String now){
        String formatted_date = now.replace('/', '-');
        return formatted_date;
    }
    public static String TransformStringDefaultToSlash(String now){
        String formatted_date = now.replace('-', '/');
        return formatted_date;
    }

    /**
     * 计算时间差
     * @param Previous
     * @param now
     * @return
     */
    public static Long PreviousToNow(String Previous,LocalDateTime now){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedFuture = LocalDateTime.parse(Previous, formatter);
        Duration between = Duration.between(parsedFuture, now);
        return between.getSeconds();
    }
    public static Long PreviousToNow(LocalDateTime Previous,LocalDateTime now){

        Duration between = Duration.between(Previous, now);
        return between.getSeconds();
    }
    public static Long PreviousToNow(Date previous, Date now) {
        // 将 Date 转换为 LocalDateTime
        LocalDateTime previousDateTime = previous.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowDateTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 计算时间差
        Duration duration = Duration.between(previousDateTime, nowDateTime);

        // 返回秒数
        return duration.getSeconds();
    }


    /**
     * 将Date分解
     * @param dateTime
     * @return
     */
    public static String splitDate(Date dateTime,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"+format+"MM"+format+"dd");
        return dateFormat.format(dateTime);
    }
    public static String splitDateNotYear(Date dateTime,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM"+format+"dd");
        return dateFormat.format(dateTime);
    }


    public static String splitTime(Date dateTime,String format) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH"+format+"mm"+format+"ss");
        return timeFormat.format(dateTime);
    }
    public static String splitTimeNotSecond(Date dateTime,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH"+format+"mm");
        return dateFormat.format(dateTime);
    }


    /**
     * 增添与删除前置零
     * @param dateString
     * @param regex
     * @return
     */
    public static String addLeadingZeros(String dateString,String regex) {
        String[] parts = dateString.split(regex);
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // 添加前置零到月份和日期部分
        String formattedMonth = (month < 10) ? "0" + month : String.valueOf(month);
        String formattedDay = (day < 10) ? "0" + day : String.valueOf(day);

        // 构建格式化后的日期字符串
        return String.format("%d"+regex+"%s"+regex+"%s", year, formattedMonth, formattedDay);
    }
    public static String removeLeadingZeros(String dateString,String regex) {
        String[] parts = dateString.split(regex);
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // 移除月份和日期部分的前导零
        String formattedMonth = String.valueOf(month);
        String formattedDay = String.valueOf(day);

        // 如果月份或日期小于 10，移除前导零
        if (month < 10 && formattedMonth.startsWith("0")) {
            formattedMonth = formattedMonth.substring(1);
        }
        if (day < 10 && formattedDay.startsWith("0")) {
            formattedDay = formattedDay.substring(1);
        }

        // 构建格式化后的日期字符串
        return String.format("%d"+regex+"%s"+regex+"%s", year, formattedMonth, formattedDay);
    }

    /**
     * 获取最近日期
     */

    public static String getYesterdayDateString () {
        // 获取当前日期
        Date currentDate = new Date();

        // 创建 Calendar 对象并设置为当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 减去一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 获取昨天的日期
        Date yesterdayDate = calendar.getTime();

        // 创建 SimpleDateFormat 对象以便格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 格式化昨天的日期为字符串
        String yesterdayDateString = sdf.format(yesterdayDate);

        return yesterdayDateString;
    }

    public static String getYesterdayDateString (Date currentDate) {

        // 创建 Calendar 对象并设置为当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 减去一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 获取昨天的日期
        Date yesterdayDate = calendar.getTime();

        // 创建 SimpleDateFormat 对象以便格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 格式化昨天的日期为字符串
        String yesterdayDateString = sdf.format(yesterdayDate);

        return yesterdayDateString;
    }


    public static Date getYesterdayDate () {
        // 获取当前日期
        Date currentDate = new Date();

        // 创建 Calendar 对象并设置为当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 减去一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 获取昨天的日期
        Date yesterdayDate = calendar.getTime();

        return yesterdayDate;
    }

    public static Date getYesterdayDate (Date currentDate) {
        // 创建 Calendar 对象并设置为当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 减去一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 获取昨天的日期
        Date yesterdayDate = calendar.getTime();

        return yesterdayDate;
    }
}
