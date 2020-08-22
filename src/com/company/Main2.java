package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;

public class Main2 {
    private static String DB_URL = "jdbc:mysql://localhost:3306/moviesrental?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "19Sprzaczka88";
    private static Connection DB_CONNECTION;

    private static final String QUERY = "select title, releaseDate from moviesinfo";
    private static final String QUERY_CUSTOMER = "select fullName, phone, address from customers";
    private static final String QUERY_PARAMETRIZED = "select title, releaseDate " +
            "from moviesinfo where releaseDate " +
            "between ? and ?";
    private static final String INSERT_MOVIE = "insert into moviesinfo (title, genre, releaseDate, description) " +
            "values (?, ?, ?, ?)";
    private static final String INSERT_CLIENT = "insert into customers (fullName, phone, address) " +
            "values (?, ?, ?)";

    public static void main(String[] args) {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", DB_USER);
        connectionProperties.put("password", DB_PASSWORD);

        try {
            DB_CONNECTION = DriverManager.getConnection(DB_URL, connectionProperties);
            Statement statement = DB_CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_CUSTOMER);
//            System.out.println("Filmy w bazie");
//            System.out.println("Tytuł\t|\tData produkcji");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("title") + "\t|\t" +
//                        resultSet.getDate(2));
//            }
            System.out.println("Klienci");
            System.out.println("ImieINazwisko \t|\t nrTel \t|\t adres");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("fullName") + "\t|\t" +
                        resultSet.getString("phone") + "\t|\t" + resultSet.getString("address"));
            }
            statement.close();
//            LocalDate[] dates = getDateFromUser();

//            printMoviesReleasedBetween(dates[0], dates[1]);
//            addMovie("Green Book", "Drama",LocalDate.of(2018,01,12), "About nothink");
            addClient("Marek Marecki", "512525889","Januszowo 15");
            DB_CONNECTION.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void addMovie(String title, String genre, LocalDate releaseDate, String description){
        try(PreparedStatement preparedStatement = DB_CONNECTION.prepareStatement(INSERT_MOVIE)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setDate(3, java.sql.Date.valueOf(releaseDate));
            preparedStatement.setString(4, description);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void addClient(String fullName, String phone, String address){
        try(PreparedStatement preparedStatement = DB_CONNECTION.prepareStatement(INSERT_CLIENT)){
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void printMovies(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title") + "\t|\t" +
                        resultSet.getDate(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private static void printMoviesReleasedBetween(LocalDate from, LocalDate to) {
//        try (PreparedStatement preparedStatement = DB_CONNECTION.prepareStatement(QUERY_PARAMETRIZED)) {
//            preparedStatement.setDate(1, java.sql.Date.valueOf(from));
//            preparedStatement.setDate(2, java.sql.Date.valueOf(to));
//
//            printMovies(preparedStatement.executeQuery());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    private static LocalDate[] getDateFromUser() {
//        Scanner scanner = new Scanner(System.in);
//        LocalDate[] dates = new LocalDate[2];
//
//        System.out.println("Podaj datę początkową: ");
//        dates[0] = LocalDate.parse(scanner.nextLine());
//
//        System.out.println("Podaj datę końcową: ");
//        dates[1] = LocalDate.parse(scanner.nextLine());
//
//        return dates;
//    }
}