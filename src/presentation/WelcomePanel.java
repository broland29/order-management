package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

/**The home-panel of the application
 * <p>Displayed on the start-up of the application</p>
 * <p>Offers the user three operation choices: on clients, on products or on orders</p>
 */
public class WelcomePanel extends JPanel{

    JButton clientButton;
    JButton productButton;
    JButton orderButton;

    /** Basic constructor of WelcomePanel */
    public WelcomePanel(){
        this.setLayout(new GridLayout(3,1,0,10));
        this.setBackground(new Color(250, 237, 205));
        this.setBorder(BorderFactory.createMatteBorder(50,150,50,150,new Color(250, 237, 205)));

        clientButton = new JButton("Client Management");
        productButton = new JButton("Product Management");
        orderButton = new JButton("Order Management");

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(clientButton);
        buttons.add(productButton);
        buttons.add(orderButton);

        for (JButton b : buttons){
            b.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
            b.setFocusable(false);
            b.setBackground(new Color(233, 237, 201));
            b.setForeground(new Color(66, 37, 24));
            this.add(b);
        }
    }

    /** Adds action listeners to client-, product- and order buttons*/
    public void addActionListener(ActionListener al){
        clientButton.addActionListener(al);
        productButton.addActionListener(al);
        orderButton.addActionListener(al);
    }
}
