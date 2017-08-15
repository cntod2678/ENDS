package com.cdj.ends.call;


import com.cdj.ends.data.User;
import com.cdj.ends.dto.ResultDTO;
import com.cdj.ends.dto.UserDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public interface LoginCallService {

    @POST("/login")
    Call<ResultDTO> postLoginInfo(@Body User user);

}
