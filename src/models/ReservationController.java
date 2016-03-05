package models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class ReservationController extends BaseController {


    @Override
    public void dispatch(String inputs, ServeurSSL.ClientSSLThread origin) {
        String[] split = inputs.split("\\?");
        String action = split[0];


        switch (action) {
            case "new": {
                String[] args = split[1].split("&");
                int id_client = -1;
                int id_chambre = -1;
                Date checkin = null;
                Date checkout = null;
                for (String arg : args) {
                    String[] tmp = arg.split("=");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "id_client": {
                                id_client = Integer.parseInt(tmp[1]);
                                break;
                            }
                            case "id_chambre": {
                                id_chambre = Integer.parseInt(tmp[1]);
                                break;
                            }
                            case "checkin": {
                                checkin = Date.valueOf(tmp[1]);

                                break;
                            }
                            case "checkout": {
                                checkout = Date.valueOf(tmp[1]);

                                break;
                            }
                        }
                    }
                }
                if (id_chambre > 0 && id_client > 0 && checkin != null && checkout != null) {
                    int id = DB.getInstance().insertReservation(id_chambre, id_client, checkin, checkout);
                    origin.send("reservation@new?id=" + id);
                }

                break;
            }
            case "get": {
                String[] args = split[1].split("&");
                int id = -1;
                for (String arg : args) {
                    String[] tmp = arg.split("=");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "id": {
                                id = Integer.valueOf(tmp[1]);
                                break;
                            }
                        }
                    }
                }
                if (id > 0) {
                    Reservation reservation = DB.getInstance().selectReservation(id);
                    if (reservation != null) {
                        origin.send("reservation@get?id=" + reservation.getId()
                                + "&id_chambre=" + reservation.getId_chambre()
                                + "&id_client=" + reservation.getId_client()
                                + "&checkin=" + reservation.getCheckin()
                                + "&checkout=" + reservation.getCheckout());
                    } else {
                        origin.send("reservation@error=failed");
                    }

                }
                break;
            }
            case "all": {
                List<Reservation> reservations = DB.getInstance().selectReservations();
                if (reservations.size() > 0) {
                    for (Reservation reservation : reservations) {
                        origin.send("reservation@all?id=" + reservation.getId()
                                + "&id_chambre=" + reservation.getId_chambre()
                                + "&id_client=" + reservation.getId_client()
                                + "&checkin=" + reservation.getCheckin()
                                + "&checkout=" + reservation.getCheckout());
                    }

                } else {
                    origin.send("reservation@error=failed");
                }

                break;
            }
        }
    }
}
