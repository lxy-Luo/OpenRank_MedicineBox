package com.qmx.smedicinebox;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.qmx.smedicinebox.sys")
@ComponentScan("com.qmx.smedicinebox.filter")
@ComponentScan("com.qmx.smedicinebox.config")
@EnableAsync
@EnableTransactionManagement
public class SMedicineBoxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SMedicineBoxApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(SMedicineBoxApplication.class, args);
//		// 打印所有 Bean 的名称
//		for (String beanName : context.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}
	}
}
