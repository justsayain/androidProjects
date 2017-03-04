package com.steve.broadcastreceivers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import android.content.BroadcastReceiver;
import android.widget.Toast;


public class MyActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"custom",Toast.LENGTH_LONG).show();
    }
}
