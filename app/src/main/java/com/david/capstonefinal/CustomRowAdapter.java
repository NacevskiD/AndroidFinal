package com.david.capstonefinal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.david.capstonefinal.ListItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by David on 11/29/2017.
 */

public class CustomRowAdapter extends ArrayAdapter<ListItem> {
    Context mContext;
    int layoutResourceId;
    ArrayList<ListItem> data = null;
    //ListItem data[] = null;
    public CustomRowAdapter(Context context,int layoutResourceId, ArrayList <ListItem> data){
        super(context,layoutResourceId,data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        DataHolder holder = null;
        if (row == null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            holder = new DataHolder();
            holder.count = (TextView) row.findViewById(R.id.count);
            holder.image = (ImageView) row.findViewById(R.id.image_view);
            holder.title = (TextView) row.findViewById(R.id.title_view);
            holder.releaseDate = (TextView) row.findViewById(R.id.release_date_view);
            holder.rating = (TextView) row.findViewById(R.id.rating_view);

            row.setTag(holder);
        }
        else {
            holder = (DataHolder)row.getTag();
        }
        Bitmap bitmap = null;
        GetBitMapFromURL getBitMapFromURL = new GetBitMapFromURL();

        ListItem listItem = data.get(position);
        try {
            bitmap = getBitMapFromURL.execute(listItem.pic).get();
        }catch (Exception e){
            Log.d("ERROR", " CANT GET BITMAP");
        }
        holder.title.setText(listItem.title);
        holder.image.setImageBitmap(bitmap);
        holder.releaseDate.setText(listItem.currentDateTimeString);
        holder.rating.setText(listItem.rating);
        String pos = "#" + position;
        holder.count.setText(pos);

        return row;
    }

    static class DataHolder{
        ImageView image;
        TextView title;
        TextView releaseDate;
        TextView rating;
        TextView count;
    }


}
