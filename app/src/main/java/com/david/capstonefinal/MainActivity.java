package com.david.capstonefinal;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mSearchButton;
    ListView mItemList;
    ArrayList<ListItem> mItems = new ArrayList<ListItem>();
    CustomRowAdapter mArrayAdapter;

    final static String VIEW_TITLE = "title";
    final static String VIEW_DESC = "desc";
    final static String VIEW_DATE = "date";
    final static String VIEW_PIC = "pic";
    final static String VIEW_YTLINK = "yt";
    final static String VIEW_AVERAGE = "average";
    final static String VIEW_ACTORS = "actor";
    final static String VIEW_RATING = "rating";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mFavorites = (Button) findViewById(R.id.show_favorites);
        mSearchButton = (Button) findViewById(R.id.search_button);
        final EditText mQuery = (EditText) findViewById(R.id.search_text);
        mItemList = (ListView) findViewById(R.id.item_list);


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = mQuery.getText().toString();
                if (query.length() == 0){

                }
                MovieAPI movieAPI = new MovieAPI(formatQuery(query),getKeyFromRawResource(MainActivity.this,R.raw.key));
                try{
                    mItems = movieAPI.getMovieList();
                }catch (Exception ex){
                    Log.d("ERROR"," : error getting movie list " + ex.getMessage() + " bla " + ex.getCause());
                }
               // this,R.layout.list_item,mItems;
                mArrayAdapter = new CustomRowAdapter(MainActivity.this,R.layout.list_item,mItems);
                mItemList.setAdapter(mArrayAdapter);
                mArrayAdapter.notifyDataSetChanged();
            }
        });

        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoritesList = new Intent(MainActivity.this,FavoritesActivity.class);
                startActivity(favoritesList);
            }
        });

        mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem item = mItems.get(i);
                Intent viewItem = new Intent(MainActivity.this,ViewItem.class);
                viewItem.putExtra(VIEW_TITLE,item.getTitle());
                viewItem.putExtra(VIEW_DESC,item.getDesc());
                viewItem.putExtra(VIEW_DATE,item.getCurrentDateTimeString());
                viewItem.putExtra(VIEW_PIC,item.getPic());
                viewItem.putExtra(VIEW_YTLINK,item.getYtLink());
                viewItem.putExtra(VIEW_AVERAGE,item.getStars());
                viewItem.putExtra(VIEW_ACTORS,item.getActors());
                viewItem.putExtra(VIEW_RATING,item.getRating());
                startActivity(viewItem);
            }

        });
        }

        String formatQuery (String query){
        query = query.replaceAll("\\s", "%20");
        return query;
        }

    String getKeyFromRawResource(Context context, int rawResource){
        InputStream keyStream = context.getResources().openRawResource(rawResource);
        BufferedReader keyStreamReader = new BufferedReader(new InputStreamReader(keyStream));

        try {
            String key = keyStreamReader.readLine();
            return key;
        }catch (IOException ioe){
            return null;
        }
    }
    }

