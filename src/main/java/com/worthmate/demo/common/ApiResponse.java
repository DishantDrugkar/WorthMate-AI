package com.worthmate.demo.common;

public class ApiResponse {
    private Boolean success;
    private Object data;

    public ApiResponse(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}
