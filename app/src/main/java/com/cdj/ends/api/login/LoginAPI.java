package com.cdj.ends.api.login;

import com.cdj.ends.api.RetrofitManagerAPI;
import com.cdj.ends.api.call.LoginCallService;
import com.cdj.ends.dto.UserDTO;

import retrofit2.Callback;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class LoginAPI extends RetrofitManagerAPI<LoginCallService, UserDTO> {

    private LoginCallService loginCallService;

    public LoginAPI(String baseUrl) {
        super(LoginCallService.class, baseUrl);
        loginCallService = createCallService();
    }

    public void postLoginInfo(String accessToken, Callback<UserDTO> callback) {
        loginCallService.postLoginInfo().enqueue(callback);
    }
}
