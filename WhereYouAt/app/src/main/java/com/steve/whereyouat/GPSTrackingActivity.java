package com.steve.whereyouat;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Steve on 12/8/2014.
 */
public class GPSTrackingActivity extends Activity {
    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getlocation);
        // create class object
        gps = new GPSTracker(GPSTrackingActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Got Your Location\nCopied to Clipboard\nPaste Location", Toast.LENGTH_LONG).show();
            String message = Double.toString(latitude)+","+Double.toString(longitude);
            TextView text= (TextView)findViewById(R.id.locationTextView);
            text.setText(message);
            ((ClipboardManager)getSystemService(this.CLIPBOARD_SERVICE)).setText(message);
            /*setContentView(text);*/
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        btnShowLocation = (Button) findViewById(R.id.sendLocationButton);

       // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(GPSTrackingActivity.this,ContactsActivity.class);
                startActivity(intent);

            }
        });
    }
}
