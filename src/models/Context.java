package models;

/**
 * Created by Gabriel on 05/03/2016.
 */
public class Context {
    private ChambreController chambreController;
    private ClientController clientController;
    private EmployeeController employeeController;
    private ReservationController reservationController;
    private static Context instance;
    public static Context getInstance(){
        if(instance == null) instance = new Context();
        return instance;
    }
    private Context(){
        System.out.println("wtf");
        chambreController = new ChambreController();
        clientController = new ClientController();
        employeeController = new EmployeeController();
        reservationController = new ReservationController();
    }

    public ChambreController getChambreController() {
        return chambreController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public ReservationController getReservationController() {
        return reservationController;
    }

}
