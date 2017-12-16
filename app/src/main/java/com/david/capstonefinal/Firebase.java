package com.david.capstonefinal;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Firebase {

    ArrayList<FirebaseData> list;

    static final String ALL_REVIEWS_KEY = "all_reviews";

    private DatabaseReference reviewReference;

    Firebase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference("all_reviews");
        reviewReference = dbReference.child(ALL_REVIEWS_KEY);
    }

    void saveReview(FirebaseData data){

        DatabaseReference newReviewReference = reviewReference.push();
            newReviewReference.setValue(data);

    }


    void deleteItem(String name){
        reviewReference.child(name).removeValue();
    }





    ArrayList<FirebaseData> fetchAllReviews() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference("all_reviews");
        ArrayList<FirebaseData> allReviews;
        //Query getAllData = reviewReference.child(ALL_REVIEWS_KEY);
        // Provide the name of a key that all the children have; to sort by that key
        dbReference.child("-L0NNKfRNzokAVOA9BgA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "All reviews in name order: " + dataSnapshot.toString());

                list = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    FirebaseData review = childSnapshot.getValue(FirebaseData.class);
                    //allReviews.add(review);
                    list.add(review);
                }
               // mAllMoviesTV.setText(allReviews.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Firebase error fetching all reviews", databaseError.toException());
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return list;
    }

    ArrayList<FirebaseData> getAllData(){
        return list;
    }
}
