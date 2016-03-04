package models;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.ServerSocket;


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
                clientSSLThread.send("Hi");
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
                    String[] inputs = line.split(":");
                    switch (inputs[0]){
                        case "client":
                            ClientController.getInstance().process(inputs);
                            break;
                        case "employe":
                            EmployeeController.getInstance().process(inputs);
                            break;
                        case "reservation":
                            ReservationController.getInstance().process(inputs);
                            break;
                        case "chambre":
                            ChambreController.getInstance().process(inputs);
                            break;
                    }
                } catch (IOException e) {
                    close();
                }
                if (line == null)
                    break;
            }
        }

        private void close() {
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

        private void send(String msg) {
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
