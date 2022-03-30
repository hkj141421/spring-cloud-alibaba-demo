package org.knight;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.knight.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import sun.applet.Main;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
@DubboComponentScan
@SpringBootApplication
public class ProviderApplication implements ServletContextListener {

    public static void main( String[] args ) {
        SpringApplication.run(ProviderApplication.class,args);
    }

    public static void dubboStart() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/dubbo-config.xml");
        context.start();
        System.in.read();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("注入规则");
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("getNacosConfig");
        rule.setClusterMode(false);
        rule.setCount(5);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRules.add(rule);
        FlowRuleManager.loadRules(flowRules);
    }

}
