/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testchatclientserver;
import java.io.*;
import java.net.*;
/**
 *
 * @author FarzBZ
 */
public class Server {

    
    /**
     * @param args the command line arguments
     */
    private static ServerSocket servSock;
    private static final int PORT = 80;
    public static void main(String[] args) throws IOException {
        System.out.println("Membuka koneksi di port 80....\n");
        try {
            servSock = new ServerSocket(PORT);
        } catch (Exception e) {
            System.out.println("Gagal membuka port");
            System.exit(1);
        }
        do {
            Socket client = servSock.accept();
            ClientHander handler = new ClientHander(client);
            handler.start();
        } while (true);
        
    }
}
class ClientHander extends Thread {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHander(Socket socket) {
        client = socket;
        try {
            in = new BufferedReader(
            new InputStreamReader(client.getInputStream())
            );
            out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
            e.printStackTrace();
            }
    }
    public void run() {
        try {
            String nama, pesan, balik = "";
            Integer no = 0;
            do {
                nama = in.readLine();
                no++;
                System.out.println("No." + no + ". Pengirim: " + nama);
                pesan = in.readLine();
                System.out.println("Pesan diterima:" + pesan);
                out.println(nama + ": " + pesan);
            } while (!pesan.equals("!QUIT"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            if (client != null) {
            System.out.println("Menutup koneksi...");
            client.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
