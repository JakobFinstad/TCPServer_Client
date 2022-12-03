package no.ntnu.IDATA2304_Networks;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class Client {

  private static final String DATABASE_URL = "jdbc:mariadb://mysql690.loopia.se/haslerud_tech";
  private static final String USERNAME = "group10@h328964";
  private static final String PASSWORD = "evensivert123";
  private static Statement st;

  private String tableName = "UserData";
  private static Connection con;

    private String tableAttributes = "kWh,Date,Time,Year"; //Not yet implemented
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Client client = new Client();
        Socket socket = new Socket("localhost",4999);
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        client.connectToDatabase();
        Boolean keepGoing = true;
        int timesSendtData = 0;

        while(keepGoing) {
            pr.println("Get data plz");
            pr.flush();

            client.sendToDatabase(bf); // Kjøre denne
            //Eller det under
           /**String str = bf.readLine();
            System.out.println("Server: " + str + " Øre/kWh");

            str = bf.readLine();
            System.out.println("Server: " + str + " o'klokke");

            str = bf.readLine();
            System.out.println("Server: " + str);*/
           timesSendtData++;
           if (timesSendtData ==100){
               keepGoing=false;
           }
        }
        con.close();
        System.out.println("Connection closed.");
    }

    private void sendToDatabase(BufferedReader bf) throws IOException, SQLException{
        String sqlQuery = "INSERT INTO " + tableName + "(" + tableAttributes +
                ") VALUES(" + bf.readLine() + ",'" + bf.readLine() + "','" + bf.readLine() + "','" +
                bf.readLine()+"');";

        System.out.println(sqlQuery);
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            rs.next();
            st.close();
            System.out.println("Data sent to dbms");
        }catch (Exception e){
            System.out.println("Something went wrong: "+ e.getMessage());
        }

    }

    private void connectToDatabase(){
        try {
            con = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connection Established successfully");
        }catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }
}
