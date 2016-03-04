package models;

/**
 * Created by 0940135 on 2016-03-04.
 */
public abstract class BaseController {
    private static EmployeeController instance;
    public static EmployeeController getInstance(){
        if(instance == null) instance = new EmployeeController();
        return instance;
    }
    public abstract void process(String[] inputs);
}
