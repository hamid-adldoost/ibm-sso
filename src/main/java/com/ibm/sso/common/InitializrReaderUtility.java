
package com.ibm.sso.common;


// Generated By AEF3 Framework, Powered by Dr.Adldoost :D
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * @author Generated By AEF Framework , powered by Dr.Adldoost :D 
 */
public class InitializrReaderUtility {

    static ResourceBundle rb = ResourceBundle.getBundle("config");

    public static String getResourceProperity(String key) {
        return rb.getString(key);
    }

    public static Enumeration<String> getResourceKeys() {
        return rb.getKeys();
    }

}
