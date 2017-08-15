package com.cdj.ends.api.login;

import com.cdj.ends.api.RetrofitManagerAPI;

import com.cdj.ends.call.LoginCallService;
import com.cdj.ends.data.User;
import com.cdj.ends.dto.ResultDTO;

import okhttp3.ResponseBody;
import retrofit2.Callback;


/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class LoginAPI extends RetrofitManagerAPI<LoginCallService, ResponseBody> {

    private LoginCallService loginCallService;

    public LoginAPI(String baseUrl) {
        super(LoginCallService.class, baseUrl);
        loginCallService = createCallService();
    }

    public void postLoginInfo(User user, Callback<ResultDTO> result) {
        loginCallService.postLoginInfo(user).enqueue(result);
    }
}
