package sample.math.automata.automaton.function;

import javafx.beans.property.StringProperty;

public interface FunctionAutomata {
    String getFunction();
    StringProperty getFunctionProperty();
    boolean setFunction(String newFunction);
}
