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

    public static void run(){
        try {
            engine.run();
        }catch(JessException e){
            e.printStackTrace();
        }
    }


}
