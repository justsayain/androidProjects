package com.steve.whereyouat;

/**
 * Created by Johannes Chen on 06.12.2014.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter
{
    static class Holder
    {
        TextView	name;
        ImageView	thumb;
    }

    private LayoutInflater	inflater;
    private List<Contact>	listContacts;

    public ContactAdapter(Context context, List<Contact> listContacts)
    {
        inflater = LayoutInflater.from(context);
        this.listContacts = listContacts;
    }

    @Override
    public int getCount()
    {
        return listContacts.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listContacts.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.single_contact, null);

            holder = new Holder();
            holder.name = (TextView) convertView
                    .findViewById(R.id.name_contact);
            holder.thumb = (ImageView) convertView
                    .findViewById(R.id.thumb_contact);

            convertView.setTag(holder);
        } else
        {
            holder = (Holder) convertView.getTag();
        }

        holder.name.setText(listContacts.get(position).getName());
        holder.thumb.setBackgroundResource(R.drawable.ic_people);

        return convertView;
    }

}
