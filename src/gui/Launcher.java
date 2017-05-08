package gui;

import nrc.fuzzy.jess.FuzzyRete;
import jess.*;

/**
 * Created by fabio on 05-05-2017.
 */
public class Launcher {

    private static Rete motor;

    public static void main(String[] args){

        FuzzyRete engine = new FuzzyRete();
        Menu m = new Menu();


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
