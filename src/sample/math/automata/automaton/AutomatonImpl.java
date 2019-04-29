package sample.math.automata.automaton;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sample.auxiliary.Messager;
import sample.math.automata.calculations.calculator.BetaCalculator;
import sample.math.automata.tag.Tag;
import sample.math.automata.tag.TagFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class AutomataImpl implements Automata{
//VARIABLES
//CHARACTERISTICS
    private StringProperty name, beta;
    private IntegerProperty cardinality;
    private StringProperty tags;

    private String previousBeta;

//LISTS
    private List<Tag> tagList;

//AUXILIARY
    protected BetaCalculator betaCalculator;
    protected Messager messager;


//CONSTRUCTOR
//
    protected AutomataImpl(String name, String beta){
        //INIT CHARACTERISTICS
        this.name = new SimpleStringProperty(name);
        this.beta = new SimpleStringProperty("2");
        previousBeta = "2";

        cardinality = new SimpleIntegerProperty(2);
        tags = new SimpleStringProperty();

        //INIT OTHER
        betaCalculator = new BetaCalculator();
        tagList = new LinkedList<>();
        messager = new Messager();
        //

        if(!setBeta(beta)){
            addTag("BIB");
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
    public void setName(String name) {
        this.name.setValue(name);
    }

//BETA
    @Override
    public String getBetaString() {
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
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getPreviousBeta() {
        return previousBeta;
    }

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

//TAG
    @Override
    public String getTagString() {
        return tags.getValue();
    }
    @Override
    public StringProperty getTagProperty() {
        return tags;
    }
    @Override
    public void addTag(String acronym) {
        TagFactory tagFactory = new TagFactory();
        Tag tag = tagFactory.getTag(acronym);
        if(tag != null){
            tagList.add(tag);
        }else{
            System.err.println("Bad tag acronym!");
        }
    }

    @Override
    public void clearTags() {
        tagList.clear();
    }

    @Override
    public String toString(){
        return "name=" + name.getValue() + "; beta=" + beta.getValue()
                + "; cardinality=" + cardinality.getValue();
    }
}
