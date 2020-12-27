
public class PeerMain {
    public static void main(String[] args) {
        String role = "";
        Peer peer;
        boolean quit = false;
        //set a username and select role in network
        role = Peer.start();
        if (role.equals("server")) {
            peer = new GameServer();
        } else {
            peer = new FreeRider();
        }
        peer.displayBoard();
        while (true) {
            System.out.print("");

            if (peer.newGameRequest) {

                if (peer.connectToTracker()) {
                    peer.newGameRequest = false;
                    if (peer instanceof FreeRider) {
                        ((FreeRider) peer).selectOpponent();
                        peer.quit();
                        if (FreeRider.closedList) {
                            FreeRider.closedList = false;
                            ((FreeRider) peer).closeListHandler();
                            continue;
                        }
                        ((FreeRider) peer).play();
                    } else if (peer instanceof GameServer) {
                        ((GameServer) peer).available();
                        //game server in still in available game server list in tracker but closes tcp connection
                        ((GameServer) peer).setServerPort(Peer.client.getLocalPort());
                        peer.quit();
                        ((GameServer) peer).play();
                        System.out.println("game end");
                    }
                } else
                    peer.board.setStatusLabel("Oops! It looks like tracker is unavailable");
            }
        }

    }
}
