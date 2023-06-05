import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RoutePagina extends JFrame implements ActionListener {
    private JFrame RB = new JFrame("Routebepaling");
    private JLabel gekozenS,startLabel, route,startLabelROUTE,KMroute, KMrouteTotaal, reisTijd;
    private Color kleur = new Color(241,194,125);
    private Stad startStad;
    private Stad eindStad;
    private KiesStedenPagina kF;
    private ArrayList<Stad> gekozenSteden,eindRoute;
    private JScrollPane scrollbarSteden,scrollbarStedenROUTE;
    private String stadnaam;
    private JList<String> stedenList,stedenListROUTE;
    private JButton terugKiezen;
    private double kmRoute = 0;
    private double kmTerugRoute = 0;
    private double totaalRoute = 0;

    public RoutePagina(KiesStedenPagina KiesStedenPagina){
        kF = KiesStedenPagina;

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
        startLabelROUTE = new JLabel("Start: " + startStad.getNaam());
        startLabelROUTE.setBounds(260, 50, 200, 20);





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

        DefaultListModel<String> stedenListModelROUTE = new DefaultListModel<>();
        for (int i = 1; i < eindRoute.size(); i++) {
            Stad stad = eindRoute.get(i);
            stedenListModelROUTE.addElement(i + ". " +stad.getNaam());
        }

        stedenListROUTE = new JList<>(stedenListModelROUTE);
        stedenListROUTE.setBackground(kleur);
        scrollbarStedenROUTE = new JScrollPane(stedenListROUTE);
        scrollbarStedenROUTE.setBounds(260, 80, 200, 300);

        RB.add(startLabelROUTE);
        RB.add(scrollbarStedenROUTE);

        KMroute = new JLabel("Lengte van de route:");
        KMroute.setBounds(480, 50, 200, 20);
        DecimalFormat decimalFormat1 = new DecimalFormat("#.00");
        String formattedValue = decimalFormat1.format(totaalRoute);
        KMrouteTotaal = new JLabel("Lengte in km: " + formattedValue + " km");
        KMrouteTotaal.setBounds(480, 80, 200, 20);




        double totaleTijdUren = totaalRoute/100;
        int uren = (int) totaleTijdUren;

        int minuten = (int) ((totaleTijdUren - uren)*60);

        reisTijd = new JLabel("Reistijd: " + uren + " uur en " + minuten + "  minuten");
        reisTijd.setBounds(480, 100, 200, 20);

        RB.add(KMroute);
        RB.add(KMrouteTotaal);
        RB.add(reisTijd);

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
    }public void bepaalRoute() {
        eindRoute = new ArrayList<>();
        eindRoute.add(startStad);
        Stad kleinsteStad = null;
        for (int i=1;i<gekozenSteden.size();i++) {
            Stad laatsteStad;
            for (Stad stad : gekozenSteden) {
                if (stad != startStad && stad != kleinsteStad && !eindRoute.contains(stad)) {
                    if (kleinsteStad == null) {
                        kleinsteStad = stad;
                    }
                    laatsteStad = eindRoute.get(eindRoute.size() - 1);
                    if (berekenAfstandSteden(stad.getLatitude(), stad.getLongitude(), laatsteStad.getLatitude(), laatsteStad.getLongitude())
                            < berekenAfstandSteden(kleinsteStad.getLatitude(), kleinsteStad.getLongitude(), laatsteStad.getLatitude(), laatsteStad.getLongitude())) {
                        kleinsteStad = stad;
                    }
                }
            }
            laatsteStad = eindRoute.get(eindRoute.size() - 1);
            kmRoute += (berekenAfstandSteden(kleinsteStad.getLatitude(), kleinsteStad.getLongitude(), laatsteStad.getLatitude(), laatsteStad.getLongitude()));
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
            new KiesStedenPagina();
            RB.dispose();
        }
    }
}
