import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FreeRider extends Peer {

    static boolean selectedOpponent;
    static boolean closedList;
    static int oppIndex;
    static boolean listChanged;
    PeerInfo opponent;
    static List<PeerInfo> availableGameServerList;
    OpponentList frame;
    Socket socket = null;
    ListHandler listHandler;

    public void selectOpponent() {
        availableGameServerList = Collections.synchronizedList(new ArrayList<PeerInfo>());
        listHandler = new ListHandler();
        sendRequestToTracker("onlineList");

        //get game server list from tracker response
        getList();
        displaySelectionFrame();
        frame.setListContent(FreeRider.serverListToArray());
        //run a handler for modifications in gamer server list
        listHandler.start();

        while (!selectedOpponent) {
            System.out.print("");
            if (closedList)
                return;
            if (listChanged) {
                //System.out.println("list changed status "+listChanged);
                listChanged = false;
                frame.removeListContent();
                frame.setListContent(FreeRider.serverListToArray());
            }
        }
        selectedOpponent = false; //make false for later attempts of "New Game"
        closeListHandler();
        frame.dispose();
        opponent = availableGameServerList.get(oppIndex);
        System.out.println(opponent.getUsername());
    }

    public void play() {
        socket = new Socket();
        String line;
        currentPlayer = "server";
        board.clearBoard();

        //connecting to opponent
        try {
            socket.connect(opponent.getSocketAddress());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        board.setStatusLabel(opponent.getUsername() + " will start the game");
        try {
            line = in.readLine();
            String response[];
            while (true) {
                System.out.println(line);
                response = line.split(",");
                if (response[0].equalsIgnoreCase("paint")) {
                    board.setCellImage(Integer.parseInt(response[1]), response[2]);
                } else if (response[0].equalsIgnoreCase("xWon")) {
                    board.setStatusLabel("You Lost !");
                    break;
                } else if (response[0].equalsIgnoreCase("oWon")) {
                    board.setStatusLabel("You Won !");
                    break;
                } else if (response[0].equalsIgnoreCase("draw")) {
                    board.setStatusLabel("Draw !");
                    break;
                } else if (response[0].equalsIgnoreCase("play")) {
                    currentPlayer = "rider";
                    board.setStatusLabel("Turn : Rider");
                    while (!cellSelected) {
                        System.out.print("");
                    }
                    cellSelected = false;
                    board.setStatusLabel("Turn : Server");
                    out.println(Integer.toString(board.getSelectedCellNumber()));
                    out.flush();
                    currentPlayer = "server";
                }
              /*  else if (response[0].equalsIgnoreCase("invalidCellSelection"))
                {
                    board.setStatusLabel("Pick another cell !");
                }   */

                line = in.readLine();
            }

        } catch (IOException e) {
            board.setStatusLabel("Oops! Looks like your opponent left :(");
        } catch (NullPointerException e) {
        }

        closeConnection();
        playing = false;
    }

    private void getList() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(client.getInputStream());
            try {
                Object object = objectInput.readObject();
                availableGameServerList = (ArrayList<PeerInfo>) object;
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem");
            e.printStackTrace();
        }
    }

    private void displaySelectionFrame() {
        frame = new OpponentList("Tic Tac Toe");
        frame.pack();
        frame.setVisible(true);
    }

    public static String[] serverListToArray() {
        String[] usernames = new String[availableGameServerList.size()];
        for (int i = 0; i < availableGameServerList.size(); i++) {
            usernames[i] = availableGameServerList.get(i).getUsername();
        }
        return usernames;
    }

    public void closeConnection() {
        try {
            System.out.println("Connection Closing..");
            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();

            }

        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }
    }

    public void closeListHandler() {
        listHandler.stop();
    }
}
