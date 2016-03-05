package models;

import java.util.List;

/**
 * Created by 0940135 on 2016-03-04.
 */
public class ClientController extends BaseController {
    @Override
    public void dispatch(String inputs, ServeurSSL.ClientSSLThread origin) {
        String[] split = inputs.split("\\?");
        String action = split[0];


        switch (action) {
            case "new": {
                String[] args = split[1].split("&");
                String nom = "";
                String prenom = "";
                int telephone = -1;
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
                            case "telephone": {
                                telephone = Integer.valueOf(tmp[1]);
                                break;
                            }
                        }
                    }
                }
                if (nom.length() > 0 && prenom.length() > 0 && telephone > 0) {
                    int id = DB.getInstance().insertClient(nom, prenom, telephone);
                    origin.send("client@new?id=" + id);
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
                    Client client = DB.getInstance().selectClient(id);
                    if (client != null) {
                        origin.send("client@get?id=" + client.getId() + "&nom=" + client.getNom() + "&prenom=" + client.getPrenom() + "&telephone=" + client.getTelephone());
                    } else {
                        origin.send("client@error=failed");
                    }

                }
                break;
            }
            case "all": {
                List<Client> clients = DB.getInstance().selectClients();
                if (clients.size() > 0) {
                    for (Client client : clients) {
                        origin.send("client@all?id=" + client.getId() + "&nom=" + client.getNom() + "&prenom=" + client.getPrenom() + "&telephone=" + client.getTelephone());
                    }

                } else {
                    origin.send("client@error=failed");
                }

                break;
            }
        }
    }
}
