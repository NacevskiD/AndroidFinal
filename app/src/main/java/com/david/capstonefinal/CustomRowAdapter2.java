package com.david.capstonefinal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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


public class CustomRowAdapter2 extends ArrayAdapter<FirebaseData> {
    Context mContext;
    int layoutResourceId;
    ArrayList<FirebaseData> data = null;
    //ListItem data[] = null;
    public CustomRowAdapter2(Context context,int layoutResourceId, ArrayList <FirebaseData> data){
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

        FirebaseData listItem = data.get(position);
        String url = listItem.picture;
        try {
            bitmap = getBitMapFromURL.execute(listItem.picture).get();
        }catch (Exception e){
            Log.d("ERROR", " CANT GET BITMAP");
        }
        holder.title.setText(listItem.title);
        holder.image.setImageBitmap(bitmap);
        holder.releaseDate.setText(listItem.date);
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

