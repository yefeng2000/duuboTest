package com.example.demo.dto;

/**
 * @ClassName:  ApiResult
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 寻找手艺人
 * @email:  maker2win@163.com
 * @date:   2019年5月17日 上午9:11:38
 *
 * @Copyright: 2019 www.maker-win.net Inc. All rights reserved.
 *
 */
public class ApiResult {

    private Integer code;

    private String message;

    private Object data;

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
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
        this.message = message == null ? null : message.trim();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
