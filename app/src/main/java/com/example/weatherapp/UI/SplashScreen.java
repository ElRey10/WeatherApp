package com.example.weatherapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.weatherapp.R;

public class SplashScreen extends Activity {
    private ImageView iconWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        iconWeather = findViewById(R.id.icon_id);
        Animation iconAnimation = AnimationUtils.loadAnimation(this, R.anim.icon_anim);
        iconWeather.startAnimation(iconAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3 * 1000);
    }

}
