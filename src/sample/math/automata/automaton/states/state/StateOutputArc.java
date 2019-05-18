package sample.math.automata.automaton.states.state;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface StateOutputArc {

    int getIndex();
    IntegerProperty getIndexProperty();

    int getInput();
    IntegerProperty getInputProperty();

    void setOutstateIndex(int index);
    int getOutstateIndex();
    IntegerProperty getOutstateIndexProperty();

    void setOutput(String output);
    String getOutput();
    StringProperty getOutputProperty();
}
