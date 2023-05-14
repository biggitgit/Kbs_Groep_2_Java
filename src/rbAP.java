import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class rbAP extends JFrame implements ActionListener {

    JFrame loginP = new JFrame("Inloggen");
    JFrame tF = new JFrame("Steden kiezen");
    JFrame RB = new JFrame("Routebepaling");
    String wachtwoord1 = "hallo";
    JLabel l1,l2,kaartLabel, km261, km306;
    JPasswordField p1;
    JButton b1,b2;
    JCheckBox s1,s2,s3,s4;
    JCheckBox[] stedenCB;
    ArrayList<Integer> gekozenSteden = new ArrayList<>();

    ImageIcon kaarNL = new ImageIcon("kaartNL.png");
    public rbAP(){
        loginP.setSize(700, 500);
        loginP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginP.setLayout(null);
        loginP.getContentPane().setBackground(new Color(241,194,125));
        //241,194,125;
        l1 = new JLabel("Vul het wachtwoord in:");
        l1.setBounds(275,100,150,50);

        p1 = new JPasswordField();
        p1.setBounds(250,150,180,50);

        b1 = new JButton("OK");
        b1.setBounds(300,210,75,25);


        loginP.add(l1);

        loginP.add(p1);

        loginP.add(b1);

        b1.addActionListener(this);
        p1.addActionListener(this);

        loginP.setResizable(false);
        loginP.setVisible(true);
    }

    public void kiesFrame(){
        tF.setSize(700,500);
        tF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tF.setLayout(null);
        tF.getContentPane().setBackground(new Color(241,194,125));

        l2 = new JLabel("Kies de steden voor uw route:");
        l2.setBounds(20,20,200,20);

        b2 = new JButton("Route bepalen");
        b2.setBounds(520,20,150,75);
        b2.addActionListener(this);

        s1 = new JCheckBox("Almere");
        s1.setBounds(20,50,100,20);
        s2 = new JCheckBox("Amsterdam");
        s2.setBounds(20,80,100,20);
        s3 = new JCheckBox("Den haag");
        s3.setBounds(20,110,100,20);
        s4 = new JCheckBox("Rotterdam");
        s4.setBounds(20,140,100,20);

        stedenCB = new JCheckBox[]{s1, s2, s3, s4};

        tF.add(b2);
        tF.add(l2);
        tF.add(s1);
        tF.add(s2);
        tF.add(s3);
        tF.add(s4);

        tF.setResizable(false);
        tF.setVisible(true);
    }

    public void RBframe(){
        RB.setSize(700,500);
        RB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RB.setLayout(null);
        RB.getContentPane().setBackground(new Color(241,194,125));

        kaartLabel = new JLabel();
        kaartLabel.setIcon(kaarNL);
        kaartLabel.setBounds(250,15,366,430);
        kaartLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        km261 = new JLabel("261 km");
        km261.setBounds(410,410,100,50);
        km306 = new JLabel("306 km");
        km306.setBounds(255,200,100,50);

        RB.add(kaartLabel);
        RB.add(km261);
        RB.add(km306);

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
            int i = 0;
            for (JCheckBox stad: stedenCB) {
                if (stad.isSelected()){
                    gekozenSteden.add(i+1);
                }
                i++;
            }
            tF.dispose();
            RBframe();
        }

    }
}
