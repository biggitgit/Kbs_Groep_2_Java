import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class rbAP extends JFrame implements ActionListener {
    ResultSet rs;

    JFrame loginP = new JFrame("Inloggen");
    JFrame tF = new JFrame("Steden kiezen");
    JFrame RB = new JFrame("Routebepaling");
    String wachtwoord1 = "";
    JLabel l1,l2,kaartLabel, km261, km306, routeF,titelInloggen,gekozenS;
    JPasswordField p1;
    JButton b1,b2;
    ArrayList<JCheckBox> stedenCB;
    ArrayList<Integer> gekozenSteden;

    ImageIcon kaarNL = new ImageIcon("kaartNL.png");
    ImageIcon routeFoto = new ImageIcon("routeFoto.png");

private int aantalSteden = 0;
    public double berekenAfstandSteden(double breedteGraad1,double lengteGraad1,double breedteGraad2,double lengteGraad2) {

        double BreedtegraadStart = (Math.PI / 180) * (breedteGraad2-breedteGraad1);
        double LengtegraadStart = (Math.PI / 180) * (lengteGraad2-lengteGraad1);

        double start = Math.sin(BreedtegraadStart/2) * Math.sin(BreedtegraadStart/2) +
                       Math.cos((Math.PI / 180) * (breedteGraad1)) *
                       Math.cos((Math.PI / 180) * (breedteGraad2)) *
                       Math.sin(LengtegraadStart / 2) *
                       Math.sin(LengtegraadStart / 2);

        double eind = 2 * Math.atan2(Math.sqrt(start), Math.sqrt(1-start));

        return eind * 6371; //Eindgetal X radius van de aarde in KM
    }


    public rbAP(){

        rs =  DatabaseConnection.DatabaseConn();

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

    public void kiesFrame() {
        tF.setSize(700,500);
        tF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tF.setLayout(null);
        tF.getContentPane().setBackground(new Color(241,194,125));
        tF.setLocationRelativeTo(null);

        l2 = new JLabel("Kies de steden voor uw route:");
        l2.setBounds(20,20,350,20);
        l2.setFont(new Font("Verdana", Font.BOLD, 20));

        b2 = new JButton("Route bepalen");
        b2.setBounds(520,20,150,75);
        b2.addActionListener(this);
        try {
            stedenCB = new ArrayList<>();
            while (rs.next()) {
                String stadNaam = rs.getString("stad_naam");
                JCheckBox stadCheckbox = new JCheckBox(stadNaam);
                stadCheckbox.setBounds(20, 50 + (aantalSteden * 30), 200, 20);
                stadCheckbox.setBackground(new Color(241,194,125));
                tF.add(stadCheckbox);

                stedenCB.add(stadCheckbox);
                aantalSteden++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        tF.add(b2);
        tF.add(l2);


        tF.setResizable(false);
        tF.setVisible(true);
    }

    public void RBframe(){
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
        int i = 0;
            for (int gekozenStad  : gekozenSteden) {

                String stadnaam = DatabaseConnection.getStadNaam(gekozenStad );
                JLabel gekozenStadLabel = new JLabel(stadnaam);
                gekozenStadLabel.setBounds(20, 50 + (i * 30), 100, 20);
                RB.add(gekozenStadLabel);
                i++;
            }


        RB.add(kaartLabel);
        RB.add(km261);
        RB.add(km306);
        RB.add(gekozenS);

        RB.setResizable(false);
        RB.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1){
            String ingevuldWW = new String(p1.getPassword());

            if (ingevuldWW.equals(wachtwoord1)){
                loginP.dispose();
                kiesFrame();
            } else {
                b1.setBackground(Color.red);
            }
        }

        if (e.getSource() == b2){
            gekozenSteden = new ArrayList<>();
            int i,o;
            i=1;
            o=0;
            for (JCheckBox CB: stedenCB) {
                if (CB.isSelected()){
                    gekozenSteden.add(i);
                }
                i++;
            }

            System.out.println("Gekozen Steden:");
            for (int gS: gekozenSteden) {
                System.out.println("Stad " + (o+1) + ": " + gekozenSteden.get(o));
                o++;
            }

            tF.dispose();
            RBframe();
        }

    }
}
