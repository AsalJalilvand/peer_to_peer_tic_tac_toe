import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ListHandler extends Thread {
    boolean cancel;
    BufferedReader in = null;

    public void run() {
        String response = "";
        try {
            in = new BufferedReader(new InputStreamReader(Peer.client.getInputStream()));
        } catch (IOException e) {
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                response = in.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                if (response.equals("listModified")) {
                    Peer.sendRequestToTracker("onlineList");
                    getList();
                    FreeRider.listChanged = true;
                }


            }
        }
    }

    private void getList() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(Peer.client.getInputStream());
            try {
                Object object = objectInput.readObject();
                if (FreeRider.availableGameServerList != null)
                    FreeRider.availableGameServerList = (ArrayList<PeerInfo>) object;
            } catch (ClassNotFoundException e) {
                System.out.println("The server list has not come from the server");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem");
            e.printStackTrace();
        }
    }

}
