package com.cdj.ends.ui;

/**
 * Created by Dongjin on 2017. 8. 8..
 */
import android.app.Activity;
import android.content.Intent;

import com.cdj.ends.R;
import com.cdj.ends.base.util.SharedPreferencesService;
import com.cdj.ends.ui.main.MainActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    private Activity mActivity;

    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("ENDS");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(35f); //float value
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
        //configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
