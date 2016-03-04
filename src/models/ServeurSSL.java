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

//            SSLSession session = ((SSLSocket) s).getSession();
//            Certificate[] cchain2 = session.getLocalCertificates();
//            for (int i = 0; i < cchain2.length; i++) {
//                System.out.println(((X509Certificate) cchain2[i]).getSubjectDN());
//            }
//            System.out.println("Peer host is " + session.getPeerHost());
//            System.out.println("Cipher is " + session.getCipherSuite());
//            System.out.println("Protocol is " + session.getProtocol());
//            System.out.println("ID is " + new BigInteger(session.getId()));
//            System.out.println("Session created in " + session.getCreationTime());
//            System.out.println("Session accessed in " + session.getLastAccessedTime());



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
                    // Traitement
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
