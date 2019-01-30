package com.ibm.sso.common;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class FarsiCodeReaderUtility {

    static ResourceBundle rb = ResourceBundle.getBundle("farsicodes");

    public static String getResourceProperity(String key) {
        return rb.getString(key);
    }

    public static Enumeration<String> getResourceKeys() {
        return rb.getKeys();
    }

}