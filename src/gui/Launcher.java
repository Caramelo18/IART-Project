package gui;

import jess.swing.JTextAreaWriter;
import nrc.fuzzy.jess.FuzzyRete;
import jess.*;

import javax.swing.*;

/**
 * Created by fabio on 05-05-2017.
 */
public class Launcher {

    private static Rete motor;


    public static void main(String[] args){

        FuzzyRete engine = new FuzzyRete();
        Menu m = new Menu();

        JTextArea results = m.getTextArea();
        JTextAreaWriter taw = new JTextAreaWriter((results));
        engine.addOutputRouter("t", taw);
        engine.addOutputRouter("WSTDOUT", taw);
        engine.addOutputRouter("WSTDERR", taw);
        try {
            engine.batch("rules/rules.clp");

            engine.eval("(assert (temperature (celsius 30)))\n");
            engine.eval("(assert (timeDay (hours 12)))");

            engine.run();
        }catch (JessException e){
            e.printStackTrace();
        }
    }
}
