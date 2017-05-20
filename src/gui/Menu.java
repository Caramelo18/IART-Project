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
    private JTextField ph;
    private JTextField wind;
    private JTextField airH;
    private JTextField soilH;
    private JTextField conduct;
    private JComboBox timeList;
    private JTabbedPane tabbedPane1;
    private JSlider soilMin;
    private JSlider soilMax;
    private JSlider airMin;
    private JSlider airMax;
    private JSlider phMin;
    private JSlider phMax;
    private JSlider windSpeed;
    private JSlider nightTempMin;
    private JSlider nightTempMax;
    private JSlider timeMin;
    private JSlider timeMax;
    private JSlider dayTempMin;
    private JSlider dayTempMax;
    private JButton saveConfigurationButton;

    private boolean validInput = true;

    public Menu(){
        super("IART -  Sistema de Regras IOT");
        setContentPane(rootPanel);
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
                textArea.setText("");
                if(!temperature.getText().isEmpty()){
                    int temp = new Integer(temperature.getText());
                    if(temp < 0 || temp > 40){
                        textArea.setText("Invalid temperature");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (temperature (celsius " + temperature.getText() + ")))");
                }
                //if(timeList.){
                    String tmp = timeList.getSelectedItem().toString();
                    Launcher.addEval("(assert (timeDay (hours " + tmp + ")))");
               // }
                if(!ph.getText().isEmpty()){
                    double phval = new Double(ph.getText());
                    if(phval < 0.0 || phval > 14.0){
                        textArea.setText("Invalid pH");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (phWater (ph " + ph.getText() + ")))");
                }
                if(!wind.getText().isEmpty()){
                    int windVal = new Integer(wind.getText());
                    if(windVal < 0 || windVal > 150){
                        textArea.setText("Invalid wind speed");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (windSpeed (velocity " + wind.getText() + ")))");
                }
                if(!airH.getText().isEmpty()){
                    int airHVal = new Integer(airH.getText());
                    if(airHVal < 0 || airHVal > 100){
                        textArea.setText("Invalid air humidity");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (airHumidity (percentage " + airH.getText() + ")))");
                }
                if(!soilH.getText().isEmpty()){
                    int soilHVal = new Integer(soilH.getText());
                    if(soilHVal < 0 || soilHVal > 40){
                        textArea.setText("Invalid soil humidity");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (soilHumidity (percentage " + soilH.getText() + ")))");
                }
                if(!conduct.getText().isEmpty()){
                    int conductVal = new Integer(conduct.getText());
                    if(conductVal < 0 || conductVal > 40){
                        textArea.setText("Invalid conductivity");
                        validInput = false;
                    }
                    Launcher.addEval("(assert (conductivity (water " + conduct.getText() + ")))");
                }

                if(validInput)
                    Launcher.run(true);
                else
                    validInput = true;
            }
        });

        saveConfigurationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String rule = "(params (" + dayTempMin.getValue()
                        + " " + dayTempMax.getValue()
                        + " " + nightTempMin.getValue()
                        + " " + nightTempMax.getValue()
                        + " " + timeMin.getValue()
                        + " " + timeMax.getValue()
                        + " " + windSpeed.getValue()
                        + " " + phMin.getValue()
                        + " " + phMax.getValue()
                        + " " + soilMin.getValue()
                        + " " + soilMax.getValue()
                        + " " + airMin.getValue()
                        + " " + airMax.getValue()
                        + "))";
                Launcher.addEval(rule);
                Launcher.run(false);
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
