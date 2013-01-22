package com.mockgenerator.provider;

import com.mockgenerator.util.DateUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: rixon
 * Date: 22/1/13
 * Time: 12:22 PM
 * This class will provide random values based on a set of values defined in a file.
 */
class FileBasedRandomValueProvider {

    private final static Logger LOG = Logger.getLogger(FileBasedRandomValueProvider.class);
    private static List<Date> dateList;
    private static final String DATES_FILE = "dates.txt";
    private static final Random random;


    static{
        dateList = new ArrayList<Date>();
        random = new Random();
    }

    public static Date randomDate() {
        if (dateList.isEmpty()) {
            try{
              datesFormFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int index = random.nextInt(dateList.size());
        return dateList.get(index);
    }

    private static void datesFormFile() throws IOException {
        URL datesURL = ClassLoader.getSystemClassLoader().getResource(DATES_FILE);

        if (datesURL == null) {
            LOG.error("Dates file not found :"+DATES_FILE);
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(datesURL.getPath()));
        String line = br.readLine();
        while (line != null) {
            line = br.readLine();
            if (line != null)
                dateList.add(DateUtil.getFormattedDate(line));
        }
        br.close();
    }

}
