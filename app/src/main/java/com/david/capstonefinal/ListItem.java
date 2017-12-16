package com.david.capstonefinal;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListItem {

    String currentDateTimeString;
    String pic;
    String desc;
    String title;
    float stars;
    int id;
    String ytLink;
    ArrayList<String> actors;
    String imdb;
    int voteCount;
    String tagLine;
    String rating;


    ListItem(){
    }


    public ListItem(String title,String desc, String pic, float stars,int id,String currentDateTimeString, String ytLink,
                        String imdb,int voteCount,String tagLine, ArrayList<String> actors,String rating){
        this.title = title;
        this.desc = desc;
        this.pic = pic;
        this.stars = stars;
        this.id = id;
        this.ytLink = ytLink;
        this.currentDateTimeString = currentDateTimeString;
        this.imdb = imdb;
        this.voteCount = voteCount;
        this.tagLine = tagLine;
        this.actors = actors;
        this.rating = rating;

    }

    public String getCurrentDateTimeString() {
        return currentDateTimeString;
    }

    public String getYtLink() {
        return ytLink;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setYtLink(String ytLink) {
        this.ytLink = ytLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setCurrentDateTimeString(String currentDateTimeString) {
        this.currentDateTimeString = currentDateTimeString;
    }

    public void setPic(String pic) {
        this.pic = pic;

    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
