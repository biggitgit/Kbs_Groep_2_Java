import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class routeB extends JFrame {
    JFrame RB = new JFrame("Routebepaling");
    JLabel kaartLabel, km261, km306,gekozenS,startLabel;
    ImageIcon kaarNL = new ImageIcon("kaartNL.png");
    String startStad;
    kiesFrame kF;
    ArrayList<Integer> gekozenSteden;
    JComboBox ComboBoxstart;
    public routeB(kiesFrame kiesFrame){
        kF = kiesFrame;
        gekozenSteden = kF.getGekozenSteden();
        ComboBoxstart = kF.getComboBoxstart();
        RB.setSize(700,500);
        RB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RB.setLayout(null);
        RB.getContentPane().setBackground(new Color(241,194,125));
        RB.setLocationRelativeTo(null);

        kaartLabel = new JLabel();
        kaartLabel.setIcon(kaarNL);
        kaartLabel.setBounds(250,15,366,430);
        kaartLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        km261 = new JLabel("261 km");
        km261.setBounds(410,410,100,50);
        km306 = new JLabel("306 km");
        km306.setBounds(255,200,100,50);

        gekozenS = new JLabel("Gekozen steden:");
        gekozenS.setBounds(20,20,350,20);
        gekozenS.setFont(new Font("Verdana", Font.BOLD, 20));

        startStad = Objects.requireNonNull(ComboBoxstart.getSelectedItem()).toString();

        startLabel = new JLabel("Start: " + startStad);
        startLabel.setBounds(20, 50, 200, 20);

        int i = 0;
        for (int gekozenStad  : gekozenSteden) {
            String stadnaam = DatabaseConnection.getStadNaam(gekozenStad );
            assert stadnaam != null;
            if (!stadnaam.equals(startStad)) {
                JLabel gekozenStadLabel = new JLabel(stadnaam);
                gekozenStadLabel.setBounds(20, 80 + (i * 30), 150, 20);
                RB.add(gekozenStadLabel);
                i++;
            }
        }


        RB.add(kaartLabel);
        RB.add(km261);
        RB.add(km306);
        RB.add(gekozenS);
        RB.add(startLabel);

        RB.setResizable(false);
        RB.setVisible(true);
    }
}
