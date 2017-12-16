package com.david.capstonefinal;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class GetJSON extends AsyncTask<String, Void, JSONObject> {
    JSONObject jsonObject;
    @Override
    protected JSONObject doInBackground(String... urls) {

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();

            //Wrap in InputStreamReader, and then wrap that in a BufferedReader to read line-by-line
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseStream));

            // Read stream into String. Use StringBuilder to put multiple lines together.
            // Read lines in a loop until the end of the stream.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            //and turn the StringBuilder into a String.
            String responseString = builder.toString();

            Log.d("OUTPUT", responseString);

            //And then parse this String into a JSON object.
            // Return this JSONOObject, and it will be delivered
            //to the onPostExecute method. onPostExecute method is called automatically.
            JSONObject json = new JSONObject(responseString);
            jsonObject = json;
            return json;

        } catch (Exception e) {
            //TODO handle with more granularity. At least 3 different exceptions could be thrown here
            Log.e("ERROR", "Error fetching temperature data", e);
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