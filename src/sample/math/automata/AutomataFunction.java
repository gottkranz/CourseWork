package sample.math.automata;

import javafx.beans.property.SimpleStringProperty;

public class AutomataFunction extends Automata {
    public String getFormula() {
        return formula.get();
    }
    public SimpleStringProperty formulaProperty() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula.set(formula);
    }
    private SimpleStringProperty formula;

    public AutomataFunction(){
        super("", "");
        this.formula = new SimpleStringProperty("x - x ^ 1234");
    }
    public AutomataFunction(String name, String beta, String formula){
        super(name, beta);
        if(formula.equals("")){
            this.formula = new SimpleStringProperty("x - 1");
        }else{
            this.formula = new SimpleStringProperty(formula);
        }
    }
}
