package sample.math;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public interface Automata {
    void getName();
    SimpleStringProperty getNameProperty();
    String setName(String name);

    String getBetaString();
    double getBetaValue();
    SimpleStringProperty getBetaProperty();
    void setBeta(String beta);

    String getCardinality();
    IntegerProperty getCardinalityProperty();

    void isFineBeta();
}
