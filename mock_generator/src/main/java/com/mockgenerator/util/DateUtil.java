package com.mockgenerator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 12:26 PM
 * This class is date based util class that provides utility methods for formatting dates
 */
public class DateUtil {
    private static final DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);

    public static Date getFormattedDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date string " + dateString);
        }
    }

    public static String getFormattedDate(Date date, String formatMask) {
        //TODO reseaarch on how format can be checked
        return getDateAsString(date);
    }


    public static String getDateAsString(Date date) {
        return dateFormat.format(date);
    }
}