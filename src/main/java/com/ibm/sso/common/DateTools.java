package com.ibm.sso.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {

    public static String getDateStringInYYYMMDDFormat(Date date) {
        if(date == null)
            return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
