package com.david.capstonefinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {
    String key = "";
    String baseURL = "https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s";
    String query;
    String baseDetailURL = "https://api.themoviedb.org/3/movie/%d?api_key=%s&language=en-US";
    ArrayList<ListItem> movieList;
    String baseYTurl = "https://api.themoviedb.org/3/movie/%s/videos?api_key=%s&language=en-US";
    String recMovie = "https://api.themoviedb.org/3/movie/%s/recommendations?api_key=%s&language=en";




    MovieAPI(String query,String key){
        this.query = query;
        this.key = key;
    }

    public ArrayList<ListItem> getMovieList() throws Exception{


        String url = String.format(baseURL, key,query);

        //RequestMinneapolisCurrentTemp jsonObject2 = new RequestMinneapolisCurrentTemp();
        //jsonObject2.execute(url);

        JSONObject testJson;

       GetJSON jsonObject = new GetJSON();
       testJson = jsonObject.execute(url).get();

        // JSON processing here


       int totalResults = testJson.getInt("total_results");
        JSONArray items = testJson.getJSONArray("results");
        int index = 0;

        movieList= new ArrayList<ListItem>();

        while (totalResults > index) {
            JSONObject movie = items.getJSONObject(index);
            System.out.println(movie);
            String title = movie.getString("title");
            String adult = getAdult(movie.getBoolean("adult"));
            String description = movie.getString("overview");
            int id = movie.getInt("id");
            String date = movie.getString("release_date");
            String image = "";
            try {
                image = imageURL(movie.getString("poster_path"));
            }catch (Exception npe){
                image = "none";
            }


            ListItem movie1 = new ListItem();
            movie1.setTitle(title);
            movie1.setDesc(description);
            movie1.setId(id);
            movie1.setCurrentDateTimeString(date);
            movie1.setPic(image);
            movie1.setYtLink(ytURL(id));
            List details = new ArrayList();
            details = getMovieDetails(id);
            movie1.setImdb(details.get(0).toString());
            movie1.setTagLine(details.get(1).toString());
            movie1.setStars(Float.parseFloat(details.get(2).toString()));
            movie1.setVoteCount(Integer.parseInt(details.get(3).toString()));
            movie1.setActors(getActors(id));
            movie1.setRating(adult);

            movieList.add(movie1);

            index++;
        }

        return movieList;
    }

    List getMovieDetails(int id) throws Exception{
        String url = String.format(baseDetailURL, id,key);


        JSONObject testJson;

        GetJSON jsonObject = new GetJSON();
        testJson = jsonObject.execute(url).get();




        String imdb = testJson.getString("imdb_id");
        String tagline = testJson.getString("tagline");
        double rating = testJson.getDouble("vote_average");
        int voteCount = testJson.getInt("vote_count");

        List details = new ArrayList();
        details.add(imdb);
        details.add(tagline);
        details.add(Double.toString(rating));
        details.add(voteCount);

        String test = details.get(0).toString();

        System.out.println(imdb);
        System.out.println(tagline);
        System.out.println(rating);

        return details;

    }

    String ytURL(int id) throws Exception{

        String ytURL = String.format(baseYTurl,id,key);

        JSONObject testJson;

        GetJSON jsonObject = new GetJSON();
        testJson = jsonObject.execute(ytURL).get();


        JSONArray items = testJson.getJSONArray("results");
        System.out.println(items);

        int index = 0;


        JSONObject movie = null;
        String youtube = "none";

        if (items.length()>0) {
            movie = items.getJSONObject(index);
            System.out.println(movie);
            String ytID = movie.getString("key");

            youtube = getYTurl(ytID);
        }


        return youtube;


    }

    String imageURL(String path){
        if(path == null){
            String defaultImageURL = "https://d30y9cdsu7xlg0.cloudfront.net/png/45447-200.png";
            return defaultImageURL;
        }else {
            String baseURL = "https://image.tmdb.org/t/p/w500%s";
            String picURL = String.format(baseURL, path);
            return picURL;
        }

    }

    String getYTurl(String id){
        String baseURL = "https://www.youtube.com/watch?v=%s";
        String yt = String.format(baseURL,id);
        return yt;
    }

    ArrayList<String> getActors(int id) throws Exception{
        String baseActorURL = "https://api.themoviedb.org/3/movie/%s/credits?api_key=%s";
        String actorURL = String.format(baseActorURL,id,key);
        JSONObject testJson;

        GetJSON jsonObject = new GetJSON();
        testJson = jsonObject.execute(actorURL).get();


        JSONArray items = testJson.getJSONArray("cast");
        System.out.println(items);

        ArrayList<String> actors = new <String> ArrayList();



        JSONObject movie = null;

        for (int index = 0;index < 5;index++) {
            String name;
            try {
                movie = items.getJSONObject(index);
                name = movie.getString("name");
            }catch (Exception exe){
                name = "None";
            }



            actors.add(name);
        }


        return actors;


    }

    String getAdult(boolean adult){
        if (adult){
            return "Rating: Adult";
        }
        else {
            return "Rating: PG-13";
        }
    }






    }








//return title;



