package no.ntnu.IDATA2304_Networks;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost",4999);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("Is it working?");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("Server: " + str);
    }
}
