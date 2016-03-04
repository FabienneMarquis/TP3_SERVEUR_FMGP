package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class EmployeeController {
    public Employee logIn(int id, String password){
        Employee employee = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            String encryptedPassword =  sb.toString();
            Employee employeeWanted = DB.getInstance().selectEmployee(id);
            if(employeeWanted.getMotDePasse().compareTo(encryptedPassword) == 0){
                employee = employeeWanted;
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
        }
        return employee;
    }

    public Employee signIn(String nom, String prenom, String password){
        Employee employee = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            String encryptedPassword =  sb.toString();
            employee = DB.getInstance().insertEmployee(nom,prenom,encryptedPassword);

        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
        }
        return employee;
    }

}
