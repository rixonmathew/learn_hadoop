package com.mockgenerator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 12:26 PM
 * This class is date based util class that provides utility methods for formatting dates
 */
public class DateUtil {

    private final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private static final Map<String,DateFormat> formatters = new HashMap<String,DateFormat>();

    public synchronized static Date getFormattedDate(String dateString) {
        try {
            DateFormat dateFormat = getFormatter(DEFAULT_DATE_FORMAT);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date string " + dateString);
        } catch(Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date string " + dateString);
        }
    }

    public synchronized static String getFormattedDate(Date date, String formatMask) {
        DateFormat format = getFormatter(formatMask);
        return format.format(date);
    }

    private synchronized static DateFormat getFormatter(String formatMask) {

        DateFormat dateFormatter = null;
        if (formatters.containsKey(formatMask)){
            dateFormatter = formatters.get(formatMask);
        }  else {
            if (formatMask==null) formatMask=DEFAULT_DATE_FORMAT;
            dateFormatter = new SimpleDateFormat(formatMask);
            formatters.put(formatMask,dateFormatter);
        }

        return dateFormatter;
        /*
        if (formatMask==null){
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        return new SimpleDateFormat(formatMask);
        */
    }
}