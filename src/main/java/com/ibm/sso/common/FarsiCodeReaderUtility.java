package com.ibm.sso.common;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class FarsiCodeReaderUtility {

    static ResourceBundle rb = ResourceBundle.getBundle("farsicodes");

    public static String getResourceProperity(String key) {
        String val = rb.getString(key);
        try {
            val = new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static Enumeration<String> getResourceKeys() {
        return rb.getKeys();
    }

}