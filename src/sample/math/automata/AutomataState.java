package sample.math.automata;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AutomataState {
    private StringProperty number, input, outStateNumber, metka, output;

    public String getNumber() {
        return number.getValue();
    }
    public void setNumber(int number) {
        if(!(number < 0))
            this.number.setValue(String.valueOf(number));
    }
    public StringProperty numberProperty(){
        return number;
    }

    public String getInput() {
        return input.getValue();
    }
    public void setInput(int input) {
        if(!(input < 0))
            this.input.setValue(String.valueOf(input));
    }
    public StringProperty inputProperty(){
        return input;
    }

    public String getOutStateNumber() {
        return outStateNumber.getValue();
    }
    public void setOutStateNumber(int outStateNumber) {
        if(!(outStateNumber < 0))
            this.outStateNumber.setValue(String.valueOf((outStateNumber)));
    }
    public StringProperty outStateNumberProperty(){
        return outStateNumber;
    }

    public String getOutput() {
        return output.getValue();
    }
    public void setOutput(String output) {
        this.output.setValue(output);
    }
    public StringProperty outputProperty(){
        return output;
    }

    public void setMetka(String metka){
        this.metka.setValue(metka);
    }
    public String getMetka(){
        return metka.getValue();
    }
    public StringProperty metkaProperty(){
        return metka;
    }

    public AutomataState(int number, int input, String output, int outStateNumber){
        this.number = new SimpleStringProperty();
        this.input = new SimpleStringProperty();
        this.output = new SimpleStringProperty();
        this.outStateNumber = new SimpleStringProperty();
        this.metka = new SimpleStringProperty();
        setNumber(number);
        setInput(input);
        setOutput(output);
        setOutStateNumber(outStateNumber);
        setMetka("");
    }
    public AutomataState(int number, int input){
        this.number = new SimpleStringProperty();
        this.input = new SimpleStringProperty();
        this.output = new SimpleStringProperty();
        this.outStateNumber = new SimpleStringProperty();
        this.metka = new SimpleStringProperty();
        setNumber(number);
        setInput(input);
        setOutput("");
        setOutStateNumber(number);
        setMetka("NI");
    }
}
