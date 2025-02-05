import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class InlogPagina extends JFrame implements ActionListener {
    private ResultSet rs;

    private JFrame loginP = new JFrame("Inloggen");
    private JLabel l1, routeF,titelInloggen;
    private JPasswordField p1;
    private JButton b1;
    private ImageIcon routeFoto = new ImageIcon("routeFoto.png");


    public InlogPagina(){

        rs =  DatabaseConnection.getCities();

        loginP.setSize(700, 500);
        loginP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginP.setLayout(null);
        loginP.getContentPane().setBackground(new Color(241,194,125));
        //241,194,125;
        loginP.setLocationRelativeTo(null);

        titelInloggen = new JLabel("PathFinder");
        titelInloggen.setBounds(220,75,300,50);
        titelInloggen.setFont(new Font("Verdana", Font.BOLD, 40));


        routeF = new JLabel();
        routeF.setIcon(routeFoto);
        routeF.setBounds(0,0,700,468);

        l1 = new JLabel("Vul het wachtwoord in:");
        l1.setBounds(275,150,150,50);

        p1 = new JPasswordField();
        p1.setBounds(250,200,180,30);

        b1 = new JButton("OK");
        b1.setBounds(300,240,75,25);


        loginP.add(titelInloggen);

        loginP.add(l1);

        loginP.add(p1);

        loginP.add(b1);

        loginP.add(routeF);


        b1.addActionListener(this);
        p1.addActionListener(this);

        loginP.setResizable(false);
        loginP.setVisible(true);
    }








    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String ingevuldWW = new String(p1.getPassword());
                if (ingevuldWW.equals(System.getenv("PASS_inlog"))) {
                    loginP.dispose();
                    new WelkomPagina();
                } else {
                    p1.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
                }
        }
    }
}
