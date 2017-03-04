package com.steve.whereyouat;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Johannes Chen on 06.12.2014.
 */
public class SendMsg extends Activity {
    EditText msg;
    TextView number;
    Button sms;
    String no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        number = (TextView)findViewById(R.id.textView);
        msg = (EditText)findViewById(R.id.editText2);

        no = getIntent().getExtras().getString("phone");
        number.setText(no);

        sms = (Button)findViewById(R.id.msg);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });
    }
    protected void sendSMS() {
        String message = msg.getText().toString();

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(no, null, message, null, null);
        Toast.makeText(getApplicationContext(), "Send Successfully", Toast.LENGTH_LONG).show();
    }


}
