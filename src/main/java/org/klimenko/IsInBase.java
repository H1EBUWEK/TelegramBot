package org.klimenko;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.klimenko.SqlProperties.*;

public class IsInBase {
    public static void IsInBase(int id, String name) {
        String sqlSelect = "SELECT id FROM tin";
        String sqlInsert = "INSERT INTO tin (id, name) VALUES (?, ?)";
        SqlConnection.SqlConnect();
        List<Integer> customerId = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlSelect);
            while (rs.next()) {
                customerId.add(rs.getInt(1));
            }
            if (!customerId.contains(id)) {
                PreparedStatement prepInsert = conn.prepareStatement(sqlInsert);
                prepInsert.setInt(1, id);
                prepInsert.setString(2, name);
                prepInsert.execute();
            }
        } catch (Exception e) {
            System.out.println("Sql Connection problem: " + e);
        }
    }
}

