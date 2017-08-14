package com.cdj.ends.ui.login;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cdj.ends.R;
import com.cdj.ends.api.login.LoginAPI;
import com.cdj.ends.base.util.ParserHelperFromJson;
import com.cdj.ends.data.User;
import com.cdj.ends.dto.UserDTO;
import com.cdj.ends.ui.main.MainActivity;
import com.google.gson.Gson;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import retrofit2.Call;
import retrofit2.Response;

import static com.cdj.ends.Config.NAVER_LOGIN_URL;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public static OAuthLogin mOAuthLoginInstance;

    private GetUserThread getUserThread;

    private OAuthLoginButton mOAuthLoginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        initNaver();
    }

    private void initNaver(){
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(
                this,
                "5guuO59Mn8jmg9u1eI8j",
                "iZl5ET6TCL",
                "ENDS"
        );


        mOAuthLoginButton.setOAuthLoginHandler(new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {

                if(success) {
                    final String accessToken = mOAuthLoginInstance.getAccessToken(LoginActivity.this);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = mOAuthLoginInstance.requestApi(LoginActivity.this, accessToken, NAVER_LOGIN_URL);

                            Gson gson = new Gson();
                            UserDTO userDTO = gson.fromJson(response, UserDTO.class);
                            Log.d(TAG, userDTO.toString());
                            User user = userDTO.getResponse();
                            //Log.d(TAG, user.getEmail());

                            //todo 디비 저장

                        }
                    }).start();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "잠시 후 로그인을 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class GetUserThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

}
