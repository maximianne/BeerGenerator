package com.example.homework2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;

public class Beer implements Parcelable {
    private String beerName;
    private String beerDescription;
    private String BAC;
    private String firstBrewed;
    private String brewerTips;
    private ArrayList<String> foodPairings;
    private String image_url;
    //added
    private String imageStatic_url;
    private String imageSelect_url;

    //added 2
    private boolean Fav;

    public Beer(String beerName, String beerDescription, String BAC, String firstBrewed,
                String brewerTips, ArrayList<String> foodPairings, String image_url, String imageStatic_url, String imageSelect_url){
        this.beerName=beerName;
        this.beerDescription=beerDescription;
        this.BAC=BAC;
        this.firstBrewed=firstBrewed;
        this.brewerTips=brewerTips;
        this.foodPairings=new ArrayList<>();
        this.image_url=image_url;
        this.imageStatic_url=imageStatic_url;
        this.imageSelect_url=imageSelect_url;
        this.Fav=true;
    }

    public Beer(Parcel in) {
        this.beerName=in.readString();
        this.beerDescription=in.readString();
        this.BAC=in.readString();
        this.firstBrewed=in.readString();
        this.brewerTips=in.readString();
        this.foodPairings= in.createStringArrayList();
        this.image_url=in.readString();
        this.imageStatic_url= in.readString();
        this.imageSelect_url=in.readString();
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerDescription() {
        return beerDescription;
    }

    public void setBeerDescription(String beerDescription) {
        this.beerDescription = beerDescription;
    }

    public String getBAC() {
        return BAC;
    }

    public void setBAC(String BAC) {
        this.BAC = BAC;
    }

    public String getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed = firstBrewed;
    }

    public String getBrewerTips() {
        return brewerTips;
    }

    public void setBrewerTips(String brewerTips) {
        this.brewerTips = brewerTips;
    }

    public ArrayList<String> getFoodPairings() {
        return foodPairings;
    }

    public void setFoodPairings(ArrayList<String> foodPairings) {
        this.foodPairings = foodPairings;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(beerName);
        dest.writeString(beerDescription);
        dest.writeString(BAC);
        dest.writeString(firstBrewed);
        dest.writeString(brewerTips);
        dest.writeList(foodPairings);
        dest.writeString(image_url);
        dest.writeString(imageStatic_url);
        dest.writeString(imageSelect_url);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public String getImageStatic_url() {
        return imageStatic_url;
    }

    public void setImageStatic_url(String imageStatic_url) {
        this.imageStatic_url = imageStatic_url;
    }

    public String getImageSelect_url() {
        return imageSelect_url;
    }

    public void setImageSelect_url(String imageSelect_url) {
        this.imageSelect_url = imageSelect_url;
    }

    public boolean isFav() {
        return Fav;
    }

    public void setFav(boolean fav) {
        Fav = fav;
    }
}
