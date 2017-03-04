package com.steve.whereyouat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsActivity extends ActionBarActivity implements
        OnItemClickListener
{
    private ListView		lv;
    private ContactDB		db;
    private ContactAdapter	adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        db = ContactDB.getInstance(this);

        lv = (ListView) findViewById(R.id.listview_contact);

        lv.setEmptyView(findViewById(R.id.empty));

        if (db.isContactHasData())
        {
            adapter = new ContactAdapter(this, db.getAllContact());
            lv.setAdapter(adapter);
        }
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add:
                Toast.makeText(this, "Add Contact", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AddContact.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3)
    {
        Bundle b = new Bundle();
        b.putInt("id", ((Contact) adapter.getItem(position)).getId());
        b.putString("name", ((Contact) adapter.getItem(position)).getName());
        b.putString("phone", ((Contact) adapter.getItem(position)).getNumber());
        b.putString("email", ((Contact) adapter.getItem(position)).getEmail());

        Intent i = new Intent(this, DetailContact.class);
        i.putExtras(b);
        startActivity(i);
        finish();

    }
}
