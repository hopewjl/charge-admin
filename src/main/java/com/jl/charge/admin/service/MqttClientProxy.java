package com.jl.charge.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jl.charge.admin.service.model.ChargeDevice;
import com.jl.charge.admin.service.model.Message;
import com.jl.charge.admin.service.model.MessageType;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangjl on 18/5/10.
 */
@Component
@PropertySource("classpath:config.properties")
public class MqttClientProxy implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqttClientProxy.class);

    protected MqttClient client;
    protected MqttClient clientPub;

    @Value("${mqtt.server.uri}")
    private String mqttServerUri;
    @Value("${mqtt.server.deviceid}")
    private String deviceId;

    @Value("${mqtt.server.user}")
    private String mqttServerUser;
    @Value("${mqtt.server.passwd}")
    private String mqttServerPasswd;

    @Autowired
    private Register register;

    public void init() {
        connect2MqttServer();
    }

    protected void connect2MqttServer() {
        try {
            clientPub = client = new MqttClient(mqttServerUri, deviceId+"pub");
            client = new MqttClient(mqttServerUri, deviceId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttServerUser);
            options.setPassword(mqttServerPasswd.toCharArray());
            client.setCallback(this);
            client.connect(options);
            clientPub.setCallback(this);
            clientPub.connect(options);

        } catch (Exception e) {
            logger.error("connect2MqttServer error!", e);
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (Exception e) {
            logger.error("start register server error!", e);

        }
    }

    public boolean publish(String topic, String msg) {
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(msg.getBytes());
            clientPub.publish(topic, mqttMessage);
        } catch (Exception e) {
            logger.error("publish msg error!,topic={} msg={}", topic, msg);
            return false;
        }
        return true;
    }

    public void parse(MqttMessage mqttMessage) {
        try {
            String reqStr = new String(mqttMessage.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            Message msg = objectMapper.readValue(reqStr, Message.class);
            doMsg(msg);
        } catch (Exception e) {
            logger.error("parse message error!", e);
        }
    }

    public void doMsg(Message msg) {
        Object req = msg.parseBody();
        if (msg.getType() == MessageType.MST_TYPE_REGIST) {
            ChargeDevice chargeDevice = (ChargeDevice) req;
            register.onRegist(chargeDevice);
        }
    }

    public MqttClient getClient() {
        return client;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        parse(mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
