package com.example.assignment_android_2.Data;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Record {
    private String date;
    private String time;
    private int points;
    private double latitude;
    private double longitude;

    public Record(int points, double latitude, double longitude) {
        setPoints(points);
        setLatitude(latitude);
        setLongitude(longitude);
        setDate();
        setTime();
    }

    private void setDate() {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = dateNow.format(dateFormatter);
    }

    private void setTime() {
        LocalTime timeNow = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.time = timeNow.format(timeFormatter);
    }

    private void setPoints(int points) {
        this.points = points;
    }

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPoints() {
        return points;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Record{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", points=" + points +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
