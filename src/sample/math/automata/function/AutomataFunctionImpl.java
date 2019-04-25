package sample.math.automata;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AutomataFunction extends AutomataImpl {
    private StringProperty functionString;

    public AutomataFunction(String name, String beta, String functionString){
        super(name, beta);
        this.functionString = new SimpleStringProperty("3");
    }

    
}
