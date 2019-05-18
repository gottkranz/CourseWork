package sample.math.automata.automaton;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface Automaton {
    String getName();
    StringProperty getNameProperty();
    boolean setName(String name);

    String getBetaString();
    double getBetaDouble();
    StringProperty getBetaProperty();
    boolean setBeta(String beta);
    String getPreviousBeta();

    int getCardinality();
    IntegerProperty getCardinalityProperty();

    //String getTagString();
    //StringProperty getTagProperty();
    //void addTag(String tag);
    //void clearTags();
}
