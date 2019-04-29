package sample.math.automata.automaton.function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.math.automata.automaton.AutomataImpl;

public class FunctionAutomataImpl /*extends AutomataImpl implements FunctionAutomata*/ {
    /*private StringProperty function;

    public FunctionAutomataImpl(String name, String beta, String function){
        super(name, beta);
        this.function = new SimpleStringProperty("3");
    }

    @Override
    public String getFunction() {
        return function.getValue();
    }

    @Override
    public StringProperty getFunctionProperty() {
        return function;
    }

    @Override
    public boolean setFunction(String newFunction) {
        try{

        }catch (Exception e){

        }
        return false;
    }
/*
    @Override
    public boolean isFineFunction(String function) {
        try {
            String formula = function.replace("x", "1");
            //double d = doubleParser.calculate(formula);
            return true;
        }catch (Exception e){
            messager.showMessageError("FUNCTION", "Bad function formula!\n" +
                    e.toString());
            messager.printErrorAtMethod(e);
            return false;
        }
    }



    @Override
    public String toString(){
        return super.toString() + "; function=" + function.getValue();
    }*/
}
