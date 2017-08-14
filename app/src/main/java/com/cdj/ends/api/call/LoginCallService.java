package com.cdj.ends.api.call;


import com.cdj.ends.dto.UserDTO;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public interface LoginCallService {

    @POST("/")
    Call<UserDTO> postLoginInfo();


}
