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

public class FavoritesViewItem extends AppCompatActivity {

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
        RatingBar mSetYourRating = (RatingBar) findViewById(R.id.yourRating);




        mTitle.setText(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_TITLE));
        mReleaseDate.setText(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_DATE));
        mDesc.setText(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_DESC));
        mRating.setText(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_RATING));
        mActors.setText(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_ACTORS));
        mCommRating.setRating(getIntent().getFloatExtra(FavoritesActivity.FAV_VIEW_AVERAGE,10));
        mSetYourRating.setRating(getIntent().getFloatExtra(FavoritesActivity.FAV_YOUR_RATING,10));

        Bitmap bitmap = null;
        GetBitMapFromURL getBitMapFromURL = new GetBitMapFromURL();
        try {
            bitmap = getBitMapFromURL.execute(getIntent().getStringExtra(FavoritesActivity.FAV_VIEW_PIC)).get();
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
