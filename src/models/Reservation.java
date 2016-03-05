package models;

import java.sql.Date;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class Reservation {
    int id;
    int id_client;
    int id_chambre;
    Date checkin;
    Date checkout;

    public Reservation(int id, int id_client, int id_chambre, Date checkin, Date checkout) {
        this.id = id;
        this.id_client = id_client;
        this.id_chambre = id_chambre;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public int getId() {
        return id;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_chambre() {
        return id_chambre;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }
}
