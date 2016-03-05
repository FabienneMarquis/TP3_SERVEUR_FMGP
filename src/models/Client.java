package models;

/**
 * Created by 1494778 on 2016-02-22.
 */
public class Client{
    private String nom;
    private String prenom;
    private int telephone;
    private int id;

    public Client(String nom, String prenom, int telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public Client(int id, String nom, String prenom, int telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }


}
