import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tracker {

    private static List<TrackerThread> onlineFreeRiderList;
    private static ArrayList<PeerInfo> availableGameServerList;

    public static void main(String args[]){


        Socket socket=null;
        ServerSocket serverSocket=null;
        int port = 9090;
        onlineFreeRiderList =  Collections.synchronizedList(new ArrayList<TrackerThread>());
        availableGameServerList = new ArrayList<PeerInfo>();
        JFrame frame = new JFrame("TrackerUI");
        frame.setContentPane(new TrackerUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        System.out.println("Server Listening......");
        try{
            serverSocket = new ServerSocket(port); // can also use static final PORT_NUM , when defined

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{
                socket= serverSocket.accept();
                System.out.println("connection Established");
                TrackerThread st=new TrackerThread(socket);
                st.start();
            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }

    //removes a server from available game server list
    protected synchronized static void removeFromAvailableList(String username)
    {
        for(int i=0;i<availableGameServerList.size();i++)
        {
            System.out.println(username.equals(availableGameServerList.get(i).getUsername()));
            if (username.equals(availableGameServerList.get(i).getUsername()))
            {
                availableGameServerList.remove(i);
                break;
            }
        }
        broadcastToFreeRiders();
    }

    //removes a free rider from free rider connection list
    protected synchronized static void removeFromOnlineRiderList(TrackerThread freeRider)
    {
        int index = onlineFreeRiderList.indexOf(freeRider);
        onlineFreeRiderList.remove(index);
    }
    //adds a game server to available game server list and broadcasts the new change to connected free riders
    protected synchronized static void addToAvailableList(PeerInfo peerInfo)
    {
        availableGameServerList.add(peerInfo);
        broadcastToFreeRiders();
    }

    protected synchronized static void addToOnlineRiderList(TrackerThread freeRider)
    {
        onlineFreeRiderList.add(freeRider);
    }

    public static ArrayList<PeerInfo> getAvailableGameServerList() {
        return availableGameServerList;
    }

    //broadcasts new changes in available game server list to online free riders
    private static void broadcastToFreeRiders()
    {
        for(int i = 0; i < onlineFreeRiderList.size(); ++i) {
            TrackerThread trackerThread = onlineFreeRiderList.get(i);
                    trackerThread.sendToClient("listModified");
        }
    }
}


class TrackerThread extends Thread{

    String line=null;
    BufferedReader in = null;
    PrintWriter out =null;
    Socket socket =null;

    public TrackerThread(Socket s){
        this.socket =s;
    }

    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out =new PrintWriter(socket.getOutputStream());
        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line= in.readLine();
            String request[] = line.split(",");
            //request = [role,username]  || request = [quit,role] || request = [onlineList]
            // request = [dismissGameServer] || request = [available,username]
            while(true){
                //request = [rider,username]
               if (request[0].equalsIgnoreCase("rider")) {
                    Tracker.addToOnlineRiderList(this);
                }
               else if(request[0].equalsIgnoreCase("available"))
               {
                   Tracker.addToAvailableList(new PeerInfo(request[1], socket.getRemoteSocketAddress()));
               }
                //request = [quit,role]
                else if (request[0].equalsIgnoreCase("quit")) {
                    if (request[1].equals("rider")) {
                        Tracker.removeFromOnlineRiderList(this);
                    }
                    break;
                }
                //request = [onlineList]
                else if (request[0].equalsIgnoreCase("onlineList")) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(Tracker.getAvailableGameServerList());
                    oos.flush();
                }
                // request = [dismissGameServer]
                else if (request[0].equalsIgnoreCase("dismissGameServer")) {
                    Tracker.removeFromAvailableList(request[1]);
                }
                line= in.readLine();
                request = line.split(",");
            }
        } catch (IOException e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
            try{
                if (in !=null){
                    in.close();
                }

                if(out !=null){
                    out.close();
                }
                if (socket !=null){
                    socket.close();
                    System.out.println("Socket Closed");
                }

            }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }

    protected void sendToClient(String msg)
    {
        out.println(msg);
        out.flush();
    }
}