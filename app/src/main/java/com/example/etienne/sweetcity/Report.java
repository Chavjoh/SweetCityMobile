package com.example.etienne.sweetcity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

/**
 * Flatseeker MobOp
 * HES-SO
 * Created by Gregori BURRI, Etienne FRANK on 23.11.2014.
 */


public class Report implements Parcelable {



    public enum Tag {
        TRASH,ROAD,LAMPPOST
    }
    
    
    public final static String INTENT_TAG = "report";
    final private String pictureFileName;
    final private LatLng latlng;
    final private Set<Tag> tags = new HashSet<Tag>();
    
    public Report(String pictureFileName, LatLng latlng){
        this.latlng = latlng;
        this.pictureFileName = pictureFileName;
    }
    
    public void setTag(Tag tag){
        tags.add(tag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pictureFileName);
        dest.writeParcelable(latlng,flags);
    }

    public static final Parcelable.Creator<Report> CREATOR
            = new Parcelable.Creator<Report>() {
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    private Report(Parcel in) {
        pictureFileName = in.readString();
        latlng = in.readParcelable(LatLng.class.getClassLoader());
    }
    
    public String getPictureFileName(){
        return pictureFileName;
    }
    
    public LatLng getLatlng(){
        return latlng;
    }
}
