package no.ntnu.IDATA2304_Networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

public class Server {
    private static Generator generator;
    private static ServerSocket serverSocket;
    private static Socket socket;
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        generator = new Generator();
        serverSocket = new ServerSocket(4999);
        socket = serverSocket.accept();
        Boolean connected = true;
        while(connected) {
            System.out.println("Connected");

            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String str = br.readLine();
            System.out.println("Client: " + str);
            server.sendData();

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        }
    private void sendData(){
        try {
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println(generator.getPrice());
            pr.flush();
            pr.println(generator.getDate());
            pr.flush();
            pr.println(generator.getCurrentHour());
            pr.flush();
            pr.println(generator.getYear());
            pr.flush();
        }catch (IOException ioException){
            System.out.println("Something went wrong, could not send data: "+ioException.getMessage());
    }
    }
}
