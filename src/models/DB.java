package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class DB {
    private static String URL = "jdbc:mysql://localhost/pr_tp3?useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "";
    private static DB instance;
    public static DB getInstance(){
        if(instance == null) instance = new DB();
        return instance;
    }
    private Connection connection;
    public DB(){
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
    public int insertEmployee(String nom, String prenom, String password){
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            if(statement.executeUpdate("INSERT INTO employees (`nom`,`prenom`,`mot_de_passe`) VALUES (\"" + nom + "\",\"" + prenom + "\",\"" + password + "\");", Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public int insertClient(String nom, String prenom, int telephone){
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            if(statement.executeUpdate("INSERT INTO clients (`nom`,`prenom`,`telephone`) VALUES (\"" + nom + "\",\"" + prenom + "\"," + telephone + ");", Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Client selectClient(int id){
        Client client = null;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute("SELECT * FROM client where id = "+id)){
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()){
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    int telephone = resultSet.getInt("telephone");
                    client = new Client(id,nom,prenom,telephone);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> selectClients() {
        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            if(statement.execute("SELECT * FROM clients")){

                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    int telephone = resultSet.getInt("telephone");
                    clients.add(new Client(id,nom,prenom,telephone));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
    public int insertReservation(int id_client,int id_chambre, Date checkin, Date checkout){
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            if(statement.executeUpdate("INSERT INTO reservations (`id_client`,`id_chambre`,`checkin`,`checkout`) VALUES (" + id_client + "," + id_chambre + ",\"" + checkin + "\""+ ",\"" + checkout + "\");", Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Reservation selectReservation(int id){
        Reservation reservation = null;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute("SELECT * FROM reservations where id = "+id)){
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()){
                    int id_client = resultSet.getInt("id_client");
                    int id_chambre = resultSet.getInt("id_chambre");
                    Date checkin = resultSet.getDate("checkin");
                    Date checkout = resultSet.getDate("checkout");
                    reservation = new Reservation(id,id_client,id_chambre,checkin,checkout);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> selectReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            if(statement.execute("SELECT * FROM reservations")){

                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int id_client = resultSet.getInt("id_client");
                    int id_chambre = resultSet.getInt("id_chambre");
                    Date checkin = resultSet.getDate("checkin");
                    Date checkout = resultSet.getDate("checkout");
                    reservations.add(new Reservation(id,id_client,id_chambre,checkin,checkout));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public List<Reservation> selectReservationsOfClient(int id_client) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            if(statement.execute("SELECT * FROM reservations where id_client = "+id_client)){

                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int id_chambre = resultSet.getInt("id_chambre");
                    Date checkin = resultSet.getDate("checkin");
                    Date checkout = resultSet.getDate("checkout");
                    reservations.add(new Reservation(id,id_client,id_chambre,checkin,checkout));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public List<Reservation> selectReservationsOfChambre(int id_chambre) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            if(statement.execute("SELECT * FROM reservations where id_chambre = "+id_chambre)){

                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int id_client = resultSet.getInt("id_client");
                    Date checkin = resultSet.getDate("checkin");
                    Date checkout = resultSet.getDate("checkout");
                    reservations.add(new Reservation(id,id_client,id_chambre,checkin,checkout));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public int insertChambre(String nom, String description){
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            if(statement.executeUpdate("INSERT INTO chambres (`nom`,`description`) VALUES (\"" + nom + "\""+ ",\"" + description + "\");", Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Chambre selectChambre(int id){
        Chambre chambre = null;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute("SELECT * FROM chambres where id = "+id)){
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()){
                    String nom = resultSet.getString("nom");
                    String description = resultSet.getString("description");
                    chambre = new Chambre(id,nom,description);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambre;
    }

    public List<Chambre> selectChambres() {
        List<Chambre> chambres = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            if(statement.execute("SELECT * FROM chambres")){

                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String description = resultSet.getString("description");
                    chambres.add(new Chambre(id,nom,description));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chambres;
    }

}
