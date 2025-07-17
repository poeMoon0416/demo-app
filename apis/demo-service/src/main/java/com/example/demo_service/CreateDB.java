package com.example.demo_service;

import java.sql.*;

public class CreateDB {
    public static void main(String[] args) {
        final String URL = "jdbc:h2:D:/ユーザーデータ/OneDrive/デスクトップ/vsc_workspace/demo-app/sql/demo-db";
        final String USER = "sa";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Success: create DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
