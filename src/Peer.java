import javax.swing.*;
import java.net.*;
import java.io.*;

public class Peer {

    static String username;
    static String role;
    static InetAddress trackerAddress;
    static Socket client;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static int trackerWelcomePort = 9090;
    static boolean correctSettings;
    static boolean newGameRequest;
    static boolean quitRequest;
    static boolean cellSelected;
    static boolean playing;
    static int currentCellNum;
    static String currentPlayer;
    Board board;

    public Peer() {
        try {
            trackerAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public static boolean connectToTracker() {

        String response;
        try {
            client = new Socket(trackerAddress, trackerWelcomePort);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            out.println(role + "," + username);
            out.flush();
            System.out.println("Connected to tracker");
            return true;

        } catch (IOException e) {
            //System.err.print("IO Exception");
            return false;
        }
    }

    public static void sendRequestToTracker(String request) {
        String response;
        // try {
        out.println(request);
        out.flush();
        // response = in.readLine();
        //  System.out.println("Server Response : " + response);

        //  } catch ( ) {
        // e.printStackTrace();
        //  System.out.println("Socket read Error");
        // }
    }

    public static String start() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(new StartPeer().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        while (!correctSettings) {
            System.out.print("");
        }
        role = StartPeer.getPeerRole();
        frame.dispose();
        return role;
    }

    public static void quit() {
        sendRequestToTracker("quit," + role);
        try {

            in.close();
            out.close();
            client.close();
            System.out.println("Connection Closed");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void displayBoard() {
        board = new Board("Tic Tac Toe");
        board.pack();
        board.setVisible(true);
    }

}
