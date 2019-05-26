package sample.math.automata.automaton;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.auxiliary.Messager;
import sample.math.automata.calculations.calculator.BetaCalculator;
import sample.math.automata.tag.TaggableImpl;
import sample.math.automata.tag.factory.TagFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class AutomatonImpl extends TaggableImpl implements Automaton {
//VARIABLES
//CHARACTERISTICS
    private StringProperty name, beta;
    private IntegerProperty cardinality;
    private StringProperty tags;

    private String previousBeta;

//LISTS


//AUXILIARY
    protected BetaCalculator betaCalculator;
    protected Messager messager;


//CONSTRUCTOR
//
    protected AutomatonImpl(String name, String beta, String type){
        super(type);
        //INIT CHARACTERISTICS
        this.name = new SimpleStringProperty("Default Name");
        this.beta = new SimpleStringProperty("2");
        previousBeta = "2";

        cardinality = new SimpleIntegerProperty(2);
        tags = new SimpleStringProperty();

        //INIT OTHER
        betaCalculator = new BetaCalculator();
        messager = new Messager();
        //

        if(!setBeta(beta)){
            addTag("BIB");
        }
        if(!setName(name)){
            addTag("BN");
        }
    }


//METHODS
//NAME
    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public StringProperty getNameProperty() {
        return name;
    }

    @Override
    public boolean setName(String name) {
        String replace = name.replace(" ", "");
        if(replace.equals("")){
            return false;
        }else{
            this.name.setValue(name);
            return true;
        }
    }


//BETA
    @Override
    public String getBeta() {
        return beta.getValue();
    }

    @Override
    public double getBetaDouble() {
        return betaCalculator.calculate(beta.getValue());
    }

    @Override
    public StringProperty getBetaProperty() {
        return beta;
    }

    @Override
    public boolean setBeta(String beta) {
        if(betaCalculator.calculate(beta) != -1){
            this.beta.setValue(beta);
            setCardinality();
            previousBeta = beta;
            onBetaChange();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getPreviousBeta() {
        return previousBeta;
    }

    protected abstract void onBetaChange();


//CARDINALITY
    @Override
    public int getCardinality() {
        return cardinality.getValue();
    }

    @Override
    public IntegerProperty getCardinalityProperty() {
        return cardinality;
    }

    private void setCardinality(){
        int newCardinality = (int)Math.ceil(getBetaDouble());
        cardinality.setValue(newCardinality);
    }

    @Override
    public String toString(){
        return "name=" + name.getValue() + "; beta=" + beta.getValue()
                + "; cardinality=" + cardinality.getValue();
    }
}
