package com.steve.simplemusic;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity implements Runnable {
    MediaPlayer theSong;
    Button playPauseButton;
    private TextView statusText;
    private String OUTPUT_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        theSong = MediaPlayer.create(this, R.raw.walkaway);
        OUTPUT_FILE=Environment.getExternalStorageDirectory()+"/audiorecorder.3gpp";
        playPauseButton=(Button)findViewById(R.id.play_pause);
        statusText= (TextView) findViewById(R.id.status);
        try{
            theSong.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theSong.isPlaying()) {
                    pauseMP();
                } else {
                    startMP();
                }
            }
        });
        Button actionButton= (Button)findViewById(R.id.record);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_thread();
            }
        });
        Button replayButton= (Button)findViewById(R.id.play);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread= new Thread(MyActivity.this);
                thread.start();
                Toast.makeText(getApplicationContext(),"replay recording",Toast.LENGTH_LONG).show();
            }
        });
    }
    String textString;
    final Handler mhandler= new Handler();
    //create runnable for posting
    final Runnable mUpdateResults= new Runnable() {
        @Override
        public void run() {
            updateResultsInUi(textString);
        }
    };
    private void updateResultsInUi(String updateText){
        statusText.setText(updateText);
    }
    private void record_thread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                textString = "Starting";
                mhandler.post(mUpdateResults);
                record();
                textString = "Done";
                mhandler.post(mUpdateResults);
            }
        });
        thread.start();
    }
    private int audioEncoding= AudioFormat.ENCODING_PCM_16BIT;
    int frequency = 11025; //Hz
    int bufferSize = 50* AudioTrack.getMinBufferSize(frequency,
            AudioFormat.CHANNEL_OUT_MONO, audioEncoding);
    // Create new AudioRecord object to record the audio.
    public AudioRecord audioRecord = new AudioRecord(
            MediaRecorder.AudioSource.MIC,
            frequency, AudioFormat.CHANNEL_IN_MONO,
            audioEncoding, bufferSize);
    // Create new AudioTrack object w/same parameters as AudioRecord obj
    public AudioTrack audioTrack = new AudioTrack(
            AudioManager.STREAM_MUSIC, frequency,
            AudioFormat.CHANNEL_OUT_MONO,
            audioEncoding, 4096,
            AudioTrack.MODE_STREAM);
    short[] buffer = new short[bufferSize];
    public void record() {
        try {
            audioRecord.startRecording();
            audioRecord.read(buffer, 0, bufferSize);
            audioRecord.stop();
        } catch (Throwable t) {
            Log.e("AudioExamplesRaw", "Recording Failed");
        }
       // Toast.makeText(getApplicationContext(),"recording",Toast.LENGTH_LONG).show();
    }
    public void run() { //play audio using runnable Activity
        audioTrack.play();
//this alone works: audioTrack.write(buffer, 0, bufferSize);
//but for illustration showing another way to play using a loop:
        int i=0;
        while(i<bufferSize) {
            audioTrack.write(buffer, i++, 1);
        }
        return;
    }
    void pauseMP(){
        playPauseButton.setText("Play");
        theSong.pause();
        Toast.makeText(getApplicationContext(),"Song Paused",Toast.LENGTH_LONG).show();
    }
    void startMP(){
        theSong.start();
        playPauseButton.setText("Pause");
        Toast.makeText(getApplicationContext(),"Brett Dennen song playing",Toast.LENGTH_LONG).show();
    }
    boolean needToResume = false;

    @Override
    protected void onPause(){
        if(theSong !=null && theSong.isPlaying()){
            needToResume = true;
            pauseMP();
        }
        if(audioTrack!=null){
            if(audioTrack.getPlayState()==AudioTrack.PLAYSTATE_PLAYING){
                audioTrack.pause();
            }
        }
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(needToResume&&theSong!=null){
            startMP();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
