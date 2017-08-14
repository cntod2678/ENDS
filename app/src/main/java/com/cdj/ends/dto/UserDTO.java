package com.cdj.ends.dto;

import com.cdj.ends.data.Temp;
import com.cdj.ends.data.User;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class UserDTO {
    String resultcode;

    String message;

    User response;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getResponse() {
        return response;
    }

    public void setResponse(User response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "resultcode='" + resultcode + '\'' +
                ", message='" + message + '\'' +
                ", response=" + response +
                '}';
    }
}
