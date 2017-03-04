package com.steve.threads;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int buttonPress =0;
    TextView mButtonLabel;

    private long mStartTime = 0L;
    private TextView mTimeLabel;


    private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(mStartTime ==0L){
            mStartTime = SystemClock.uptimeMillis();
            mHandler.removeCallbacks(mUpdatedTimeTask);
            mHandler.postDelayed(mUpdatedTimeTask,100);
        }
        mTimeLabel = (TextView) findViewById(R.id.text);
        mButtonLabel = (TextView) findViewById(R.id.trigger);

        Button startButton = (Button) findViewById(R.id.trigger);
        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mButtonLabel.setText("Pressed "+ ++buttonPress + "times");
            }
        });
    }
    private Runnable mUpdatedTimeTask = new Runnable() {
        @Override
        public void run() {
            final long start = mStartTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis/1000);
            int minutes = seconds/60;
            seconds = seconds %60;
            mTimeLabel.setText(""+ minutes + ":" + String.format("%02d",seconds));
            mHandler.postDelayed(this,200);
        }
    };

    @Override
    protected void onPause(){
        mHandler.removeCallbacks(mUpdatedTimeTask);
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause",Toast.LENGTH_LONG);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mHandler.postDelayed(mUpdatedTimeTask,100);

        Toast.makeText(getApplicationContext(), "onResume",Toast.LENGTH_LONG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
