package models;

import java.sql.*;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class DB {
    private static DB instance;

    public static DB getInstance(){
        if (instance == null)
            instance = new DB();
        return instance;
    }
    private static String URL = "jdbc:mysql://localhost/pr_tp3?useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "";

    private Connection connection;
    private DB(){
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Client getClient(int id){
        Client client = null;
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM client where id = "+id);
            ResultSet resultSet = statement.getResultSet();
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            int telephone = resultSet.getInt("telephone");
            client = new Client(nom,prenom,telephone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
