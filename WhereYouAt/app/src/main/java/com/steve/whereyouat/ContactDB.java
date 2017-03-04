package com.steve.whereyouat;

/**
 * Created by Johannes Chen on 06.12.2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactDB extends SQLiteOpenHelper
{
    private static final String		DB_NAME		= "db_contact";
    private static final int		DB_VERSION	= 1;

    private static ContactDB		dbInstance;
    private static SQLiteDatabase	db;

    private static final String		TB_CONTACT	= "tb_contact";

    interface COLUMN
    {
        String	id		= "_id";
        String	name	= "name";
        String	number	= "number";
        String	email	= "email";
    }

    private static final String	SQL_CREATE_CONTACT	= "CREATE TABLE "
            + TB_CONTACT
            + "( "
            + COLUMN.id
            + " integer primary key autoincrement not null, "
            + COLUMN.name
            + " text, "
            + COLUMN.email
            + " text, "
            + COLUMN.number
            + " text)";

    private ContactDB(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ContactDB getInstance(Context context)
    {
        if (dbInstance == null)
        {
            dbInstance = new ContactDB(context);
            db = dbInstance.getWritableDatabase();
        }
        return dbInstance;
    }

    @Override
    public synchronized void close()
    {
        super.close();
        if (dbInstance != null)
            dbInstance.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        TRACE("ContactDB", "" + SQL_CREATE_CONTACT);
        db.execSQL(SQL_CREATE_CONTACT);

    }

    public static void TRACE(String tag, String msg)
    {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST " + TB_CONTACT);
        onCreate(db);
    }

    public List<Contact> getAllContact()
    {
        List<Contact> listContacts = new ArrayList<Contact>();

        Cursor cursor = db.query(TB_CONTACT, new String[] { COLUMN.id,
                        COLUMN.name, COLUMN.email, COLUMN.number }, null, null, null,
                null, COLUMN.name);
        cursor.moveToFirst();
        do
        {
            listContacts
                    .add(new Contact(cursor.getInt(cursor
                            .getColumnIndexOrThrow(COLUMN.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(COLUMN.name)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(COLUMN.number)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(COLUMN.email))));
        } while (cursor.moveToNext());
        return listContacts;
    }

    public boolean isContactHasData()
    {
        Cursor cursor = db.query(TB_CONTACT, new String[] { COLUMN.id }, null,
                null, null, null, null);
        return (cursor.getCount() > 0) ? true : false;
    }

    public boolean addContact(Contact contact)
    {
        ContentValues values = new ContentValues();

        values.put(COLUMN.name, contact.getName());
        values.put(COLUMN.email, contact.getEmail());
        values.put(COLUMN.number, contact.getNumber());

        return ((db.insert(TB_CONTACT, null, values)) != -1) ? true : false;
    }

    public boolean editContact(Contact contact)
    {
        ContentValues values = new ContentValues();

        values.put(COLUMN.name, contact.getName());
        values.put(COLUMN.email, contact.getEmail());
        values.put(COLUMN.number, contact.getNumber());

        return ((db.update(TB_CONTACT, values, COLUMN.id + "=?",
                new String[] { String.valueOf(contact.getId()) })) == 1) ? true
                : false;
    }

    public void deleteContact(int id)
    {
        db.delete(TB_CONTACT, COLUMN.id + "=?",
                new String[] { String.valueOf(id) });
    }
}
