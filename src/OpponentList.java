import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OpponentList extends JFrame {
    protected JList list1;
    protected JPanel panel;
    private JLabel header;
    protected JScrollPane listScrollPane;
    protected JButton select;

    public OpponentList(String title) {
        super(title);
        setSize(200, 350);
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        list1 = new JList();
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setVisibleRowCount(15);
        panel = new JPanel();
        header = new JLabel("Select Opponent");
        select = new JButton("Let's Get Started!");

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FreeRider.oppIndex = list1.getSelectedIndex();
            }
        });
        select.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FreeRider.selectedOpponent = true;
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FreeRider.closedList = true;
                e.getWindow().dispose();
            }
        });

        //add(panel) ;
        add(header, BorderLayout.NORTH);
        add(list1, BorderLayout.CENTER);
        add(select, BorderLayout.SOUTH);
        listScrollPane = new JScrollPane(list1);
        add(listScrollPane);
    }

    public void setListContent(String[] content) {
        list1.setListData(content);
    }

    public void removeListContent() {
        // System.out.println("removing");
        list1.removeAll();
    }


}
