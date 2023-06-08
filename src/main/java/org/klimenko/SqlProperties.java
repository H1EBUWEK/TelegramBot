package org.klimenko;

import static org.klimenko.Main.properties;

public class SqlProperties {
    public final static String url = properties.getProperty("url");
    public final static String user = properties.getProperty("user");
    public final static String password = properties.getProperty("password");

}
