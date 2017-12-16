package com.david.capstonefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FavoritesActivity extends AppCompatActivity {
    ArrayList<FirebaseData> mItems;
    ArrayAdapter mArrayAdapter2;

    final static String FAV_VIEW_TITLE = "title";
    final static String FAV_VIEW_DESC = "desc";
    final static String FAV_VIEW_DATE = "date";
    final static String FAV_VIEW_PIC = "pic";
    final static String FAV_VIEW_YTLINK = "yt";
    final static String FAV_VIEW_AVERAGE = "average";
    final static String FAV_VIEW_ACTORS = "actor";
    final static String FAV_VIEW_RATING = "rating";
    final static String FAV_YOUR_RATING = "urrating";
    static final String ALL_REVIEWS_KEY = "all_reviews";
    ListView mItemList;
    GenericTypeIndicator<HashMap<String,FirebaseData>> data;
    LocalDB db;

    private DatabaseReference reviewsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        Firebase firebase = new Firebase();
        db = new LocalDB(this);
        mItems = new ArrayList<>();
        mItems = db.fetchAllProducts();


        mItemList = (ListView) findViewById(R.id.item_list);

        //ArrayList<FirebaseData> mListItems = new ArrayList<FirebaseData>();

       mArrayAdapter2 = new CustomRowAdapter2(this,R.layout.list_item,mItems);
       mItemList.setAdapter(mArrayAdapter2);

       mArrayAdapter2.notifyDataSetChanged();

       mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               FirebaseData data = mItems.get(i);
               Intent intent = new Intent(FavoritesActivity.this,FavoritesViewItem.class);
               intent.putExtra(FAV_VIEW_TITLE,data.getTitle());
               intent.putExtra(FAV_VIEW_DESC,data.getDesc());
               intent.putExtra(FAV_VIEW_DATE,data.getDate());
               intent.putExtra(FAV_VIEW_PIC,data.getPicture());
               intent.putExtra(FAV_VIEW_YTLINK,data.getYtLink());
               intent.putExtra(FAV_VIEW_AVERAGE,data.getCommRating());
               intent.putExtra(FAV_VIEW_ACTORS,data.getActors());
               intent.putExtra(FAV_VIEW_RATING,data.getRating());
               intent.putExtra(FAV_YOUR_RATING,data.getYourRating());
               startActivity(intent);

           }
       });

       mItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
             //  FirebaseData data = mItems.get(i);
               //firebase.deleteItem(data.getTitle());
               //ArrayAdapter2.notifyDataSetChanged();
               FirebaseData selected = mItems.get(i);
               db.deleteItem(selected.getDate());
               mArrayAdapter2.notifyDataSetChanged();
               return true;
           }
       });


    }


}
