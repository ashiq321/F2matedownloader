package com.f2mate.fbdownloader;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;

public class SplashActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setLightMode(SplashActivity.this);

        iv = findViewById(R.id.logo);

        Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.intro_animation);
        iv.startAnimation(myanimation);
        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {



                Intent i = new Intent(SplashActivity.this, IntroActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, 1*1000); // wait for 1 seconds
    }
}
