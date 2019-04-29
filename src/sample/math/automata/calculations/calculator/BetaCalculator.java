package sample.math.automata.calculations;

import sample.auxiliary.Messager;
import sample.auxiliary.parser.DoubleParser;

public class BetaCalculator implements Calculator {
    private static DoubleParser doubleParser;
    private Messager messager;

    public BetaCalculator(){
        doubleParser = new DoubleParser();
        messager = new Messager();
    }

    @Override
    public double calculate(String expression) {
        try {
            double d = doubleParser.calculate(expression);
            if(d <= 1){
                messager.showMessageError("BETA", "Bad beta value!\n" +
                        "beta > 1");
                return -1;
            }else{
                return d;
            }
        }catch (Exception e){
            messager.showMessageError("BETA", "Bad beta formula!" +
                    e.toString());
            messager.printErrorAtMethod(e);
            return -1;
        }
    }
}
