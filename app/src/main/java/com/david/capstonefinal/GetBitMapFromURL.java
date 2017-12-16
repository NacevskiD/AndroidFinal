package com.david.capstonefinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class GetBitMapFromURL extends AsyncTask<String, Void, Bitmap> {
    JSONObject jsonObject;
    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }


    protected void onPostExecute(JSONObject json) {
        if (json != null) {

            //Hide progress bar now task is done


            try {
                //If we make an invalid request, WU may return JSON describing our error. So check for that.
                if ( json.getJSONObject("response").has("error") ) {
                    Log.e("ERROR", "Error in response from WU " + json.getJSONObject("response")
                            .getJSONObject("error")
                            .getString("description"));
                    return;
                }

                //Hopefully we have JSON and it's not reporting an error. Try and read desired data
                String temp_f = json.getJSONObject("current_observation").getString("temp_f");

                //And update the TextView with the data.


            } catch (JSONException je) {
                Log.e("ERROR", "JSON parsing error", je);
            }
        }
    }

}