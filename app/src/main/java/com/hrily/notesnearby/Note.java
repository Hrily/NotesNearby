package com.hrily.notesnearby;
//////////////
// by hrily //
//////////////

public class Note {
    public double lat, lng;
    public String title, desc, id;

    public Note(){}

    public Note(double lat, double lng, String title, String desc) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.desc = desc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
