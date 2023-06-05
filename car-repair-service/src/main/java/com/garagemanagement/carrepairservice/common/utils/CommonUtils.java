package com.garagemanagement.carrepairservice.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static String dateToString(Date date){
        if(date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static double roundDouble(double d){
        return Math.round(d * 1000.0) / 1000.0;
    }
}
