package org.klimenko;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.klimenko.Main.personList;
import static org.klimenko.SqlProperties.*;


public class DAO {

    public static boolean CheckTinId(long id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlSelect = "SELECT id FROM tin";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlSelect)) {
                    while (rs.next()) {
                        if (rs.getLong(1) == id)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean CheckTinUsername(String name) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlSelect = "SELECT name FROM tin";
        List<String> customerName = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlSelect)) {
                    while (rs.next()) {
                        customerName.add(rs.getString(1));
                    }
                }
            }
        }
        return customerName.contains(name);
    }

    public static synchronized void AddToTin(long id, String name) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlInsert = "INSERT INTO tin (id, name) " + "VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.execute();
            }
        }
    }

    public static boolean CheckDebtLine(String creditor, String debtor, String chatId) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        String sqlSelect = "SELECT creditor, debtor, sum FROM " + chatId;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlSelect)) {
                    while (rs.next()) {
                        Person person = new Person(rs.getString(2), rs.getString(1), rs.getBigDecimal(3));
                        personList.add(person);
                        if (person.creditor.equals(creditor) && person.debtor.equals(debtor) || person.creditor.equals(debtor) && person.debtor.equals(creditor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean CheckDebtLineStraight (String creditor, String debtor, String chatId) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        String sqlSelect = "SELECT creditor, debtor, sum FROM " + chatId;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlSelect)) {
                    while (rs.next()) {
                        Person person = new Person(rs.getString(2), rs.getString(1), rs.getBigDecimal(3));
                        personList.add(person);
                        if (person.creditor.equals(creditor) && person.debtor.equals(debtor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static synchronized void AddCreditLine(String debtor, String creditor, BigDecimal money, String chatId) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlAdd = "INSERT INTO " + chatId + " (creditor, debtor, sum) " + "VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, creditor);
                preparedStatement.setString(2, debtor);
                preparedStatement.setBigDecimal(3, money);
                preparedStatement.execute();
            }
        }
    }

    public static synchronized void ChangeCreditLine(String debtor, String creditor, BigDecimal sum, String chatId) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlSumUpdate = "UPDATE " + chatId + " SET sum = ? WHERE creditor = ? AND debtor = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlSumUpdate)) {
                preparedStatement.setBigDecimal(1, sum);
                preparedStatement.setString(2, creditor);
                preparedStatement.setString(3, debtor);
                preparedStatement.execute();
            }
        }
    }

    public static void DeleteCreditline(String creditor, String debtor, String chatId) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlDelete = "DELETE FROM " + chatId + " WHERE creditor = ? AND debtor = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (PreparedStatement preparedstatement = conn.prepareStatement(sqlDelete)) {
                preparedstatement.setString(1, creditor);
                preparedstatement.setString(2, debtor);
                preparedstatement.execute();
            }
        }
    }

    public static synchronized void AddDebtLine(BigDecimal sum, String creditor, String debtor, String chatId) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlInsert = "INSERT INTO " + chatId + " (creditor, debtor, sum) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert)) {
                preparedStatement.setString(1, debtor);
                preparedStatement.setString(2, creditor);
                preparedStatement.setBigDecimal(3, sum);
                preparedStatement.execute();
            }
        }
    }

    public static BigDecimal getDebtBalance(String debtor, String creditor, String chatId) throws SQLException {
        BigDecimal balance = null;
        String sqlGetSum = "SELECT SUM FROM " +chatId + " WHERE creditor = ? AND debtor = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement preparedstatement = conn.prepareStatement(sqlGetSum)) {

                preparedstatement.setString(1, creditor);
                preparedstatement.setString(2, debtor);
                try(ResultSet rs = preparedstatement.executeQuery()) {
                    if (rs.next()) {
                         balance = rs.getBigDecimal(1);
                    }
                }
            }
        }
        return balance;
    }

    public static boolean TableListContains (String tablename) throws SQLException {
        boolean isInTables = false;
        String sqlSelect = "show tables";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()){
                        if(resultSet.getString(1).equals(tablename)){
                            isInTables = true;
                        }
                    }
                }
            }
        }
        return isInTables;
    }

    public static void CreateTable (String chatId) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String sqlCreateTable = "CREATE TABLE " + chatId + " LIKE example";
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Statement Statement = connection.prepareStatement(sqlCreateTable)) {
                Statement.execute(sqlCreateTable);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static long GetUserId (String username) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        long id = 1;
        String sqlGetId = "Select * FROM tin";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlGetId)) {
                    while (rs.next()) {
                        if(rs.getString(2).equals(username)) {
                            id = rs.getLong(1);
                        }
                    }
                }
            }
        }
        return id;
    }


    public static List<Person> Balance(String creditor, String chatId) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        String sqlSelect = "SELECT * FROM " + chatId;
        List<Person> balanceList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlSelect)) {
                    while (rs.next()) {
                        Person person = new Person(rs.getString(2), rs.getString(1), rs.getBigDecimal(3));
                        if (person.creditor.equals(creditor) || person.debtor.equals(creditor)) {
                            balanceList.add(person);
                        }
                    }
                }
            }
        }
        return balanceList;
    }

    public static boolean doesTableExist(String tablename) throws Exception{
        String sqlIsTableExists = "show tables";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlIsTableExists)) {
                    while (rs.next()) {
                        if(rs.getString(1).equals(tablename)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isTableEmpty(String table) throws Exception{
        String sqlIsTableEmpty = "SELECT EXISTS (SELECT 1 FROM " + table + ")";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery(sqlIsTableEmpty)) {
                    if (rs.next()) {
                        if(rs.getInt(1) == 0)
                            return true;
                    }
                }
            }
        }
        return false;
    }

}