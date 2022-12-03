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

    //The url for the database
    private static final String DATABASE_URL = "jdbc:mariadb://mysql690.loopia.se/haslerud_tech";
    //Username used to connect to the database
    private static final String USERNAME = "group10@h328964";
    //Password used to connect to the database
    private static final String PASSWORD = "evensivert123";
    //Request sent to server for connection
    private static final String REQUEST = "Can I receive data?";

  private static Statement st;

    //Name of the table that the data will go into
    private String tableName = "UserData";

    private static Connection con;

    //The name of the attributes in the table
    private static final String tableAttributes = "kWh,Date,Time,Year";

  /**
   * The main method to run the client class.
   * Establishes a TCP connection with Server to receive information and sends the
   * information to the database
   * @param args
   * @throws IOException in case of buffered reader not able to read line
   * @throws SQLException if error when connecting to database
   */
    public static void main(String[] args) throws IOException, SQLException {
        Client client = new Client();
        Socket socket = new Socket("localhost",4999);
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        client.connectToDatabase();
        Boolean keepGoing = true;
        int timesSendtData = 0;

        while(keepGoing) {
            pr.println(REQUEST);
            pr.flush();

            client.sendToDatabase(bf);
           timesSendtData++;
           if (timesSendtData ==100){
               keepGoing=false;
           }
        }
        con.close();
        System.out.println("Connection closed.");
    }

    /**
     * Sends information to the connected database
     * @param bf
     * @throws IOException Throws exception in case of buffered reader not able to read line
     */
    private void sendToDatabase(BufferedReader bf) throws IOException {
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

    /**
     * Connect to the database with url, username and password
     */
    private void connectToDatabase(){
        try {
            con = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connection Established successfully");
        }catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
        }

    }
}
