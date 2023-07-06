package com.example.asaf;

import java.util.ArrayList;

public class DriveModel {
    private static DriveModel instance;
    private ArrayList<DriveModel> driveList = new ArrayList<>();

    String name;
    String date;
    String time;
    String from;
    String to;
    String passengerCount;

    public DriveModel(String name,String date, String time, String from, String to,String passengerCount) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
        this.passengerCount = passengerCount;
    }

    public static DriveModel getInstance() {
        if (instance == null) {
            instance = new DriveModel("saar","25","1","a","b","2");
        }
        return instance;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public String getPassengerCount() {
        return passengerCount;
    }

    public ArrayList<DriveModel> getDriveList() {
        return driveList;
    }
}




