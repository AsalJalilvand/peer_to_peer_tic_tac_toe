import java.io.Serializable;
import java.net.*;

public class PeerInfo implements Serializable {
    String username;
    SocketAddress socketAddress;
    //  int port;

    public PeerInfo(String username, SocketAddress socketAddress
                    //, int port
    ) {
        this.username = username;
        this.socketAddress = socketAddress;
        //  this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }


}
