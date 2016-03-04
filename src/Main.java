import models.DB;
import models.Employee;
import models.EmployeeController;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class Main {
    public static void main(String[] args){
        DB db = DB.getInstance();
        EmployeeController employeeController = new EmployeeController();
        Employee employee = employeeController.signIn("bob", "roche", "tomate");
        System.out.println(employee);

        Employee employeeConnecter = employeeController.logIn(13, "tomate");
        System.out.println(employeeConnecter);
    }
}
