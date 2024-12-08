package com.qmx.smedicinebox.utils;


import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    public static String mailObject = "smedicinebox@163.com";

    public static Boolean isQQEmail(String email){
        final String QQ_EMAIL_REGEX = "[0-9]{5,10}@qq.com";
        Pattern pattern = Pattern.compile(QQ_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static Boolean isNeteaseEmail(String email) {
        final String NETEASE_EMAIL_REGEX = "[a-zA-Z0-9._%+-]+@(163|126)\\.com";
        Pattern pattern = Pattern.compile(NETEASE_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void sendEmail(){
        System.out.println(javaMailSender);
    }





}
