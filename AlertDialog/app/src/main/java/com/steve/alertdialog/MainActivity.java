package com.steve.alertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.BroadcastReceiver;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1=(Button)findViewById(R.id.btn1);
        mButton2=(Button)findViewById(R.id.btn2);
        mButton3=(Button)findViewById(R.id.btn3);
        mButton4=(Button)findViewById(R.id.btn4);
        mButton5=(Button)findViewById(R.id.btn5);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                showOneButtonDialog();
                break;
            case R.id.btn2:
                showTwoButtonDialog();
                break;
            case R.id.btn3:
                showThreeButtonDialog();
                break;
            case R.id.btn4:
                showListDialog();
                break;
            case R.id.btn5:
                showRadioButtonDialog();
                break;

        }
    }
    public void showOneButtonDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Game Over");
        dialogBuilder.setMessage("You Loose");
        dialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"1 button notification: Game Over",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void showTwoButtonDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Delete Confirm");
        dialogBuilder.setMessage("You wanna delete this");
        dialogBuilder.setPositiveButton("yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"You said yes",Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"You clicked no",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void showThreeButtonDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Broadcast");
        dialogBuilder.setMessage("Did you want to send Broadcast signal?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"you said yes",Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"you said no",Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"nothing happened",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void showListDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[]colors = {"Red","Blue","Green","Yellow"};
        dialogBuilder.setTitle("choose color");
        dialogBuilder.setItems(colors, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(), colors[which],Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void showRadioButtonDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String []radio={"AM","FM"};
        dialogBuilder.setTitle("select radio frequency");
        dialogBuilder.setSingleChoiceItems(radio,-1,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),radio[which],Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void sendBroadcast(View v){
        Intent intent = new Intent();
        intent.setAction("com.steve.alertdialog.CustomMessage");
        sendBroadcast(intent);
    }
}
