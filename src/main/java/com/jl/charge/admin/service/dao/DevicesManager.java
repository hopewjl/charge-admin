package com.jl.charge.admin.service.dao;

import com.jl.charge.admin.service.model.ChargeDevice;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangjl on 18/5/10.
 */
@Component
public class DevicesManager {
    private Map<String, ChargeDevice> deviceMap = new ConcurrentHashMap<>();

    public void addDevice(ChargeDevice chargeDevice) {
        if (chargeDevice != null) {
            deviceMap.put(chargeDevice.getId(), chargeDevice);
        }
    }

    public Collection<ChargeDevice> allDevices(){
       return deviceMap.values();
    }
}
