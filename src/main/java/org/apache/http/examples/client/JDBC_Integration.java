package org.apache.http.examples.client;

import com.fasterxml.jackson.core.JsonParser;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.Scanner;

public class JDBC_Integration {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int i, j = 0, k = 1;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres")) {
            String response = null;
            ResultSet resultSet;

            ClientWithResponseHandler newClient = new ClientWithResponseHandler();
            Statement statement = connection.createStatement();
            PreparedStatement In_stmt = connection.prepareStatement("INSERT INTO public.cards(id, board_id, data) VALUES (?, ?, cast(? as json));");
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");


            System.out.println("Database Integration of Server Responses !\n");
            System.out.println("=========================\n\nExisting Table :\n\n==============================================");

            resultSet = statement.executeQuery("SELECT data ->'status' as Status, data->'errorNumber' as errorNumber, data->'errorMessage' as errorMessage FROM public.cards");
            while (resultSet.next()) {
                System.out.println("status : " + resultSet.getInt("status") + "\t" + "\t errorNumber : " + resultSet.getInt("errorNumber") + "\t" + "\terrorMessage : " + resultSet.getString("errorMessage"));
                j++;
            }

            System.out.println("\n=====================================================\n Insertion \n==================================================\n\n");

            try {
                response = newClient.ResponseHandlerFunc();
            } catch (Exception e) {
                e.printStackTrace();
            }

            jsonObject.setValue(response);
            In_stmt.setInt(1, j);
            In_stmt.setInt(2, k);
            In_stmt.setObject(3, jsonObject);

            i = In_stmt.executeUpdate();
            if (i < 1) {
                System.out.println("Insert failed :\n Exiting ......");
                System.exit(1);
            } else {
                System.out.println("Insertion Successful");
            }


            System.out.println("========================================================\n Updated Table : \n\n==============================================================");
            resultSet = statement.executeQuery("SELECT data ->'status' as Status, data->'errorNumber' as errorNumber, data->'errorMessage' as errorMessage FROM public.cards");
            while (resultSet.next()) {
                System.out.println("status : " + resultSet.getInt("status") + "\t" + "\t errorNumber : " + resultSet.getInt("errorNumber") + "\t" + "\terrorMessage : " + resultSet.getString("errorMessage"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }

    }
}


