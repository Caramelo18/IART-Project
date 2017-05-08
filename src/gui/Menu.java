package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fabio on 08-05-2017.
 */
public class Menu extends JFrame{
    private JButton submitButton;
    private JPanel rootPanel;
    private JTextField temperature;
    private JButton exit;

    public Menu(){
        super("Titulo");
        setContentPane(rootPanel);
        //pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        setSize(1000, 600);
        setResizable(false);
        setVisible(true);

        initButtons();
    }

    private void initButtons(){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
