import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class welkom extends JFrame implements ActionListener {
    private JFrame Welkomp = new JFrame("Welkom");


    private JLabel l1,titelWelkom;

    private JButton Routebepaling,Orders;


    private Color kleur = new Color(241, 194, 125);


    public welkom(){

       Routebepaling = new JButton("Routebepaling");
       Routebepaling.setBounds(245, 240, 90, 40);
       Routebepaling.setBackground(this.kleur);
       Routebepaling.setForeground(Color.black);
       Routebepaling.setBorder(BorderFactory.createLineBorder(Color.black, 1));
       Routebepaling.addActionListener(this);

        Orders = new JButton("Bestellingen");
        Orders.setBounds(355, 240, 90, 40);
        Orders.setBackground(this.kleur);
        Orders.setForeground(Color.black);
        Orders.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Orders.addActionListener(this);

        Welkomp.setSize(700, 500);
        Welkomp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Welkomp.setLayout(null);
        Welkomp.getContentPane().setBackground(new Color(241,194,125));
        //241,194,125;
        Welkomp.setLocationRelativeTo(null);

        titelWelkom = new JLabel("Welkom");
        titelWelkom.setBounds(255,75,300,50);
        titelWelkom.setFont(new Font("Verdana", Font.BOLD, 40));


        l1 = new JLabel("U bent succesvol ingelogd.");
        l1.setBounds(272,150,155,50);


        Welkomp.add(titelWelkom);
        Welkomp.add(l1);
        Welkomp.add(Routebepaling);
        Welkomp.add(Orders);

        Welkomp.setResizable(false);
        Welkomp.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Routebepaling) {
                Welkomp.dispose();
                new kiesFrame();
        }
        if (e.getSource() == Orders) {
            Welkomp.dispose();
            try {
                new OrdersPagina();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
