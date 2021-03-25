package test.java.uoc.ded.practica.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date createDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date ret = null;
        try {
            ret = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
