package com.steve.whereyouat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailContact extends ActionBarActivity implements
        OnClickListener
{

    private TextView	phone, email;
    private int			id;
    private ImageView	call, btnEmail;
    private ContactDB	db;
    private Button      msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_contact);

        db = ContactDB.getInstance(this);
        msg = (Button)findViewById(R.id.button);

        phone = (TextView) findViewById(R.id.txt_phone_number);
        email = (TextView) findViewById(R.id.txt_email);
        call = (ImageView) findViewById(R.id.btn_call);
        btnEmail = (ImageView) findViewById(R.id.btn_email);

        Bundle b = getIntent().getExtras();
        if (b != null)
        {
            id = b.getInt("id");
            phone.setText(b.getString("phone"));
            getSupportActionBar().setTitle(b.getString("name"));
            if (!b.getString("email").equals("null"))
            {
                email.setText(b.getString("email"));
            } else
            {
                email.setText("");
            }

        }
        msg.setOnClickListener(this);
        call.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_edit:
                Bundle b = new Bundle();
                b.putInt("id", id);
                b.putString("name", getSupportActionBar().getTitle().toString());
                b.putString("email", email.getText().toString());
                b.putString("phone", phone.getText().toString());

                Intent i = new Intent(this, EditContact.class);
                i.putExtras(b);
                startActivity(i);
                finish();
                return true;
            case R.id.action_delete:
                showSettingsAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onClick(View v)
    {
        if (v == btnEmail)
        {
            if (!email.getText().toString().equals(""))
            {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", email.getText().toString(),
                                null));

                startActivity(Intent
                        .createChooser(emailIntent, "Send email..."));

            }

        } else if (v == call)
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
            startActivity(callIntent);

        } else if (v.getId() == msg.getId())
        {
            Intent msgIntent = new Intent(this, SendMsg.class);
            msgIntent.putExtra("phone", phone.getText().toString());
            startActivity(msgIntent);
        }

    }

    private void showSettingsAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Delete");
        alert.setMessage("Friend will be removed");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                db.deleteContact(id);
                startActivity(new Intent(DetailContact.this,
                        ContactsActivity.class));
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alert.show();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(DetailContact.this, ContactsActivity.class));

        finish();
    }

}
