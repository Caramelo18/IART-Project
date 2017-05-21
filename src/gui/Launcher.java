package gui;

import jess.swing.JTextAreaWriter;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.jess.FuzzyRete;
import jess.*;

import javax.swing.*;

/**
 * Created by fabio on 05-05-2017.
 */
public class Launcher {

    static FuzzyRete engine = new FuzzyRete();
    static Menu m;
    static FuzzyVariable nightTemp;
    static FuzzyVariable dayTemp;

    public static void main(String[] args){

        nightTemp = assignFuzzyValues(14, 17);
        dayTemp = assignFuzzyValues(20, 22);
        m = new Menu();
        JTextArea results = m.getTextArea();
        JTextAreaWriter taw = new JTextAreaWriter((results));
        engine.addOutputRouter("t", taw);
        engine.addOutputRouter("WSTDOUT", taw);
        engine.addOutputRouter("WSTDERR", taw);

        try {
            engine.batch("rules/rules.clp");
        }catch (JessException e){
            e.printStackTrace();
        }
    }

    public static void addEval(String rule){
        try {
            engine.eval(rule);
        }catch(JessException e){
            e.printStackTrace();
        }
    }

    public static void run(boolean reset){
        try {
            engine.run();
            if(reset)
                engine.reset();
        }catch(JessException e){
            e.printStackTrace();
        }
    }

    public static FuzzyVariable assignFuzzyValues(int maxCold, int minHot){
        try {
            // There are many ways to define the terms for the fuzzy variable
            // Here we use 2 of those methods:
            //   1. using arrays of x and y values to define the shape of the fuzzy set
            //   2. using already defined terms of the fuzzy variable and linguistic
            //      expressions
            double xHot[] = {minHot, minHot + 5};
            double yHot[] = {0, 1};
            double xCold[] = {maxCold - 10, maxCold};
            double yCold[] = {1, 0};

            // Create new fuzzy variable for temperature with Universe of discourse
            // from 0 to 100 and units "C".
            FuzzyVariable temp = new FuzzyVariable("temperature", 0, minHot + 10, "C");

            // Add three terms hot, cold and medium
            temp.addTerm("hot", xHot, yHot, 2);
            temp.addTerm("cold", xCold, yCold, 2);
            // Note: once a term is added it can be used to define other terms
            temp.addTerm("medium", "not hot and not cold");

            return temp;

            // Now we can define fuzzy values using the terms in the fuzzy variable.
            // Note: fuzzy values can also be created using fuzzy sets, etc. and not
            //       just with linguistic expressions.


            //System.out.println(plot);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setFuzzyTemp(boolean day, int maxCold, int minHot){
        if(day){
            dayTemp = assignFuzzyValues(maxCold, minHot);
        } else{
            nightTemp = assignFuzzyValues(maxCold, minHot);
        }
    }

    public static String setFuzzyPlot(String[] list, boolean[] days){
        String plot;
        FuzzyVariable temp;

        FuzzyValue[] values = new FuzzyValue[list.length];
        try {
            for (int i = 0; i < list.length; i++) {
                if (days[i])
                    temp = Launcher.dayTemp;
                else temp = Launcher.nightTemp;
                values[i] = new FuzzyValue(temp, list[i]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        plot = values[0].plotFuzzyValues("+-*", values);
        return plot;
    }
}
