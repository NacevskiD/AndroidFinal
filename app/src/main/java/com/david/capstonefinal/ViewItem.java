package com.david.capstonefinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        TextView mTitle = (TextView) findViewById(R.id.title_view);
        TextView mReleaseDate = (TextView) findViewById(R.id.release_date_view);
        TextView mDesc = (TextView) findViewById(R.id.desc_view);
        TextView mRating = (TextView) findViewById(R.id.rating_view);
        TextView mActors = (TextView) findViewById(R.id.actors_view);
        RatingBar mCommRating = (RatingBar) findViewById(R.id.communityRating);
        final RatingBar mYourRating = (RatingBar) findViewById(R.id.yourRating);
        ImageView picture = (ImageView) findViewById(R.id.image_view);
        Button mAddToFav = (Button) findViewById(R.id.addToFavorites);




        mTitle.setText(getIntent().getStringExtra(MainActivity.VIEW_TITLE));
        mReleaseDate.setText(getIntent().getStringExtra(MainActivity.VIEW_DATE));
        mDesc.setText(getIntent().getStringExtra(MainActivity.VIEW_DESC));
        mRating.setText(getIntent().getStringExtra(MainActivity.VIEW_RATING));
        mActors.setText(getActors(getIntent().getStringArrayListExtra(MainActivity.VIEW_ACTORS)));
        float floatNumbe = getIntent().getFloatExtra(MainActivity.VIEW_AVERAGE,10)/2;
        mCommRating.setRating(floatNumbe);
        Bitmap bitmap = null;
        GetBitMapFromURL getBitMapFromURL = new GetBitMapFromURL();
        try {
            bitmap = getBitMapFromURL.execute(getIntent().getStringExtra(MainActivity.VIEW_PIC)).get();
        }catch (Exception e){
            Log.d("ERROR", " CANT GET BITMAP");
        }

        picture.setImageBitmap(bitmap);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               watch_video(getIntent().getStringExtra(MainActivity.VIEW_YTLINK));
            }
        });

        mAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float stars = mYourRating.getRating();
                Firebase firebase = new Firebase();
                FirebaseData firebaseData = new FirebaseData();

                firebaseData.setTitle(getIntent().getStringExtra(MainActivity.VIEW_TITLE));
                firebaseData.setDate(getIntent().getStringExtra(MainActivity.VIEW_DATE));
                firebaseData.setDesc(getIntent().getStringExtra(MainActivity.VIEW_DESC));
                firebaseData.setRating(getIntent().getStringExtra(MainActivity.VIEW_RATING));
                String actor =getActors(getIntent().getStringArrayListExtra(MainActivity.VIEW_ACTORS));
                firebaseData.setActors(actor);
                firebaseData.setCommRating(getIntent().getFloatExtra(MainActivity.VIEW_AVERAGE,10)/2);
                firebaseData.setPicture(getIntent().getStringExtra(MainActivity.VIEW_PIC));
                firebaseData.setYtLink(getIntent().getStringExtra(MainActivity.VIEW_YTLINK));
                firebaseData.setYourRating(stars);


                firebase.saveReview(firebaseData);

                LocalDB sqlite = new LocalDB(ViewItem.this);
                sqlite.addItem(getIntent().getStringExtra(MainActivity.VIEW_TITLE),(getIntent().getStringExtra(MainActivity.VIEW_DATE)),
                        (getIntent().getStringExtra(MainActivity.VIEW_DESC)),(getIntent().getStringExtra(MainActivity.VIEW_RATING)),actor,Float.toString((getIntent().getFloatExtra(MainActivity.VIEW_AVERAGE,10)/2)),
                        (getIntent().getStringExtra(MainActivity.VIEW_PIC)),(getIntent().getStringExtra(MainActivity.VIEW_YTLINK)),Float.toString(stars));


                finish();

            }
        });


    }

    void watch_video(String url)
    {
        Intent yt_play = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooser = Intent.createChooser(yt_play , "Open With");

        if (yt_play .resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    String getActors(ArrayList<String> list){
        String actors = "";
        for (String item:list){
            actors+=item +", ";
        }
        return actors;
    }
}
