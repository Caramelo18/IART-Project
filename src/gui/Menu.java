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
    private JTextArea textArea;
    private JTextField ph;
    private JTextField wind;
    private JTextField airH;
    private JTextField soilH;
    private JTextField conduct;
    private JComboBox timeList;
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
    private JTextArea stats;
    private JCheckBox activate1;
    private JCheckBox activate2;
    private JCheckBox activate3;
    private JComboBox not11;
    private JComboBox temp11;
    private JComboBox not12;
    private JComboBox temp12;
    private JComboBox and11;
    private JComboBox and12;
    private JComboBox not13;
    private JComboBox temp13;
    private JRadioButton dayRadio1;
    private JButton drawGraphsButton;
    private JComboBox not21;
    private JComboBox not22;
    private JComboBox not23;
    private JComboBox not31;
    private JComboBox not32;
    private JComboBox not33;
    private JComboBox and21;
    private JComboBox and22;
    private JComboBox and31;
    private JComboBox and32;
    private JRadioButton dayRadio3;
    private JComboBox temp21;
    private JComboBox temp22;
    private JComboBox temp23;
    private JComboBox temp31;
    private JComboBox temp32;
    private JComboBox temp33;
    private JRadioButton dayRadio2;

    private boolean validInput = true;

    public Menu(){
        super("IART -  Sistema de Regras IOT");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        setSize(1000, 600);
        setResizable(false);
        setVisible(true);

        initButtons();
        drawGraphsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int numGraphs = 0;
                String[] list;
                boolean[] days;
                if(activate1.isSelected())
                    numGraphs++;
                if(activate2.isSelected())
                    numGraphs++;
                if(activate3.isSelected())
                    numGraphs++;
                if(numGraphs == 0) return;
                list = new String[numGraphs];
                days = new boolean[numGraphs];
                numGraphs = 0;

                if(activate1.isSelected()){
                    boolean day = dayRadio1.isSelected();
                    String fuzzy = "";
                    fuzzy += analyzeMain(not11, temp11);
                    fuzzy += analyzeOptional(and11, not12, temp12);
                    fuzzy += analyzeOptional(and12, not13, temp13);
                    days[numGraphs] = day;
                    list[numGraphs++] = fuzzy;
                }
                if(activate2.isSelected()){
                    boolean day = dayRadio2.isSelected();
                    String fuzzy = "";
                    fuzzy += analyzeMain(not21, temp21);
                    fuzzy += analyzeOptional(and21, not22, temp22);
                    fuzzy += analyzeOptional(and22, not23, temp23);
                    days[numGraphs] = day;
                    list[numGraphs++] = fuzzy;
                }
                if(activate3.isSelected()){
                    boolean day = dayRadio3.isSelected();
                    String fuzzy = "";
                    fuzzy += analyzeMain(not31, temp31);
                    fuzzy += analyzeOptional(and31, not32, temp32);
                    fuzzy += analyzeOptional(and32, not33, temp33);
                    days[numGraphs] = day;
                    list[numGraphs] = fuzzy;
                }

                String plot = Launcher.setFuzzyPlot(list, days);
                stats.setText(plot);
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
                String rule = "(params " + dayTempMin.getValue()
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
                        + ")";
                Launcher.addEval(rule);
                Launcher.run(false);

                Launcher.setFuzzyTemp(true, dayTempMin.getValue(), dayTempMax.getValue());
                Launcher.setFuzzyTemp(false, nightTempMin.getValue(), nightTempMax.getValue());
            }
        });
    }

    public JTextArea getTextArea(){
        return textArea;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private String analyzeMain(JComboBox notBox, JComboBox tempBox){
        String fuzzy = "";
        if(!notBox.getSelectedItem().toString().isEmpty()){
            fuzzy += "not ";
        }
        fuzzy += tempBox.getSelectedItem().toString();
        return fuzzy;
    }

    private String analyzeOptional(JComboBox andBox, JComboBox notBox, JComboBox tempBox){
        String fuzzy = "";
        if(!andBox.getSelectedItem().toString().isEmpty()){
            fuzzy += " " + and11.getSelectedItem().toString() + " ";
            fuzzy += analyzeMain(notBox, tempBox);
        }
        return fuzzy;
    }
}
