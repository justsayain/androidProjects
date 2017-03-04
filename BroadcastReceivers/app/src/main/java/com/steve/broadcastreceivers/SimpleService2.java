package com.steve.broadcastreceivers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Steve on 10/3/2014.
 */
public class SimpleService2 extends Service {
    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }
    @Override
    public void onCreate(){
        super.onDestroy();
        Toast.makeText(this,"Service created",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"service destroyed",Toast.LENGTH_LONG).show();
    }
}
