package no.ntnu.IDATA2304_Networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

public class Server {
    private static DecimalFormat df = new DecimalFormat("0.00");
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        ServerSocket ss = new ServerSocket(4999);
        Socket socket = ss.accept();
        Boolean connected = true;
        while(connected) {
            System.out.println("Connected");

            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String str = br.readLine();
            System.out.println("Client: " + str);

            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println(generator.getPrice());
            pr.flush();
            pr.println(generator.getCurrentHour());
            pr.flush();
            pr.println(generator.getDate());
            pr.flush();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
