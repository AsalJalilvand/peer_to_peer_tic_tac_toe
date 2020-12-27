/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 12/11/16
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuitHandler extends Thread {
    GameServer gameServer;

    public QuitHandler(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void run() {
        while (!gameServer.quitRequest) {
            System.out.print("");
        }
        gameServer.availableNoMore();
        System.exit(0);
    }
}
