package com.jl.charge.admin.service.model;

/**
 * Created by wangjl on 18/5/10.
 */
public class Message {
    private int type;
    private String body;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object parseBody() {
        switch (type) {
            case MessageType.MST_TYPE_REGIST:
                return ChargeDevice.parseFromJson(body);
            case MessageType.MST_TYPE_MSG:
                break;
        }
        return null;
    }


}
