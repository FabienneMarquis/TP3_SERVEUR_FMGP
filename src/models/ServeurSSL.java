package models;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by 0940135 on 2016-03-04.
 */
public class ServeurSSL extends Thread {
    public static void main(String args[]) throws Exception {
        new ServeurSSL().start();
    }

    public ServeurSSL() {
        System.setProperty("javax.net.ssl.keyStore", "serveurtp3.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "derpherp");
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket ss = null;
        try {
            ss = ssf.createServerSocket(8888);
            while (true) {
                SSLSocket s = (SSLSocket) ss.accept();
                ClientSSLThread clientSSLThread = new ClientSSLThread(s);
                clientSSLThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ClientSSLThread extends Thread {
        private SSLSocket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;

        public ClientSSLThread(SSLSocket socket) {
            this.socket = socket;
            try {
                socket.startHandshake();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Logger.getLogger(getClass().getName()).log(Level.FINEST, "caca", "Connection[" + socket.getInetAddress() + "]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                String line = null;
                try {
                    line = bufferedReader.readLine();
                    System.out.println(line);
                    if(line!=null){
                        String[] split = line.split("@");
                        String controller = split[0];
                        String action = split[1];
                        switch (controller) {
                            case "client":
                                Context.getInstance().getClientController().dispatch(action, this);
                                break;
                            case "employee":
                                Context.getInstance().getEmployeeController().dispatch(action, this);
                                break;
                            case "reservation":
                                Context.getInstance().getReservationController().dispatch(action, this);
                                break;
                            case "chambre":
                                Context.getInstance().getChambreController().dispatch(action, this);
                                break;
                        }
                    }

                } catch (IOException e) {
                    close();
                }
                if (line == null)
                    break;
            }
        }

        private void close() {
            Logger.getLogger(getClass().getName()).log(Level.FINEST, null, "Disconnection[" +socket.getInetAddress()+"]");
            try {
                socket.close();
            } catch (IOException e1) {
                //e1.printStackTrace();
            }
            try {
                bufferedReader.close();
            } catch (IOException e1) {
                // e1.printStackTrace();
            }
            try {
                bufferedWriter.close();
            } catch (IOException e1) {
                //e1.printStackTrace();
            }
        }

        public void send(String msg) {
            try {
                bufferedWriter.write(msg + "\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
    }
}
