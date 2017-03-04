package com.steve.alertdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Steve on 10/7/2014.
 */
public class AirplaneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"airplane mode changed",Toast.LENGTH_LONG).show();
    }
}
