package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by fabio on 08-05-2017.
 */
public class Menu extends JFrame{
    private JButton submitButton;
    private JPanel rootPanel;
    private JTextField temperature;
    private JButton exit;
    private JTextArea textArea;
    private JTextField time;
    private JTextField ph;
    private JTextField wind;
    private JTextField airH;
    private JTextField soilH;
    private JTextField conduct;

    public Menu(){
        super("Titulo");
        setContentPane(rootPanel);
        //pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        setSize(1000, 600);
        setResizable(false);
        setVisible(true);

        initButtons();
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                textArea.setText("");
                if(!temperature.getText().isEmpty())
                    Launcher.addEval("(assert (temperature (celsius " + temperature.getText() + ")))");
                if(!time.getText().isEmpty())
                    Launcher.addEval("(assert (timeDay (hours " + time.getText() + ")))");
                if(!ph.getText().isEmpty())
                    Launcher.addEval("(assert (phWater (ph " + ph.getText() + ")))");
                if(!wind.getText().isEmpty())
                    Launcher.addEval("(assert (windSpeed (velocity " + wind.getText() + ")))");
                if(!airH.getText().isEmpty())
                    Launcher.addEval("(assert (airHumidity (percentage " + airH.getText() + ")))");
                if(!soilH.getText().isEmpty())
                    Launcher.addEval("(assert (soilHumidity (percentage " + soilH.getText() + ")))");
                if(!conduct.getText().isEmpty())
                    Launcher.addEval("(assert (conductivity (water " + conduct.getText() + ")))");
                Launcher.run();
            }
        });
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

    public JTextArea getTextArea(){
        return textArea;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
