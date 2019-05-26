package sample.math.automata.automaton.states.state;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.math.automata.tag.TaggableImpl;

public class StateOutputArcImpl extends TaggableImpl implements StateOutputArc {
//VARIABLES
//CHARACTERISTICS
    private final IntegerProperty index;
    private final IntegerProperty input;

    private StringProperty output;
    private IntegerProperty outstateIndex;

//CONSTRUCTOR
//
    public StateOutputArcImpl(int index, int input){
        this(index, input, "", index);
    }

    public StateOutputArcImpl(int index, int input, String output, int outstate){
        super("arc");
        this.index = new SimpleIntegerProperty(index);
        this.input = new SimpleIntegerProperty(input);

        this.output = new SimpleStringProperty(output);
        outstateIndex = new SimpleIntegerProperty(outstate);
    }

//INDEX
    @Override
    public int getIndex() {
        return index.getValue();
    }

    @Override
    public IntegerProperty getIndexProperty() {
        return index;
    }


//INPUT
    @Override
    public int getInput() {
        return input.getValue();
    }

    @Override
    public IntegerProperty getInputProperty() {
        return input;
    }


//STATES
    @Override
    public void setOutstateIndex(int index) {
        outstateIndex.setValue(index);
    }

    @Override
    public int getOutstateIndex() {
        return outstateIndex.getValue();
    }

    @Override
    public IntegerProperty getOutstateIndexProperty() {
        return outstateIndex;
    }


//OUTPUT
    @Override
    public void setOutput(String output) {
        this.output.setValue(output);
    }

    @Override
    public String getOutput() {
        return output.getValue();
    }

    @Override
    public StringProperty getOutputProperty() {
        return output;
    }

//
    @Override
    public String toString(){
        return "[index =\t" + index.getValue() +
                ";\tinput =\t" + input.getValue() +
                ";\toutput =\t" + output.getValue() +
                ";\toutstateIndex =\t" + outstateIndex.getValue() +
                "]";
    }
}
