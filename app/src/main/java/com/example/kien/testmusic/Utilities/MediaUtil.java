package com.example.kien.testmusic.Utilities;

/**
 * Created by Kien on 11/17/2016.
 */

public class MediaUtil {
    public static String convertMilisecondstoTime(int Miliseconds){
        String time = "";
        int seconds = Miliseconds/1000;
        int minute = seconds/60;
        int second = seconds % 60;
        if(minute < 9){
            time = time + "0" + minute;
        }else{
            time = time + minute;
        }
        time = time + ":";
        if(second < 9){
            time = time + "0" + second;
        }else{
            time = time + second;
        }
        return time;

    }
    public static int caculatePercent(int current,int duration){
        if(current == 0 && duration == 0){
            return 0;
        }else{
            int result = (current * 100)/duration;
            return result;
        }

    }
    public static int convertPercenttoMiliseconds(int percent, int duration){
        return (percent * duration)/100;

    }
}
