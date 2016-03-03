import models.Client;
import models.DB;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class Main {
    public static void main(String[] args){
        DB db = DB.getInstance();
        Client client = db.getClient(1);
    }
}
