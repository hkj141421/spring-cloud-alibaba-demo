package org.knight.rest;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.client.NacosPropertySourceLocator;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.Sph;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import org.apache.dubbo.configcenter.support.nacos.NacosDynamicConfiguration;
import org.knight.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executor;

/**
 * @Date 2022/3/15
 * @Author knight
 * @Description
 **/
@NacosConfigurationProperties(dataId = "application",autoRefreshed = true,type= ConfigType.YAML)
@RequestMapping("provider")
@RestController
public class NacosController {

    @Autowired
    NacosConfigManager configManager;

    @NacosValue(value = "${test:none}",autoRefreshed = true)
    String test;

    @Value("${env:local}")
    String env;

    @Autowired
    HelloService helloService;

    @GetMapping("/nacos/{name}")
    public Object getNacosInfo(@PathVariable("name") String name) throws NacosException {
        ConfigService nacosConfigService = configManager.getConfigService();
        nacosConfigService.addListener("application",
                "DEFAULT_GROUP",
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String s) {
                        System.out.println(s);
                    }
                });
        return name;
    }

    @GetMapping("config")
    public String getNacosConfig(HttpServletRequest request){
        String res = helloService.sayHi("provider");
        return "N".equals(res)?res:"Y";
    }

    public NacosConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(NacosConfigManager configManager) {
        this.configManager = configManager;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
