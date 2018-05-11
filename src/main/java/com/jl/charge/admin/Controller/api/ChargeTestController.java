package com.jl.charge.admin.Controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jl.charge.admin.service.MqttPub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjl on 18/5/11.
 */
@Controller
@RequestMapping(value = "/jl/charge/api/v1")
public class ChargeTestController {

    @Autowired
    private MqttPub mqttPub;

    @ResponseBody
    @RequestMapping(value = "/test/{deviceId}", method = RequestMethod.GET)
    public JsonNode banPubAccount(@PathVariable("deviceId") String deviceId, HttpServletResponse response) {
        mqttPub.sendMsg(deviceId, "get");
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("device", deviceId);
        objectNode.put("res", 0);
        return objectNode;
    }
}
