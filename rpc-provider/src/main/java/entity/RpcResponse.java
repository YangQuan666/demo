package entity;

import java.io.Serializable;

/**
 * 封装 RPC 响应
 */

public class RpcResponse implements Serializable {

    private String requestId;
    /**
     * response code
     */
    private Integer code;
    /**
     * response message
     */
    private String message;
    /**
     * response body
     */
    private Object data;

    public static RpcResponse success(Object data, String requestId) {
        RpcResponse response = new RpcResponse();
        response.setRequestId(requestId);
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static RpcResponse fail(Integer code, String message) {
        RpcResponse response = new RpcResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}