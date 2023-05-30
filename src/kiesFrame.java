import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class kiesFrame extends JFrame implements ActionListener {
    JFrame tF = new JFrame("Steden kiezen");
    JLabel titelK, fotoDots,extraInfZoek,voegMin2, aantalSteden;
JTextField zoekField;
    JButton routeB, voegToeStad, resetKnop;
    ArrayList<Stad> gekozenSteden = new ArrayList<>();
    ImageIcon routeFoto = new ImageIcon("dotsFoto.png");
    Color kleur = new Color(241,194,125);


    public kiesFrame() {
        tF.setSize(700,500);
        tF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tF.setLayout(null);
        tF.getContentPane().setBackground(kleur);// #F1C27D
        tF.setLocationRelativeTo(null);

        titelK = new JLabel("Kies de steden voor uw route:");
        titelK.setBounds(120,60,500,30);
        titelK.setFont(new Font("Verdana", Font.BOLD, 25));

        routeB = new JButton("Route bepalen");
        routeB.setBounds(470,350,150,75);
        routeB.setBackground(kleur);
        routeB.setForeground(Color.black);
        routeB.setBorder(BorderFactory.createLineBorder(Color.black,2));
        routeB.addActionListener(this);

        zoekField = new JTextField();
        zoekField.setBounds(155,150,250,40);

        extraInfZoek = new JLabel("*Vul de start-stad als eerst in!");
        extraInfZoek.setBounds(155,120,200,40);

        voegToeStad = new JButton("Voeg Toe");
        voegToeStad.setBounds(410,150,90,40);
        voegToeStad.setBackground(kleur);
        voegToeStad.setForeground(Color.black);
        voegToeStad.setBorder(BorderFactory.createLineBorder(Color.black,1));
        voegToeStad.addActionListener(this);

        fotoDots = new JLabel();
        fotoDots.setIcon(routeFoto);
        fotoDots.setBounds(35,130,600,310);

        voegMin2 = new JLabel("*Minimaal 2 steden toegevoegt");
        voegMin2.setBounds(155,180,200,40);
        voegMin2.setForeground(Color.RED);

        resetKnop = new JButton("Reset steden");
        resetKnop.setBounds(60,350,150,75);
        resetKnop.setForeground(Color.black);
        resetKnop.setBackground(Color.red);
        resetKnop.addActionListener(this);

        aantalSteden = new JLabel("Aantal steden: " + gekozenSteden.size());
        aantalSteden.setBounds(470,425,200,40);

        tF.add(zoekField);
        tF.add(routeB);
        tF.add(titelK);
        tF.add(voegToeStad);
        tF.add(fotoDots);
        tF.add(extraInfZoek);
        tF.add(voegMin2);
        tF.add(resetKnop);
        tF.add(aantalSteden);

        tF.setResizable(false);
        tF.setVisible(true);

        //tF.dispose();
    }

    public boolean stadChecker(String stadNaam) {
        boolean aanWezig = false;
        for (Stad stad: gekozenSteden) {
            if (stad.getNaam().equalsIgnoreCase(stadNaam)) {
                aanWezig = true;
                System.out.println("Stad is al toegevoegt: "  + zoekField.getText());
                break;
            }
        }
        if (!aanWezig) {
            try (ResultSet rs = DatabaseConnection.DatabaseConn()) {
                while (true) {
                    assert rs != null;
                    if (!rs.next()) break;
                    String city = rs.getString("city");
                    double latitude = rs.getDouble("lat");
                    double longitude = rs.getDouble("lng");
                    if (city.equalsIgnoreCase(stadNaam)) {
                        System.out.println("Stad toegevoegt: "  + zoekField.getText());
                        gekozenSteden.add(new Stad(stadNaam, latitude, longitude));
                        if (gekozenSteden.size() > 1){
                            voegMin2.setForeground(Color.green);
                        } else {
                            voegMin2.setForeground(Color.red);
                        }
                        aanWezig = true;
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return aanWezig;
    }

    public ArrayList<Stad> getGekozenSteden() {
        return gekozenSteden;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == routeB){
            if (gekozenSteden.size() > 1){
            tF.dispose();
            new routeB(this);
            }
        }
        if (e.getSource() == voegToeStad){
            if (!stadChecker(zoekField.getText())){
                if (!zoekField.getText().equals("")) {
                    System.out.println("Stad niet gevonden: " + zoekField.getText());
                } else {
                    System.out.println("Niets ingevuld");
                }
            }
            zoekField.setText("");
            aantalSteden.setText("Aantal steden: " + gekozenSteden.size());
            }
        if (e.getSource() == resetKnop){
            System.out.println("Steden gereset");
            gekozenSteden.clear();
            voegMin2.setForeground(Color.red);
            aantalSteden.setText("Aantal steden: " + gekozenSteden.size());
        }


    }
}
