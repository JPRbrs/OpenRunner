package com.openrunner.jprapps.openrunner;

public class Instant {

    private int id;
    private long time;
    private float latitude;
    private float longitude;
    private int race;

    public Instant(){}

    public Instant(long time, float latitude, float longitude, int race) {
        super();
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.race = race;
    }

    @Override
    public String toString() {
        return "Instant " + time + "on race " + race;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }
}
