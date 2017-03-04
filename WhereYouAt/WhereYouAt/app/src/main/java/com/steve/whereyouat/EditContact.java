package com.steve.whereyouat;

/**
 * Created by Johannes Chen on 06.12.2014.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditContact extends ActionBarActivity implements OnClickListener
{
    private EditText	name, email, phone;
    private Button		cancel, done;
    private ContactDB	db;
    private int			id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        getSupportActionBar().setTitle("Edit Contact");

        db = ContactDB.getInstance(this);

        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_phone);
        cancel = (Button) findViewById(R.id.btn_cancel);
        done = (Button) findViewById(R.id.btn_done);

        Bundle b = getIntent().getExtras();
        if (b != null)
        {
            id = b.getInt("id");
            name.setText(b.getString("name"));
            phone.setText(b.getString("phone"));
            email.setText(b.getString("email"));
        }

        cancel.setOnClickListener(this);
        done.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if (v == done)
        {
            db.editContact(new Contact(id, name.getText().toString(), phone
                    .getText().toString(), email.getText().toString()));
            startActivity(new Intent(this, ContactsActivity.class));
            finish();

        } else if (v == cancel)
        {
            startActivity(new Intent(this, ContactsActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, ContactsActivity.class));
        finish();
    }
}
