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
    Stad eindStad;
    kiesFrame kF;
    ArrayList<Stad> gekozenSteden,eindRoute;
    JScrollPane scrollbarSteden;
    String stadnaam;
    JList<String> stedenList;
    JButton terugKiezen;
    double kmRoute = 0;
    double kmTerugRoute = 0;
    double totaalRoute = 0;

    public routeB(kiesFrame kiesFrame){
        kF = kiesFrame;

        gekozenSteden = kF.getGekozenSteden();
        if (gekozenSteden.get(0) != null){
            startStad = gekozenSteden.get(0);
            eindStad = gekozenSteden.get(gekozenSteden.size() - 1);
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


        bepaalRoute();
        RB.setResizable(false);
        RB.setVisible(true);
    }

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
    public void bepaalRoute() {
        eindRoute = new ArrayList<>();
        eindRoute.add(startStad);
        Stad kleinsteStad = null;
        for (int i=1;i<gekozenSteden.size();i++) {
            for (Stad stad : gekozenSteden) {
                if (stad != startStad && stad != kleinsteStad && !eindRoute.contains(stad)) {
                    if (kleinsteStad == null) {
                        kleinsteStad = stad;
                    }
                    Stad laatsteStad = eindRoute.get(eindRoute.size() - 1);
                        if (berekenAfstandSteden(stad.getLatitude(), stad.getLongitude(), laatsteStad.getLatitude(), laatsteStad.getLongitude())
                                < berekenAfstandSteden(kleinsteStad.getLatitude(), kleinsteStad.getLongitude(), laatsteStad.getLatitude(), laatsteStad.getLongitude())) {
                            kleinsteStad = stad;
                        }
                    }
            }
            Stad LLstad = eindRoute.get(eindRoute.size() - 1);
            kmRoute += (berekenAfstandSteden(kleinsteStad.getLatitude(), kleinsteStad.getLongitude(), LLstad.getLatitude(), LLstad.getLongitude()));
            kmTerugRoute = (berekenAfstandSteden(eindStad.getLatitude(), eindStad.getLongitude(),startStad.getLatitude(), startStad.getLongitude()));
            totaalRoute = kmRoute + kmTerugRoute;
            eindRoute.add(kleinsteStad) ;// + terug km naar start stad
            kleinsteStad = null;
        }
        eindRoute.add(startStad);
        for (Stad stad:eindRoute) {
            System.out.println(stad.getNaam());
        }
        System.out.println("Totaal "+totaalRoute+" KM");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == terugKiezen){
            new kiesFrame();
            RB.dispose();
        }
    }
}
