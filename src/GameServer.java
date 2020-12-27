import java.io.*;
import java.net.*;

public class GameServer extends Peer {

    int serverPort;
    int seed;
    Socket socket = null;
    ServerSocket serverSocket = null;
    XO xo = null;
    QuitHandler quitHandler;

    public void play() {
        String icon = null;
        quitHandler = new QuitHandler(this);
        board.clearBoard();
        try {
            serverSocket = new ServerSocket(serverPort);
            quitHandler.start();
            board.setStatusLabel("Waiting for an opponent...");
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        quitHandler.stop();
        availableNoMore();

        try {
            xo = new XO();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            currentPlayer = role;
            seed = xo.CROSS;

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        xo.initGame();
        do {
            getCellNum();
            while (!xo.playerMove(seed, currentCellNum)) {
                if (socket.isClosed()) {
                    board.setStatusLabel("Oops! Looks like your opponent left :(");
                    return;
                }
                getCellNum();
            }
            if (currentPlayer.equals("rider"))
                sendToFreeRider("validCellSelection");

            System.out.println(currentCellNum + " " + currentPlayer);

            xo.updateGame(seed, currentCellNum); // update currentState

            if (currentPlayer.equals(role))
                icon = "x";
            else
                icon = "o";

            board.setCellImage(currentCellNum, icon);
            sendToFreeRider("paint," + currentCellNum + "," + icon);

            // Print message if game-over
            if (xo.currentState == xo.CROSS_WON) {
                board.setStatusLabel("You Won!");
                sendToFreeRider("xWon");
            } else if (xo.currentState == xo.NOUGHT_WON) {
                board.setStatusLabel("You Lost!");
                sendToFreeRider("oWon");
            } else if (xo.currentState == xo.DRAW) {
                board.setStatusLabel("Draw!");
                sendToFreeRider("draw");
            }
            // Switch player
            setSeed();
        }
        while (xo.currentState == xo.PLAYING);
        closeConnection();
        playing = false;
    }

    public void setServerPort(int port) {
        serverPort = port;
    }

    public void available() {
        sendRequestToTracker("available," + username);
    }

    public static void availableNoMore() {
        if (connectToTracker()) {
            sendRequestToTracker("dismissGameServer," + username);
            quit();
        }
    }

    public void getCellNum() {

        if (currentPlayer.equals("server")) {
            board.setStatusLabel("Turn : Server");
            while (!cellSelected) {
                System.out.print("");
            }
            board.setStatusLabel("Turn : Rider");
            cellSelected = false;
            currentCellNum = board.getSelectedCellNumber();
            return;
        }
        String line = null;
        try {
            System.out.println("waiting for o to pick a cell");
            sendToFreeRider("play");
            line = in.readLine();
        } catch (IOException e) {
            closeConnection();
            return;
        }
        currentCellNum = Integer.parseInt(line);
    }

    public void sendToFreeRider(String msg) {
        out.println(msg);
        out.flush();
    }

    public void setSeed() {
        if (currentPlayer.equals(role)) {
            seed = xo.NOUGHT;
            currentPlayer = "rider";
        } else {
            seed = xo.CROSS;
            currentPlayer = role;
        }
        System.out.println("setSeed -- current player = " + currentPlayer);
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
                serverSocket.close();
            }

        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }
    }
}
