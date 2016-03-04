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
    public Client selectClient(int id){
        Client client = null;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute("SELECT * FROM client where id = "+id)){
                ResultSet resultSet = statement.getResultSet();
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int telephone = resultSet.getInt("telephone");
                client = new Client(id,nom,prenom,telephone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    public Employee selectEmployee(int id){
        Employee employee = null;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute("SELECT * FROM employees where id = "+id)){
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()){
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String password = resultSet.getString("mot_de_passe");
                    employee = new Employee(id,nom,prenom,password);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    public Employee insertEmployee(String nom, String prenom, String password){
        Employee employee = null;
        try {
            Statement statement = connection.createStatement();

            if(statement.executeUpdate("INSERT INTO employees (`nom`,`prenom`,`mot_de_passe`) VALUES (\"" + nom + "\",\"" + prenom + "\",\"" + password + "\");", Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    employee = new Employee(resultSet.getInt(1),nom,prenom,password);
                }
            }

            //employee = new Employee(id,nom,prenom,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
