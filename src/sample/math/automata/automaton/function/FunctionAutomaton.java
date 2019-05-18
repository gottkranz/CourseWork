package sample.math.automata.automaton.function;

import javafx.beans.property.StringProperty;

public interface FunctionAutomaton {
    String getFunction();
    StringProperty getFunctionProperty();
    boolean setFunction(String newFunction);
}
