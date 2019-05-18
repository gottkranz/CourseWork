package sample.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import sample.auxiliary.Messager;
import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.RecentAutomatonInitializer;
import sample.math.automata.automaton.filer.RecentAutomatonInitializerImpl;
import sample.math.automata.automaton.filer.reader.Reader;
import sample.math.automata.automaton.filer.writer.Writer;

public abstract class Controller {
//VALUES
//AUXILIARY
    protected Automaton currentAutomaton;

    protected Writer writer;

//
    private final AutomatonType automatonType;

    private final String FILE_NAME;
    private RecentAutomatonInitializer recentAutomatonInitializer;

//FXML
    @FXML
    protected TextField betaField;
    @FXML
    protected Button betaApplyButton;

    @FXML
    protected TextField nameField;


//CONSTRUCTOR
    protected Controller(AutomatonType automatonType, Writer writer, String fileName){
        this.automatonType = automatonType;

        this.writer = writer;

        recentAutomatonInitializer = new RecentAutomatonInitializerImpl();

        currentAutomaton = recentAutomatonInitializer.getRecentAutomaton(automatonType);

        FILE_NAME = fileName;
    }


//METHODS
///FXML
    @FXML
    protected void onApplyBetaButton(){
        if(betaField.isDisable()){
            betaField.setDisable(false);
            betaApplyButton.setText("Apply");
        }else{
            if(currentAutomaton.setBeta(betaField.getText())){
                betaApplyButton.setText("Change");
                betaField.setDisable(true);
            }
        }
    }

    @FXML
    protected void onCancelBetaButton(){
        betaField.setText(currentAutomaton.getPreviousBeta());
        betaApplyButton.setText("Change");
        betaField.setDisable(true);
    }

    @FXML
    protected void onCancelWindowButton(){
        ((Stage)betaField.getScene().getWindow()).close();
    }

    @FXML
    protected void onSaveButton(){
        writer.write(currentAutomaton, FILE_NAME, true);
    }

    @FXML
    protected void onOkButton(){
        onApplyWindowButton();
        onCancelWindowButton();
    }

    @FXML
    protected void onApplyWindowButton(){

    }


//BINDING
    protected void bindAutomaton(){
        if(currentAutomaton == null){
            return;
        }else{
            bindCommon();
            bindSpecial();
            return;
        }
    }

    private void bindCommon(){
        nameField.setText(currentAutomaton.getName());

        betaField.setText(currentAutomaton.getBetaString());
        betaApplyButton.setText("Change");
        betaField.setDisable(true);
    }

    protected abstract void bindSpecial();


//INIT
    protected void init(){
        initCommon();
        initSpecial();
    }

    protected void initCommon(){
        //LISTENERS
        String regex = "\\d*[\\.\\/]?\\d*\\^?\\(?\\d*[\\.\\/]?\\d*\\)?";
        int limit = 27;
        addListener(betaField, regex, limit);

        regex = "[\\d\\D]*";
        addListener(betaField, regex, limit);
    }

    protected abstract void initSpecial();

//LISTENERS
    protected void addListener(TextInputControl textInputControl, String regex){
        int limit = Integer.MAX_VALUE;
        addListener(textInputControl, regex, limit);
    }

    protected void addListener(TextInputControl textInputControl, String regex, int limit){

        textInputControl.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try{
                        if (!newValue.matches(regex) || newValue.length() > limit) {
                            textInputControl.setText(oldValue);
                        }
                    }catch (Exception e){
                        System.err.println("ERROR AT: listening text:\t" + e);
                    }
                });
    }
}
