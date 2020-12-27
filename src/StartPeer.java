import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPeer {
    protected JPanel panel1;
    private JButton freeRiderButton;
    private JButton gameServerButton;
    protected JTextField textField1;
    protected static String peerRole;


    public StartPeer() {

        gameServerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                peerRole = "server";
               // System.out.println(textField1.getText().length());

                if (textField1.getText().length()>=3)
                {
                    Peer.correctSettings = true;
                    Peer.username = textField1.getText();

                }
            }
        });
        freeRiderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                peerRole = "rider";
           //     System.out.println(textField1.getText().length());

                if (textField1.getText().length()>=3)
                {
                    Peer.correctSettings = true;
                    Peer.username = textField1.getText();
                }

            }
        });
    }


    public static String getPeerRole() {
        return peerRole;
    }

}
