package com.ibm.sso.common;

// Generated By AEF3 Framework, powered by Dr.Adldoost :D
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ErrorCodeReaderUtil {

    static ResourceBundle rb = ResourceBundle.getBundle("errorcodes");

    public static String getResourceProperity(String key)  {
            String val = rb.getString(key);
        try {
            val = new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static Enumeration<String> getResourceKeys(String key) {
            return rb.getKeys();
    }
}