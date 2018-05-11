package com.jl.charge.admin.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangjl on 18/5/10.
 */
public class ChargeDevice {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(ChargeDevice.class);

    private String id="";
    private String name="";
    private String address="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            logger.error("ChargeDevice::toString error!", e);
        }
        return null;
    }

    public static ChargeDevice parseFromJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChargeDevice chargeDevice = objectMapper.readValue(json, ChargeDevice.class);
            return chargeDevice;
        } catch (Exception e) {
            logger.error("ChargeDevice::parseFromJson error!", e);
            return null;
        }
    }
}
