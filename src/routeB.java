import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Objects;

public class routeB extends JFrame implements ActionListener {
    JFrame RB = new JFrame("Routebepaling");
    JLabel kaartLabel, km261, km306,gekozenS,startLabel, route;
    ImageIcon kaarNL = new ImageIcon("kaartNL.png");
    Color kleur = new Color(241,194,125);
    Stad startStad;
    kiesFrame kF;
    ArrayList<Stad> gekozenSteden;
    JScrollPane scrollbarSteden;
    String stadnaam;
    JList<String> stedenList;
    JButton terugKiezen;

    public routeB(kiesFrame kiesFrame){
        kF = kiesFrame;
        gekozenSteden = kF.getGekozenSteden();
        if (gekozenSteden.get(0) != null){
            startStad = gekozenSteden.get(0);
            stadnaam = startStad.getNaam();
        } else {
            stadnaam = "geen stad gekozen";
        }
        RB.setSize(700,500);
        RB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RB.setLayout(null);
        RB.getContentPane().setBackground(kleur);
        RB.setLocationRelativeTo(null);

        gekozenS = new JLabel("Gekozen steden:");
        gekozenS.setBounds(20,20,350,20);
        gekozenS.setFont(new Font("Verdana", Font.BOLD, 20));

        route= new JLabel("Route:");
        route.setBounds(260,20,350,20);
        route.setFont(new Font("Verdana", Font.BOLD, 20));

        startLabel = new JLabel("Start: " + startStad.getNaam());
        startLabel.setBounds(20, 50, 200, 20);





        DefaultListModel<String> stedenListModel = new DefaultListModel<>();
        for (int i = 1; i < gekozenSteden.size(); i++) {
            Stad stad = gekozenSteden.get(i);
            stedenListModel.addElement(stad.getNaam());
        }

        stedenList = new JList<>(stedenListModel);
        stedenList.setBackground(kleur);
        scrollbarSteden = new JScrollPane(stedenList);
        scrollbarSteden.setBounds(20, 80, 200, 300);

        terugKiezen = new JButton("Opnieuw kiezen");
        terugKiezen.setBounds(55, 390, 130, 40);
        terugKiezen.addActionListener(this);

        JPanel verticaleLijn = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawLine(240,0,240,500);
            }
        };
        verticaleLijn.setBounds(240, 0, 3, 500);
        verticaleLijn.setBackground(Color.black);


        RB.add(scrollbarSteden);
        RB.add(gekozenS);
        RB.add(route);
        RB.add(startLabel);
        RB.add(terugKiezen);
        RB.add(verticaleLijn);

        RB.setResizable(false);
        RB.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == terugKiezen){
            new kiesFrame();
            RB.dispose();
        }
    }
}
