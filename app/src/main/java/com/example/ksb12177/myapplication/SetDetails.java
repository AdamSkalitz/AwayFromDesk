package com.example.ksb12177.myapplication;

/**
 * Created by Turkleton's on 22/11/2016.
 */

public class SetDetails {

    private static String stringLocation="";
    private static String stringTimeAway="";
    private static int intRadioGroupSelection=0;

    public SetDetails(String loc, String timeaway, int rgs){
        stringLocation = loc;
        stringTimeAway= timeaway;
        intRadioGroupSelection = rgs;
    }

    public static String getLocation(){
        return stringLocation;
    }
    public  static String getTimeAway(){
        return stringTimeAway;
    }
    public static int getShortcut(){
        return intRadioGroupSelection;
    }
    public static void setLocation(String loc){
        stringLocation = loc;
    }
    public static void setTimeAway(String timeaway){
        stringTimeAway= timeaway;
    }
    public static void setShortcut(int rgs){
        intRadioGroupSelection = rgs;
    }

}
