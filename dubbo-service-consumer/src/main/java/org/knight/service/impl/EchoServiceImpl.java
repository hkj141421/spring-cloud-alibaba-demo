package org.knight.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.knight.api.EchoService;

/**
 * @Date 2022/3/14
 * @Author knight
 * @Description
 **/
@Service
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String msg) {
        return msg;
    }
}
