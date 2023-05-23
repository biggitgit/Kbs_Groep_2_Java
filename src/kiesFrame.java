import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class kiesFrame extends JFrame implements ActionListener {
    ResultSet rs;
    JFrame tF = new JFrame("Steden kiezen");
    JLabel titelK;
    ArrayList<JCheckBox> stedenCB;
    JButton routeB;
    ArrayList<String> startComboBox;
    ArrayList<Integer> gekozenSteden = new ArrayList<>();
    String[] ComboArray;
    private int aantalSteden = 0;
    JComboBox ComboBoxstart;



    public kiesFrame() {
        rs =  DatabaseConnection.DatabaseConn();

        tF.setSize(700,500);
        tF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tF.setLayout(null);
        tF.getContentPane().setBackground(new Color(241,194,125));
        tF.setLocationRelativeTo(null);

        titelK = new JLabel("Kies de steden voor uw route:");
        titelK.setBounds(20,20,350,20);
        titelK.setFont(new Font("Verdana", Font.BOLD, 20));

        routeB = new JButton("Route bepalen");
        routeB.setBounds(520,20,150,75);
        routeB.addActionListener(this);
        stedenCB = new ArrayList<>();
        startComboBox = new ArrayList<>();


        try {
            int x_CheckBox = 20;
            while (rs.next()) {
                if (aantalSteden <10){
                    x_CheckBox = 20;
                } else if (aantalSteden <20) {
                    x_CheckBox = 170;
                } else if (aantalSteden <30) {
                    x_CheckBox = 320;
                }
                String stadNaam = rs.getString("city");
                JCheckBox stadCheckbox = new JCheckBox(stadNaam);
                stadCheckbox.setBounds(x_CheckBox, 80 + (aantalSteden * 30), 150, 20);
                stadCheckbox.setBackground(new Color(241,194,125));
                tF.add(stadCheckbox);

                startComboBox.add(stadNaam);
                stedenCB.add(stadCheckbox);
                aantalSteden++;

            }
            ComboArray = startComboBox.toArray(new String[0]);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComboBoxstart = new JComboBox(ComboArray);
        ComboBoxstart.setBounds(20, 50, 150, 20);
        ComboBoxstart.setBackground(new Color(241,194,125));



        tF.add(ComboBoxstart);

        tF.add(routeB);
        tF.add(titelK);


        tF.setResizable(false);
        tF.setVisible(true);

        //tF.dispose();
    }
    public JComboBox getComboBoxstart() {
        return ComboBoxstart;
    }

    public ArrayList<Integer> getGekozenSteden() {
        return gekozenSteden;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test2");
        if (e.getSource() == routeB){
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
            for (int ignored : gekozenSteden) {
                System.out.println("Stad " + (o+1) + ": " + gekozenSteden.get(o));
                o++;
            }

            System.out.println("test3");

            tF.dispose();
            new routeB(this);
        }
        System.out.println("test4");

    }
}
