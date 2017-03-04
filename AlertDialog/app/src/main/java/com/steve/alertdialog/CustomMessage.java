package com.steve.alertdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Steve on 10/7/2014.
 */
public class CustomMessage extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"custom receiver message",Toast.LENGTH_LONG).show();
    }
}
