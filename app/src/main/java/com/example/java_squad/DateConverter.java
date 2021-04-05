package com.example.java_squad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public java.util.Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateTypeDate = formatter.parse(date);
        return dateTypeDate;
    }

    public String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //https://www.tutorialspoint.com/java/java_date_time.htm
        String stringTypeDate = formatter.format(date);
        return stringTypeDate;

    }
}