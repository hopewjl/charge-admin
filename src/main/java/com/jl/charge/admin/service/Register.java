package com.jl.charge.admin.service;

import com.jl.charge.admin.service.model.ChargeDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangjl on 18/5/10.
 */
@Component
@PropertySource("classpath:config.properties")
public class Register {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private String registerTopic = "/com/jl/charge/admin/server";
    @Autowired
    private MqttClientProxy mqttClientProxy;
    @Autowired
    private MqttPub controller;

    @PostConstruct
    public void init() {
        mqttClientProxy.init();
        mqttClientProxy.subscribe(registerTopic);
    }

    public void onRegist(ChargeDevice device)
    {
        logger.info("device regist:", device);
        controller.sendMsg(device.getId(),"OK");
    }


}
