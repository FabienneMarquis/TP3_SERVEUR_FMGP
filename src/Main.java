import models.*;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class Main {
    public static void main(String[] args){
        Context context = Context.getInstance();
        ServeurSSL serveurSSL = new ServeurSSL();
        serveurSSL.start();

    }
}
