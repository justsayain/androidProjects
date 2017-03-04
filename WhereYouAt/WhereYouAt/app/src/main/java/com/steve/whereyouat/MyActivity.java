package com.steve.whereyouat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity extends FragmentActivity
        implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener{
    // Global constants
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
            /*
             * If the result code is Activity.RESULT_OK, try
             * to connect again
             */
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    /*
                     * Try the request again
                     */

                        break;
                }

        }

    }
    
    // Global variable to hold the currenr
    public Location mCurrentLocation;
    public double myLongitude=0, myLatitude=0,myLocationLatitude=0,myLocationLongitude=0;
    private GPSTracker gps;
    private Button locBtn, sndBtn, settingsBtn,goBtn,sendRequestBtn;
    public GoogleMap myMap;
    private EditText message;
    LocationClient myLocationClient;
    String myMessage;
    public LatLng CSUN = new LatLng(34.241357, -118.529314);
    //public LatLng myLocation = new LatLng(gps.getLatitude(),gps.getLongitude());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        checkGPS();
        locBtn=(Button)findViewById(R.id.locationButton);
        sendRequestBtn=(Button)findViewById(R.id.sendRequestButton);
        settingsBtn=(Button)findViewById(R.id.settingsButton);
        goBtn=(Button)findViewById(R.id.goButton);
        myMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if(myMap !=null){
            myMap.setMyLocationEnabled(true);
        }
        myLocationClient = new LocationClient(getApplicationContext(),this,this);
            if(myLocationClient !=null){
            myLocationClient.connect();
        }
        //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.241357,-118.529314),15));
//       myLocationLatitude = gps.getLatitude();
//       myLocationLongitude = gps.getLongitude();
       //LatLng myLocationStart=  new LatLng(myLocationLatitude,myLocationLongitude);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CSUN, 15),3000,null);
        myMap.addMarker(new MarkerOptions().position(CSUN).title("You Are Here"));
        locBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        sendRequestBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);

        //Put lat, long
        //EditText latlngText=(EditText)findViewById(R.id.editLocationText);

        message= (EditText)findViewById(R.id.editLocationText);
//        myMessage= message.getText().toString();
        goBtn.setOnClickListener(goBtnListener);
    }
    String split1;
    String split2;
    private View.OnClickListener goBtnListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myMessage= message.getText().toString();
            if (myMessage.contains(",")) {
                // Split it.
                String[]split = myMessage.split(",",2);
                split1= split[0];//before ,
                split2= split[1];//after the comma
                double givenLatitude=Double.parseDouble(split1);
                double givenLongitude=Double.parseDouble(split2);
                LatLng personsLocation= new LatLng(givenLatitude,givenLongitude);
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(personsLocation,15),3000,null);
                myMap.addMarker(new MarkerOptions().position(personsLocation).title("Friend is here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else {
                AlertDialog.Builder dialog= new AlertDialog.Builder(MyActivity.this);
                dialog.setTitle("Error");
                dialog.setMessage("Your Syntax may be wrong \n Please Put Latitude, Longitude");
                dialog.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();

                    }
                });

                Toast.makeText(getApplicationContext(),"Cannot Get Location",Toast.LENGTH_LONG).show();
            }


        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
        }} else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode) {
        Toast.makeText(getApplicationContext(),
                "ERROR CANNOT GET RESOLUTION",Toast.LENGTH_LONG).show();
    }

    private  static final LocationRequest REQUEST= LocationRequest.create()
            .setInterval(5000)
            .setFastestInterval(16)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    @Override
    public void onConnected(Bundle bundle) {
        myLocationClient.requestLocationUpdates(REQUEST,this);
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.locationButton:
                getLocation();
                break;
            case R.id.sendRequestButton:
                sendRequest();
                break;
            case R.id.settingsButton:
                showSettings();
                break;
        }
    }
    public void getLocation(){
        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            myLatitude= gps.getLatitude();
            myLongitude= gps.getLongitude();
            Toast.makeText(getApplicationContext(),"Got Your Location",Toast.LENGTH_LONG).show();

        }
        else {
            //cannot get location
            //GPS or Network is not enabled
            //asks user to enable network in settings
            gps.showSettingsAlert();
        }
        Intent intent = new Intent(this, GPSTrackingActivity.class);
        startActivity(intent);
    }

    public void sendRequest(){
        Intent intent= new Intent(this,ContactsActivity.class);
        startActivity(intent);
    }
    public void showSettings(){
        AlertDialog.Builder settingsDialog = new AlertDialog.Builder(this);
        final String[]options = {"Friend List", "Coming Soon", "Exit"};
        settingsDialog.setTitle("Select");
        settingsDialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),options[which],Toast.LENGTH_LONG).show();
                if(options[0]==options[which]){
                    Intent intent =  new Intent(MyActivity.this,ContactsActivity.class);
                    startActivity(intent);
                }
                else if(options[1]==options[which]){
                    Toast.makeText(getApplicationContext(),options[which],Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent= new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }
        });
        AlertDialog alertDialog=settingsDialog.create();
        alertDialog.show();
    }

    public void checkGPS(){
        LocationManager service= (LocationManager)getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //check if it is enabled or not and if it not send user to settings
        if(!enabled){
            Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    /*Geocoder geo = new Geocoder(getBaseContext());
    List<Address> gotAdressess = null;
    try{
        gotAdressess = geo.getFromLocation()
    }catch(IOException e){
        e.printStackTrace();
    }
    Address address = (Address)gotAdressess.get(0);
    LatLng latlng= new LatLng(address.getLatitude(), address.getLongitude());
    String properAddress = String.format("%s,%s",address.getMaxAddressLineIndex()>0?address.getAddressLine(0):"",address.getCountryName());
*/
    @Override
    protected void onStart(){
        super.onStart();
        //connect the client
        myLocationClient.connect();
    }
    @Override
    protected void onStop(){
        myLocationClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
