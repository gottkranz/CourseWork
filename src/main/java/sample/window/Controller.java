package sample.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.auxiliary.Messager;
import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.RecentAutomatonInitializer;
import sample.math.automata.automaton.filer.RecentAutomatonInitializerImpl;
import sample.math.automata.automaton.filer.writer.Writer;
import sample.window.save.FXMLSaveDialogContoller;

import java.io.File;

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
                System.err.println(currentAutomaton.getBeta());
                bindSpecial();
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
        currentAutomaton.setName(nameField.getText());
        writer.write(currentAutomaton, FILE_NAME, true);
    }

    @FXML
    protected void onOkButton(){
        currentAutomaton.setName(nameField.getText());
        try {
            onApplyWindowButton();
        }catch (Exception e){
            Messager.showMessageError("Bad automaton!", e +
                    "\ntry to restart the application!");
        }
        onCancelWindowButton();
    }

    @FXML
    protected void onApplyWindowButton() throws Exception{
        currentAutomaton.setName(nameField.getText());
        String path;
        File remove;

        switch (automatonType){
            case STATES:
                path = RecentAutomatonInitializerImpl.CURRENT_STATES_FILE_NAME;
                remove = new File(RecentAutomatonInitializerImpl.CURRENT_FUNCTION_FILE_NAME);
                remove.delete();
                //System.err.println(path);
                break;
            case FUNCTION:
                path = RecentAutomatonInitializerImpl.CURRENT_FUNCTION_FILE_NAME;
                remove = new File(RecentAutomatonInitializerImpl.CURRENT_STATES_FILE_NAME);
                remove.delete();
                //System.err.println(path);
                break;
                default:
                    System.err.println("Bad automatonType");
                    throw new Exception("Bad automatonType");
        }
        writer.write(currentAutomaton, path, false);
    }

    @FXML
    protected void onLoadButton(){
        try{
            //INIT LOADER
            FXMLSaveDialogContoller controller = new FXMLSaveDialogContoller(automatonType, FILE_NAME);
            String path = "/sample/window/save/FXMLSaveDialog.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            loader.setController(controller);

            //INIT WINDOW
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Load Dialog");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            //INIT LISTENER
            controller.loadButton.setOnAction((event -> {
                currentAutomaton.copy(controller.getSelected());
                bindAutomaton();
                stage.close();
            }));
            /*
            stage.setOnCloseRequest((WindowEvent event1) ->{
                currentAutomaton.copy(controller.getSelected());
                bindAutomaton();
                stage.close();
            });*/

            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            System.err.println("FATAL ERROR AT:" +
                    "\nLoad Save Dialog!!!" +
                    "\n" + e);
        }
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

        betaField.setText(currentAutomaton.getBeta());
        betaApplyButton.setText("Change");
        betaField.setDisable(true);
    }

    protected abstract void bindSpecial();


//INIT
    protected void init(){
        initCommon();
        initSpecial();
    }

    private void initCommon(){
        //LISTENERS
        String regex = "\\d*[\\.\\/]?\\d*\\^?\\(?\\d*[\\.\\/]?\\d*\\)?";
        int limit = 27;
        addListener(betaField, regex, limit);

        regex = "[\\d\\D]*";
        addListener(betaField, regex, limit);
    }

    protected abstract void initSpecial();

//LISTENERS
    public static void addListener(TextInputControl textInputControl, String regex){
        int limit = Integer.MAX_VALUE;
        addListener(textInputControl, regex, limit);
    }

    public static void addListener(TextInputControl textInputControl, String regex, int limit){

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
