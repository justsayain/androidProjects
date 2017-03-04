package com.steve.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Steve on 10/3/2014.
 */
public class SimpleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context rcvContext, Intent rcvIntent){
        String action = rcvIntent.getAction();
        if(action.equals(Intent.ACTION_CAMERA_BUTTON)){
            rcvContext.startService(new Intent(rcvContext, SimpleService2.class));
        }
    }
}
