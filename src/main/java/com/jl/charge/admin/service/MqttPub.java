package com.jl.charge.admin.service;

import com.jl.charge.admin.service.model.ChargeDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangjl on 18/5/10.
 */
@Component
public class MqttPub {
    private static final String clientTopicProfix = "/com/jl/charge/device/";

    @Autowired
    private MqttClientProxy mqttClientProxy;

    public String getClientTopic(ChargeDevice chargeDevice) {
        return getClientTopic(chargeDevice.getId());
    }

    public String getClientTopic(String deviceId) {
        return clientTopicProfix + deviceId;
    }

    public boolean sendMsg(String deviceId, String msg) {
        return mqttClientProxy.publish(getClientTopic(deviceId), msg);
    }
}
