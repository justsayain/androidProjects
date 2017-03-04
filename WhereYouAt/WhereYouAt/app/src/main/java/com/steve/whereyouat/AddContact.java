package com.steve.whereyouat;

/**
 * Created by Johannes Chen on 06.12.2014.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends ActionBarActivity implements
        OnClickListener
{
    private EditText	name, email, phone;
    private Button		cancel, done;
    private ContactDB	db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        db = ContactDB.getInstance(this);

        name = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        phone = (EditText) findViewById(R.id.input_phone);
        cancel = (Button) findViewById(R.id.btn_cancel);
        done = (Button) findViewById(R.id.btn_done);

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == done)
        {
            String txtemail = "null";
            if (!TextUtils.isEmpty(name.getText().toString())
                    && !TextUtils.isEmpty(phone.getText().toString()))
            {
                if (!TextUtils.isEmpty(email.getText().toString()))
                    txtemail = email.getText().toString();

                db.addContact(new Contact(name.getText().toString(), phone
                        .getText().toString(), txtemail));

                startActivity(new Intent(this, ContactsActivity.class));
                finish();
            } else
            {
                Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (v == cancel)
        {

            startActivity(new Intent(this, ContactsActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, ContactsActivity.class));
    }
}