package com.steve.simplerecording;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

public class MyActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    MediaPlayer theSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        OUTPUT_FILE= Environment.getExternalStorageDirectory()+"/audiorecorder.3gpp";
    }
    public void buttonTapped(View view){
        switch (view.getId()){
            case R.id.record:
                try{
                    recordAudio();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.stop:
                try{
                    stopRecording();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.playBack:
                try{
                    playBackAudio();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    private void recordAudio() throws Exception {
        checkRecords();
        File outFile = new File(OUTPUT_FILE);
        if(outFile.exists())
            outFile.delete();
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //recorder.setMaxDuration(5000);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();
    }
    private void stopRecording(){
        if(recorder!=null)
           recorder.stop();
    }
    private void playBackAudio() throws Exception{
        checkRecords();
        mediaPlayer= new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
    private void checkRecords(){
        if(recorder !=null){
            recorder.stop();
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
