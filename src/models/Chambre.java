package models;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class Chambre {
    int id;
    String nom;
    String description;

    public Chambre(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }
}
