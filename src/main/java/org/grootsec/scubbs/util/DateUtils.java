package org.grootsec.scubbs.util;

public class DateUtils {

    public static String getDate() {

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        
        return currentTime;
    }
}
