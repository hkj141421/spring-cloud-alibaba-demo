package org.knight.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.dubbo.config.annotation.Service;
import org.knight.api.HelloService;

/**
 * @Date 2022/3/14
 * @Author knight
 * @Description
 **/
@org.springframework.stereotype.Service
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    @SentinelResource(value = "getNacosConfig",blockHandler = "handlerSayHi",blockHandlerClass = HelloServiceImpl.class)
    public String sayHi(String name) {
//        Entry entry = null;
//        try {
//            entry = SphU.entry("getNacosConfig");
            System.out.println(String.format("hello dubbo! I am %s",name));
            return String.format("hello! nice to meet you.");
//        } catch (BlockException e) {
//            e.printStackTrace();
//            return "N";
//        } finally {
//            if(entry!=null){
//                entry.exit();
//            }
//        }
    }

    public static String handlerSayHi(String name,BlockException e){
        System.out.println("触发限流");
        return "N";
    }

}
