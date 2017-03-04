package com.steve.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Main_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        final Button button;
        button = (Button) findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.netflix.com"));
                startActivity(browserIntent);
            }
        });
        tToast("onCreate");
    }
    public void onStart(){
        super.onStart();
        tToast("onStart");
    }
    public void onRestart(){
        super.onRestart();
        tToast("onRestart");
    }
    public void onResume(){
        super.onResume();
        tToast("onResume");
    }
    public void onPause(){
        super.onPause();
        tToast("onPause");
    }
    public void onStop(){
        super.onStop();
        tToast("onStop");
    }
    public void onDestroy(){
        super.onDestroy();
        tToast("onDstroy");
    }
    private void tToast(String text){
        Context context= getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_, menu);
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
