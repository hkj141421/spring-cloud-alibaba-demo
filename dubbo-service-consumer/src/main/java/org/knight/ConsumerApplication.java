package org.knight;


import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.knight.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2022/3/14
 * @Author knight
 * @Description
 **/
@DubboComponentScan
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    public static void dubboStart(){
        ClassPathXmlApplicationContext context  = new ClassPathXmlApplicationContext("classpath*:/dubbo-consumer-config.xml");
        context.start();
        HelloService helloService = (HelloService) context.getBean("helloService");
        helloService.sayHi("consumer");
    }
}
