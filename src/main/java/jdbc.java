import java.sql.*;
import java.lang.ClassNotFoundException;
import java.sql.Connection;
public class jdbc {
    public static void main(String args[]) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/example", "postgres", "postgres")) {
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading car records...");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cars");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("model"), resultSet.getString("price"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            }
        }
    }
