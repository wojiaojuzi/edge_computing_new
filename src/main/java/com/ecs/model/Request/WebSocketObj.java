package com.ecs.model.Request;

import lombok.Data;

@Data
public class WebSocketObj {
    private String type;
    private String data;

    public WebSocketObj(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
