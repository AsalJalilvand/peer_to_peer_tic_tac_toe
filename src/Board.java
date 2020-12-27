import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JFrame {
    protected JPanel cellBoardPanel;
    protected JPanel panel;
    protected JPanel header;

    private JLabel status;
    private JLabel greeting;

    private JButton newGameButton;
    private JButton quitButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;

    private int selectedCellNumber;

    public Board(String title) {
        super(title);
        setSize(430, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension cellD = new Dimension(100, 100);
        Color cellC = new Color(255, 255, 153);
        Color btnC = new Color(123, 219, 255);
        Color panelC = new Color(0, 153, 255);
        Color lblC = new Color(26, 0, 105);
        status = new JLabel("\n");
        greeting = new JLabel("Hello," + Peer.username + " !");
        cellBoardPanel = new JPanel();
        panel = new JPanel();
        header = new JPanel();
        newGameButton = new JButton("New Game");
        quitButton = new JButton("Quit");
        button1 = new JButton("");
        button2 = new JButton("");
        button3 = new JButton("");
        button4 = new JButton("");
        button5 = new JButton("");
        button6 = new JButton("");
        button7 = new JButton("");
        button8 = new JButton("");
        button9 = new JButton("");

        button1.setPreferredSize(cellD);
        button2.setPreferredSize(cellD);
        button3.setPreferredSize(cellD);
        button4.setPreferredSize(cellD);
        button5.setPreferredSize(cellD);
        button6.setPreferredSize(cellD);
        button7.setPreferredSize(cellD);
        button8.setPreferredSize(cellD);
        button9.setPreferredSize(cellD);
        button1.setBackground(cellC);
        button2.setBackground(cellC);
        button3.setBackground(cellC);
        button4.setBackground(cellC);
        button5.setBackground(cellC);
        button6.setBackground(cellC);
        button7.setBackground(cellC);
        button8.setBackground(cellC);
        button9.setBackground(cellC);
        quitButton.setPreferredSize(new Dimension(110, 30));
        newGameButton.setPreferredSize(new Dimension(110, 30));
        quitButton.setBackground(btnC);
        newGameButton.setBackground(btnC);
        status.setBackground(panelC);
        panel.setBackground(panelC);
        cellBoardPanel.setBackground(panelC);
        status.setForeground(lblC);
        greeting.setBackground(panelC);
        greeting.setForeground(lblC);
        header.setBackground(panelC);
        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Peer.playing)
                    Peer.newGameRequest = true;
            }
        });
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Peer.quitRequest = true;
                if (Peer.role.equals("server")) {
                    GameServer.availableNoMore();
                }
                System.exit(0);
            }
        });

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 1;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }
            }
        });
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 2;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }
            }
        });
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 3;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }
            }
        });
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 4;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }

            }
        });
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 5;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }

            }
        });
        button6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 6;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }

            }
        });
        button7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 7;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }
            }
        });
        button8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 8;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }

            }
        });
        button9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    if (Peer.currentPlayer.equals(Peer.role)) {
                        selectedCellNumber = 9;
                        Peer.cellSelected = true;
                    }
                } catch (NullPointerException ex) {
                }


            }
        });


        cellBoardPanel.setLayout(new GridLayout(3, 3));
        cellBoardPanel.add(button1);
        cellBoardPanel.add(button2);
        cellBoardPanel.add(button3);
        cellBoardPanel.add(button4);
        cellBoardPanel.add(button5);
        cellBoardPanel.add(button6);
        cellBoardPanel.add(button7);
        cellBoardPanel.add(button8);
        cellBoardPanel.add(button9);

        panel.setLayout(new BorderLayout());
        panel.add(status, BorderLayout.NORTH);
        panel.add(quitButton, BorderLayout.WEST);
        panel.add(newGameButton, BorderLayout.EAST);

        header.add(greeting);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(header, BorderLayout.NORTH);
        c.add(cellBoardPanel, BorderLayout.CENTER);
        c.add(panel, BorderLayout.SOUTH);
    }

    public int getSelectedCellNumber() {
        return selectedCellNumber;
    }

    public void setStatusLabel(String msg) {
        status.setText(msg);
    }

    public void setCellImage(int cellNum, String seed) {
        ImageIcon s = new ImageIcon(seed + ".png");
        switch (cellNum) {
            case 1:
                button1.setIcon(s);
                break;
            case 2:
                button2.setIcon(s);
                break;
            case 3:
                button3.setIcon(s);
                break;
            case 4:
                button4.setIcon(s);
                break;
            case 5:
                button5.setIcon(s);
                break;
            case 6:
                button6.setIcon(s);
                break;
            case 7:
                button7.setIcon(s);
                break;
            case 8:
                button8.setIcon(s);
                break;
            case 9:
                button9.setIcon(s);
                break;
        }
    }

    public void clearBoard() {
        for (int i = 1; i < 10; i++) {
            setCellImage(i, "empty");
        }
    }
}
