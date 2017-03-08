package com.justsayain.murther.pewpewpewpew;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.button);
        final Animation animGrow = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        final MediaPlayer pew = MediaPlayer.create(this, R.raw.airhorn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animGrow);
                pew.start();
            }
        });
    }
}
