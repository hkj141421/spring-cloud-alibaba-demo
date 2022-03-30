package org.knight.rest;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.dubbo.config.annotation.Reference;
import org.knight.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2022/3/14
 * @Author knight
 * @Description
 **/
@RequestMapping("consumer")
@RestController
public class ConsumerController {

    @Reference(url = "dubbo://192.168.40.3:28080/org.knight.api.HelloService")
    HelloService helloService;

    @Autowired
    NacosServiceManager namingService;

    @GetMapping("hello/{name}")
    public String sayHello(@PathVariable("name") String name){
        return helloService.sayHi(name);
    }

    @GetMapping("nacos/{name}")
    public Object getNacosInstance(@PathVariable("name")String name) throws NacosException {
        return namingService!=null?"注入成功":"注入失败";
    }
}
