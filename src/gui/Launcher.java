package gui;

import jess.swing.JTextAreaWriter;
import nrc.fuzzy.jess.FuzzyRete;
import jess.*;

import javax.swing.*;

/**
 * Created by fabio on 05-05-2017.
 */
public class Launcher {

    static FuzzyRete engine = new FuzzyRete();

    public static void main(String[] args){

        Menu m = new Menu();

        JTextArea results = m.getTextArea();
        JTextAreaWriter taw = new JTextAreaWriter((results));
        engine.addOutputRouter("t", taw);
        engine.addOutputRouter("WSTDOUT", taw);
        engine.addOutputRouter("WSTDERR", taw);
        try {
            engine.batch("rules/rules.clp");
//            addEval("(defaultParams 20 22 14.5 17 7 18 50 5.5 6.5 40 60 60 80)");
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


}
