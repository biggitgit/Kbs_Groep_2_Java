import com.mysql.cj.protocol.Resultset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersPagina extends JFrame implements ActionListener {
    ResultSet rs,rsOL;
    JFrame OP = new JFrame("OrdersPagina");
    Color kleur = new Color(241, 194, 125);
    JButton terugKnopO,OK, JA;
    JLabel titelO,Olabel, OrderInfo, isGeretourneerd;
    JSpinner OrderSpinner;
    int CosID,OrdID;
    String JaNee;
    public OrdersPagina(){
        OP.setSize(700, 500);
        OP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OP.setLayout(null);
        OP.getContentPane().setBackground(kleur);
        //241,194,125;
        OP.setLocationRelativeTo(null);

        terugKnopO = new JButton("Terug");
        terugKnopO.setBounds(20,20,100,25);
        terugKnopO.setBackground(kleur);
        terugKnopO.setForeground(Color.black);
        terugKnopO.setBorder(BorderFactory.createLineBorder(Color.black,2));
        terugKnopO.addActionListener(this);

        titelO = new JLabel("Typ de gewenste order id:");
        titelO.setBounds(150,60,500,30);
        titelO.setFont(new Font("Verdana", Font.BOLD, 25));

        Olabel = new JLabel("Order id:");
        Olabel.setBounds(250,150,100,30);

        SpinnerModel SM = new SpinnerNumberModel(1, 1, 100, 1);
        OrderSpinner = new JSpinner(SM);
        OrderSpinner.setBounds(310,150,50,30);
        OrderSpinner.getEditor().getComponent(0).setBackground(kleur);
        ((JSpinner.DefaultEditor) OrderSpinner.getEditor()).getTextField().setEditable(false);

        OK = new JButton("OK");
        OK.setBounds(375,150,60,30);
        OK.setBackground(kleur);
        OK.setBorder(BorderFactory.createLineBorder(Color.black));
        OK.addActionListener(this);

        CosID = 0;
        OrdID = 0;
        JaNee = "Nee";
        String OrderInfoText = "OrderId: " + OrdID + "   CostumerId: " + CosID + "   Geretourneerd: " + JaNee;
        OrderInfo = new JLabel(OrderInfoText);
        OrderInfo.setBounds(200,200,300,30);

        isGeretourneerd = new JLabel("Is de order met succes geretourneerd en wilt u de voorraad veranderen?");
        isGeretourneerd.setBounds(145,300,500,30);

        JA = new JButton("Ja");
        JA.setBounds(310,350,60,30);
        JA.setBackground(kleur);
        JA.setBorder(BorderFactory.createLineBorder(Color.black));
        JA.addActionListener(this);

        OP.add(terugKnopO);
        OP.add(titelO);
        OP.add(Olabel);
        OP.add(OrderSpinner);
        OP.add(OK);
        OP.add(OrderInfo);
        OP.add(isGeretourneerd);
        OP.add(JA);

        OP.setResizable(false);
        OP.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == terugKnopO){
            OP.dispose();
            new welkom();
        }
        if (e.getSource() == OK){
            rs =  DatabaseConnection.getOrders();
            try {
                while (rs.next()){
                    int OrderID = rs.getInt("OrderID");
                    if (OrderID == (int) OrderSpinner.getValue()){
                        OrdID = (int) OrderSpinner.getValue();
                        CosID = rs.getInt("CustomerID");
                        if (rs.getInt("Geretourneerd") == 0){
                            JaNee = "Nee";
                        } else {
                            JaNee = "Ja";
                        }
                    }
                }
                String OrderInfoText = "OrderId: " + OrdID + "   CostumerId: " + CosID + "   Geretourneerd: " + JaNee;
                OrderInfo.setText(OrderInfoText);
            } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        if (e.getSource() == JA){
            rsOL = DatabaseConnection.getOrdersLines();
            JA.setBackground(Color.green);
            try {
                while (rsOL.next()) {
                    int OrderID = rsOL.getInt("OrderID");
                    if (OrderID == (int) OrderSpinner.getValue()){
                        DatabaseConnection.updateStockItemsHolding(rsOL.getInt("StockItemID"));
                    }
                }} catch (SQLException ex){

            }
        }
        }
    }
