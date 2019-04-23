package sample.math.automata;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sample.math.DoubleParser;
import sample.math.Parser;

import java.util.Optional;

public abstract class Automata implements Cloneable{
    private SimpleStringProperty name, beta;
    private IntegerProperty cardinality;

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getBeta() {
        return beta.get();
    }
    public SimpleStringProperty betaProperty() {
        return beta;
    }
    public void setBeta(String beta) {
        this.beta.set(beta);
        setCardinality();
    }

    public int getCardinality() {
        return cardinality.get();
    }
    public IntegerProperty cardinalityProperty() {
        return cardinality;
    }
    private void setCardinality(){
        try{
            double d = new DoubleParser().evaluate(beta.getValue());
            int card = (int) Math.ceil(d);
            if(card > 1){
                cardinality.setValue(card);
            }
        }catch (Exception e){
            System.err.println("Error in cardinality: " + e);
        }
    }


    protected Automata(String name, String beta){
        if(name.equals("")){
            this.name = new SimpleStringProperty("Default Name");
        }else{
            this.name = new SimpleStringProperty(name);
        }
        if(beta.equals("")){
            this.beta = new SimpleStringProperty("2");
        }else{
            this.beta = new SimpleStringProperty(beta);
        }
        cardinality = new SimpleIntegerProperty();
        setCardinality();
    }

    public static boolean isFineBeta(String betaString){
        DoubleParser doubleParser = new DoubleParser();
        try{
            double beta = doubleParser.evaluate(betaString);
            if(beta > 1){
                return true;
            }else{
                showMessageError("Error", "Beta", "Bad beta value!");
            }
        }catch (Exception e){
            showMessageError("Error", "Beta", "Bad beta formula!");
        }
        return false;
    }

    public static void showMessageInfo(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static void showMessageError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static boolean showMessageAgree(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == null){
            return false;
        }else{
            return option.get() == ButtonType.OK;
        }
    }
}
