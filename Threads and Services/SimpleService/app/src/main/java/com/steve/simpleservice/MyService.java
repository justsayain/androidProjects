package com.steve.simpleservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
/**
 * Created by Steve on 10/2/2014.
 */
public class MyService extends Service {
    public MyService(){
    }
    @Override
    public IBinder onBind(Intent intent){
        throw new UnsupportedOperationException("not yet implemented");
    }
    @Override
    public void onCreate(){
        Toast.makeText(this,"new service created",Toast.LENGTH_LONG).show();
    }
    public void onStart(){
        Toast.makeText(this, "Service Starteed",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy(){
        Toast.makeText(this, "Service Destroyed",Toast.LENGTH_LONG).show();
    }
}
