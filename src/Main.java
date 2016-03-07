import models.*;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by 0940135 on 2016-03-03.
 */
public class Main {
    public static void main(String[] args){
        try {
            Handler handler = new FileHandler("log.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            handler.setLevel(Level.ALL);
            Logger.getLogger("").addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Context context = Context.getInstance();
        ServeurSSL serveurSSL = new ServeurSSL();
        serveurSSL.start();

    }
}
