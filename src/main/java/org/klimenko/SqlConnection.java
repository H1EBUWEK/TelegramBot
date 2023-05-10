package org.klimenko;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.klimenko.SqlProperties.*;

public class SqlConnection {
    public static void SqlConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection conn = DriverManager.getConnection(url, user, password)){
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
