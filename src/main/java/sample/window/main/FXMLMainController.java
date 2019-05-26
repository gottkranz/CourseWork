package sample.window.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.RecentAutomatonInitializerImpl;
import sample.window.Controller;
import sample.window.WindowLoader;
import sample.window.WindowType;
import sample.window.play.graphic.AutomatonGraphicBuilder;

public class FXMLMainController {
    @FXML
    protected Label currentNameLabel, currentTypeLabel;

    @FXML
    protected RadioButton burstButton, singleButton;

    @FXML
    protected TextField initialKField, finishKField, singleKField;

//

    protected Automaton currentAutomaton;

//METHODS
//BUTTONS
    @FXML
    protected void onStatesButton(){
        initCurrent(AutomatonType.STATES);
    }

    @FXML
    protected void onFunctionButton(){
        initCurrent(AutomatonType.FUNCTION);
    }

    @FXML
    protected void onStartButton(){
        AutomatonGraphicBuilder automatonGraphicBuilder = new AutomatonGraphicBuilder(currentAutomaton);
        automatonGraphicBuilder.start();
    }


//RADIO BUTTONS
    protected void onBurstRadionButton(boolean newSelected){
        boolean newDisableStatus = !newSelected;
        initialKField.setDisable(newDisableStatus);
        finishKField.setDisable(newDisableStatus);
    }

    protected void onSingleRadioButton(boolean newSelected){
        boolean newDisableStatus = !newSelected;
        singleKField.setDisable(newDisableStatus);
    }

//WORK
    private void initCurrent(AutomatonType automatonType){
        WindowLoader windowLoader = new WindowLoader();
        switch (automatonType){
            case STATES:
                windowLoader.openWindow(WindowType.STATES_WORK);
                break;
            case FUNCTION:
                windowLoader.openWindow(WindowType.FUNCTION_WORK);
                break;
        }
        gerRecentAutomaton(automatonType);
    }


//INIT
    @FXML
    public void initialize(){
        initRadioButtons();
        initFieldListeners();

        gerRecentAutomaton(AutomatonType.STATES);

        if(currentAutomaton == null){
            gerRecentAutomaton(AutomatonType.FUNCTION);
        }

        burstButton.setSelected(true);
    }

    @FXML
    protected void initRadioButtons(){
        burstButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                onBurstRadionButton(isNowSelected);
                onSingleRadioButton(!isNowSelected);
                singleButton.setSelected(!isNowSelected);
            }
        });

        singleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                onBurstRadionButton(!isNowSelected);
                onSingleRadioButton(isNowSelected);
                burstButton.setSelected(!isNowSelected);
            }
        });
    }

    @FXML
    protected void initFieldListeners(){
        String regex = "\\d*";
        int limit = 3;
        Controller.addListener(initialKField, regex, limit);
        Controller.addListener(finishKField, regex, limit);
        Controller.addListener(singleKField, regex, limit);
    }

//READ AUTOMATON
    protected void gerRecentAutomaton(AutomatonType automatonType){
        RecentAutomatonInitializerImpl recentAutomatonInitializer = new RecentAutomatonInitializerImpl();
        Automaton automaton = recentAutomatonInitializer.getRecentAutomaton(automatonType);

        if(automaton != null){
            currentTypeLabel.setText(automatonType.name());
            currentNameLabel.setText(automaton.getName());
            currentAutomaton = automaton;
        }
    }

}
