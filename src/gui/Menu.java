package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.UIManager;

public class Menu extends JFrame{
    private JButton submitButton;
    private JPanel rootPanel;
    private JTextField temperature;
    private JTextField outTemperature;
    private JButton exit;
    private JTextArea textArea;
    private JTextField ph;
    private JTextField wind;
    private JTextField airH;
    private JTextField outAirH;
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
    private JComboBox temp21;
    private JComboBox temp22;
    private JComboBox temp23;
    private JComboBox temp31;
    private JComboBox temp32;
    private JComboBox temp33;
    private JTextField city;
    private JButton getDataFromCityButton;

    private boolean validInput = true;
    private boolean validWeatherInput = true;

    Menu(){
        super("IART -  Sistema de Regras IOT");

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }  catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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

                try {
                    textArea.setText("");
                    validWeatherInput = true;
                    if (outAirH.getText().isEmpty()
                         || outTemperature.getText().isEmpty()
                         || wind.getText().isEmpty()){
                        validWeatherInput = false;
                    }

                    if(validWeatherInput){
                        String rule = "(params2 " + outTemperature.getText()
                                + " " + outAirH.getText()
                                + " " + wind.getText()
                                + ")";
                        Launcher.addEval(rule);
                        Launcher.run(false);
                    } else {
                        textArea.setText("Invalid weather conditions\n");
                    }

                    if (!temperature.getText().isEmpty()) {
                        double temp = new Double(temperature.getText());
                        if (temp < 0 || temp > 40) {
                            textArea.setText("Invalid temperature");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (temperature (celsius " + temperature.getText() + ")))");
                    }
                    //if(timeList.){
                    String tmp = timeList.getSelectedItem().toString();
                    Launcher.addEval("(assert (timeDay (hours " + tmp + ")))");
                    // }
                    if (!ph.getText().isEmpty()) {
                        double phval = new Double(ph.getText());
                        if (phval < 0.0 || phval > 14.0) {
                            textArea.setText("Invalid pH");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (phWater (ph " + ph.getText() + ")))");
                    }
                    if (!wind.getText().isEmpty()) {
                        double windVal = new Double(wind.getText());
                        if (windVal < 0 || windVal > 150) {
                            textArea.setText("Invalid wind speed");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (windSpeed (velocity " + wind.getText() + ")))");
                    }
                    if (!airH.getText().isEmpty()) {
                        double airHVal = new Double(airH.getText());
                        if (airHVal < 0 || airHVal > 100) {
                            textArea.setText("Invalid air humidity");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (airHumidity (percentage " + airH.getText() + ")))");
                    }
                    if (!soilH.getText().isEmpty()) {
                        double soilHVal = new Double(soilH.getText());
                        if (soilHVal < 0 || soilHVal > 40) {
                            textArea.setText("Invalid soil humidity");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (soilHumidity (percentage " + soilH.getText() + ")))");
                    }
                    if (!conduct.getText().isEmpty()) {
                        double conductVal = new Double(conduct.getText());
                        if (conductVal < 0 || conductVal > 100) {
                            textArea.setText("Invalid conductivity");
                            validInput = false;
                        }
                        Launcher.addEval("(assert (conductivity (water " + conduct.getText() + ")))");
                    }

                    if (validInput)
                        Launcher.run(true);
                    else
                        validInput = true;
                }catch(NumberFormatException e){
                    textArea.setText("Input field can't read letters. \nOnly numbers at the exception of the last field which takes cities as inputs.");
                }
            }
        });

        saveConfigurationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.setText("");
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

        drawGraphsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int numGraphs = 0;
                String[] list;
                boolean day;
                if(activate1.isSelected())
                    numGraphs++;
                if(activate2.isSelected())
                    numGraphs++;
                if(activate3.isSelected())
                    numGraphs++;
                if(numGraphs == 0) return;
                day = dayRadio1.isSelected();
                list = new String[numGraphs];
                numGraphs = 0;

                if(activate1.isSelected()){
                    String fuzzy = "";
                    fuzzy += analyzeMain(not11, temp11);
                    fuzzy += analyzeOptional(and11, not12, temp12);
                    fuzzy += analyzeOptional(and12, not13, temp13);
                    list[numGraphs++] = fuzzy;
                }
                if(activate2.isSelected()){
                    String fuzzy = "";
                    fuzzy += analyzeMain(not21, temp21);
                    fuzzy += analyzeOptional(and21, not22, temp22);
                    fuzzy += analyzeOptional(and22, not23, temp23);
                    list[numGraphs++] = fuzzy;
                }
                if(activate3.isSelected()){
                    String fuzzy = "";
                    fuzzy += analyzeMain(not31, temp31);
                    fuzzy += analyzeOptional(and31, not32, temp32);
                    fuzzy += analyzeOptional(and32, not33, temp33);
                    list[numGraphs] = fuzzy;
                }

                String plot = Launcher.setFuzzyPlot(list, day);
                stats.setText(plot);
            }
        });

        getDataFromCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Float[] params = Launcher.analyzeNewCity(city.getText());
                if (params != null) {
                    wind.setText(params[0].toString());
                    outAirH.setText(params[1].toString());
                    outTemperature.setText(params[2].toString());
                    Calendar calendar = new GregorianCalendar();
                    timeList.setSelectedIndex(calendar.get(Calendar.HOUR_OF_DAY));

                    String rule = "(params2 " + outTemperature.getText()
                                    + " " + outAirH.getText()
                                    + " " + wind.getText()
                                    + ")";
                    Launcher.addEval(rule);
                    Launcher.run(false);
                }else{
                    textArea.setText("Cannot find specified location");
                }
            }
        });
    }

    JTextArea getTextArea(){
        return textArea;
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
            fuzzy += " " + andBox.getSelectedItem().toString() + " ";
            fuzzy += analyzeMain(notBox, tempBox);
        }
        return fuzzy;
    }
}
