package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.regex.Pattern;

public class Joiner {
    public static void setLimiter(TextInputControl textField, String regex, int limit){
        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try{
                        if (!newValue.matches(regex) || newValue.length() > limit) {
                            textField.setText(oldValue);
                        }
                    }catch (Exception e){
                        System.err.println("Error: Joiner.setLimiter() + " + e);
                    }
                });
    }
}
