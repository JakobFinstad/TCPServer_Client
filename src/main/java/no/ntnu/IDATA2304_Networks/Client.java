package no.ntnu.IDATA2304_Networks;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;

public class Client {
    private String tableName = "tableName";
    private String tableAttributes = "attribute1,attribute2,attribute3"; //Not yet implemented
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Socket socket = new Socket("localhost",4999);
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        while(true) {
            pr.println("Get data plz");
            pr.flush();

           // client.sendToDatabase(bf); // Kjøre denne
            //Eller det under
           String str = bf.readLine();
            System.out.println("Server: " + str + " Øre/kWh");

            str = bf.readLine();
            System.out.println("Server: " + str + " o'klokke");

            str = bf.readLine();
            System.out.println("Server: " + str);
        }
    }

    private void sendToDatabase(BufferedReader bf) throws IOException {
        String sqlQuery = "INSERT INTO " + tableName + "(" + tableAttributes +
                ") VALUES(" + bf.readLine() + ",'" + bf.readLine() + "','" + bf.readLine() + "');";

        System.out.println(sqlQuery);
        //Skal sendes til databasen ikke terminal vindu
    }
}
