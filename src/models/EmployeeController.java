package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class EmployeeController extends BaseController {


    public Employee logIn(int id, String password) {
        Employee employee = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            String encryptedPassword = sb.toString();
            Employee employeeWanted = DB.getInstance().selectEmployee(id);
            if(employeeWanted!=null){
                if (employeeWanted.getMotDePasse().compareTo(encryptedPassword) == 0) {
                    employee = employeeWanted;
                    Logger.getLogger("log").log(Level.FINEST, null, "Login:" +employee);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return employee;
    }

    public int signIn(String nom, String prenom, String password) {
        int id = -1;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            String encryptedPassword = sb.toString();
            id = DB.getInstance().insertEmployee(nom, prenom, encryptedPassword);
            if(id>0){
                Logger.getLogger(getClass().getName()).log(Level.FINEST, null, "Signin:" +new Employee(id,nom,prenom,""));
            }

        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return id;
    }

    @Override
    public void dispatch(String inputs, ServeurSSL.ClientSSLThread origin) {
        String[] split = inputs.split("\\?");
        String action = split[0];
        String[] args = split[1].split("&");
        switch (action) {
            case "login": {
                int id = -1;
                String password = "";
                for (String arg : args) {
                    String[] tmp = arg.split("=");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "id": {
                                id = Integer.valueOf(tmp[1]);
                                break;
                            }
                            case "mot_de_passe": {
                                password = tmp[1];
                                break;
                            }
                        }
                    }

                }
                if (id != -1 && password.length() > 0) {
                    Employee employee = logIn(id, password);
                    if(employee!=null){
                        origin.send("employee@login?prenom=" + employee.getPrenom() + "&nom=" + employee.getNom());
                    }else {
                        origin.send("employee@login?error=failed");
                    }

                }
                break;
            }
            case "signin": {
                String nom = "";
                String prenom = "";
                String password = "";
                for (String arg : args) {
                    String[] tmp = arg.split("=");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "nom": {
                                nom = tmp[1];
                                break;
                            }
                            case "prenom": {
                                prenom = tmp[1];
                                break;
                            }
                            case "mot_de_passe": {
                                password = tmp[1];
                                break;
                            }

                        }
                    }

                }
                if (nom.length() > 0 && prenom.length() > 0 && password.length() > 0) {
                    int id = signIn(nom, prenom, password);
                    origin.send("employee@signin?id=" + id);
                }else {
                    origin.send("employee@signin?error=failed");
                }

                break;
            }
        }
    }
}
