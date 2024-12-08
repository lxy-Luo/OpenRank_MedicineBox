package com.qmx.smedicinebox.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Component
public class ExecuteCommandInBackground /*implements CommandLineRunner*/ {

    private static final Logger logger = LogManager.getLogger(ExecuteCommandInBackground.class);

    /**
     * 本机IP
     */
    private static String ClientIP = "127.0.0.1";
    /**
     * 服务器IP
     */
    private static String ServerIP = "139.9.202.40";
    /**
     * 服务提供者端口
     */
    private static String ServerPort = "1111";

    /**
     * 服务端提供接口
     */
    private static String ServerPorts[] = {"81"};

    /**
     * 本地映射端口
     */
    private static String ClientPorts[] = {"81"};

    /**
     * 端口映射
     */
    private static String ServerToClientPort = "";


//    @Override
    public void run(String... args) throws Exception {

        logger.info("内网穿透启动");

        for(int i = 0;i < ClientPorts.length;i++){
            logger.info("端口映射："+ServerIP+":"+ServerPorts[i]+"-->"+ClientIP+":"+ClientPorts[i]);
            ServerToClientPort += " R:" + ServerPorts[i] + ":" + ClientIP + ":" + ClientPorts[i];
        }

        // 创建 Process 对象以便稍后引用
        Process process = null;

        try {
            // 创建 ProcessBuilder 对象并设置命令
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c", "%ComSpec%", "/k",
                    "D:\\IntranetPenetration\\Chisel\\chisel.exe client " + ServerIP +  ":" + ServerPort + ServerToClientPort
            );
            // 设置工作目录
            processBuilder.directory(null); // 使用当前工作目录

            // 启动进程并保存 Process 对象的引用
            process = processBuilder.start();

            // 注册关闭钩子
            Process finalProcess = process;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // 在关闭钩子中关闭进程
                if (finalProcess != null) {
                    finalProcess.destroy();
                    logger.info("内网穿透终止");
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
