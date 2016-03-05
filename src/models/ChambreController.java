package models;

import java.util.List;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class ChambreController extends BaseController {


    @Override
    public void dispatch(String inputs, ServeurSSL.ClientSSLThread origin) {
        String[] split = inputs.split("\\?");
        String action = split[0];


        switch (action) {
            case "new": {
                String[] args = split[1].split("&");
                String nom = "";
                String description = "";
                for (String arg : args) {
                    String[] tmp = arg.split("=");
                    if (tmp.length == 2) {
                        switch (tmp[0]) {
                            case "nom": {
                                nom = tmp[1];
                                break;
                            }
                            case "description": {
                                description = tmp[1];
                                break;
                            }
                        }
                    }
                }
                if (nom.length() > 0 && description.length()>0) {
                    int id = DB.getInstance().insertChambre(nom, description);
                    origin.send("chambre@new?id=" + id);
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
                    Chambre chambre = DB.getInstance().selectChambre(id);
                    if (chambre != null) {
                        origin.send("chambre@get?id=" + chambre.getId() + "&nom=" + chambre.getNom() + "&description=" + chambre.getDescription());
                    } else {
                        origin.send("chambre@error=failed");
                    }

                }
                break;
            }
            case "all": {
                List<Chambre> chambres = DB.getInstance().selectChambres();
                if (chambres.size() > 0) {
                    for (Chambre chambre : chambres) {
                        origin.send("chambre@all?id=" + chambre.getId() + "&nom=" + chambre.getNom() + "&description=" + chambre.getDescription());
                    }

                } else {
                    origin.send("chambre@error=failed");
                }

                break;
            }
        }
    }

}
