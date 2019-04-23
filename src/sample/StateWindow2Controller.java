package sample;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import sample.auxilliary.MyCellFactory;
import sample.auxilliary.StatesFiler;
import sample.math.automata.Automata;
import sample.math.automata.AutomataState;
import sample.math.automata.AutomataStates;

import java.util.ArrayList;
import java.util.List;

public class StateWindow2Controller extends MyController{
    @FXML
    private TableView<Automata> savedAutomataTable;
    @FXML
    private TableColumn<Automata, Integer> cardinalityColumn;
    @FXML
    private  TableView<AutomataState> stateTable, stateTable1;
    @FXML
    private TableColumn<AutomataState, String> metkaColumn1, outputColumn1, stateColumn1, inputColumn1, outStateColumn1,
            stateColumn, inputColumn, outStateColumn, metkaColumn, outputColumn;
    @FXML
    private TextField stateField, inputField, outputField, outStateField;
    private List<AutomataStates> savedAutomataList;

    private String betaTxt;

    public StateWindow2Controller(){
        super(888, 432);
        savedAutomataList = new ArrayList<>();
        filer = new StatesFiler();

        savedAutomataList = filer.getAutomatas();
        automataList = filer.getAutomatas();
    }

    @FXML
    public void initialize(){
        initCommon();
        getMainAutomataFromFile();
        //onSetBetaBtn();
        betaField.setDisable(true);
    }
    @FXML
    protected void onPlus(){

    }
    @FXML
    protected void onMinus(){

    }
    @FXML
    protected void onEnter(){
        try{
            AutomataState automataState = stateTable.getSelectionModel().getSelectedItem();
            stateField.setText(automataState.getNumber());
            outStateField.setText(automataState.getOutStateNumber());
            inputField.setText(automataState.getInput());

            outputField.setText(automataState.getOutput());
            outStateField.setText(automataState.getOutStateNumber());
        }catch (Exception e){

        }
    }
    @FXML
    protected void onSetStateBtn(){
        try{
            AutomataState automataState = stateTable.getSelectionModel().getSelectedItem();
            String beta = betaField.getText();
            if(betaField.isDisabled()){
                String out = outputField.getText();
                boolean fineOutput = AutomataStates.isFineOutput(out, beta);
                if(fineOutput){
                    String outState = outStateField.getText();
                    boolean fineOutState = ((AutomataStates)currentAutomata).isFineOutState(outState);
                    if(fineOutState){
                        automataState.setOutput(out);
                        int intOut = Integer.valueOf(outState);
                        automataState.setOutStateNumber(intOut);
                    }else{
                        Automata.showMessageError("Error", "Out State", "Bad out state value!");
                    }
                }else{
                    Automata.showMessageError("Error", "Output", "Bad output value!");
                }
            }else{
                Automata.showMessageError("Error", "Beta", "Bad beta formula!");
            }

            //.setOutput();
        }catch (Exception e){

        }
    }
    @FXML
    protected void onResetStateBtn(){

    }

    @FXML
    protected void onSetBetaBtn(){
        if(Automata.isFineBeta(betaField.getText())){
            if(Automata.showMessageAgree("WARNING", "Beta changing", "If you change beta, states can get changed or deleted!\n*If |X| changes.")){
                betaField.setDisable(true);
            }else{

            }
        }
    }
    @FXML
    protected void onResetBetaBtn(){
        if(betaField.isDisable()){
            betaField.setDisable(false);
        }else{
            betaField.setText("");
        }
    }

    //AUTOMATA WORKS
    //
    protected void getMainAutomataFromFile(){
        List<AutomataStates> automataStatesList = filer.getAutomatas("mainAutomata");
        AutomataStates automataStates  = automataStatesList.get(0);
        if(automataStates.getName().contains("states")){
            String name = automataStates.getName().replace("states", "");
            automataStates.setName(name);
            
            currentAutomata = automataStates;
            bindAutomata();
            System.out.println("Main automata is States.");
        }
    }
    protected void initCurrentAutomataFromFields(){
        String beta = betaField.getText();
        String name = nameField.getText();
        currentAutomata = new AutomataStates(name, beta, stateTable.getItems());
    }
    protected void bindAutomata(){
        betaField.setText(currentAutomata.getBeta());
        nameField.setText(currentAutomata.getName());
        stateTable.getItems().removeAll(stateTable.getItems());
        stateTable.getItems().addAll(((AutomataStates)currentAutomata).getStatesList());
    }

    protected void onBetaCorrection(){

    }

    //INIT METHODS
    //
    protected void initTables(){
        tableView = savedAutomataTable;
        cardinalityColumn.setCellValueFactory(new PropertyValueFactory<>("cardinality"));

        metkaColumn.setCellValueFactory(new PropertyValueFactory<>("metka"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        inputColumn.setCellValueFactory(new PropertyValueFactory<>("input"));
        outputColumn.setCellValueFactory(new PropertyValueFactory<>("output"));
        outStateColumn.setCellValueFactory(new PropertyValueFactory<>("outStateNumber"));

        metkaColumn1.setCellValueFactory(new PropertyValueFactory<>("metka"));
        stateColumn1.setCellValueFactory(new PropertyValueFactory<>("number"));
        inputColumn1.setCellValueFactory(new PropertyValueFactory<>("input"));
        outputColumn1.setCellValueFactory(new PropertyValueFactory<>("output"));
        outStateColumn1.setCellValueFactory(new PropertyValueFactory<>("outStateNumber"));

        outputColumn.setCellFactory(new MyCellFactory<>());
        outStateColumn.setCellFactory(new MyCellFactory<>());
        outputColumn1.setCellFactory(new MyCellFactory<>());
        outStateColumn1.setCellFactory(new MyCellFactory<>());

        for(Automata automata : automataList){
            savedAutomataTable.getItems().add(automata);
        }
    }
    protected void initListeners(){
        String regex = "\\d*";
        int limit = 11;
        Joiner.setLimiter(outStateField, regex, limit);
        regex = "[\\d\\s]*";
        limit = 16;
        Joiner.setLimiter(outputField, regex, limit);

        ChangeListener<Object> listener = (obs, oldValue, newValue) -> {
            if(oldValue != newValue){
                AutomataStates as = (AutomataStates)newValue;
                List<AutomataState> list = as.getStatesList();
                stateTable1.getItems().clear();
                stateTable1.getItems().addAll(list);
            }
        };
        tableView.getSelectionModel().selectedItemProperty().addListener(listener);

        stateTable.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                onEnter();
            }
        });
    }

    //CHECK METHODS
    //
    protected boolean isFineSpecial(){
        List<AutomataState> list = stateTable.getItems();
        boolean fineOutState = true;
        boolean fineOutput = true;
        for(int i = 0; i < list.size(); i++){
            AutomataState automataState = list.get(i);
            int outState = Integer.valueOf(automataState.getOutStateNumber());
            String outString = automataState.getOutput();
            int statesAmount = ((AutomataStates)currentAutomata).getStatesAmount();

            if(!isFineOutState(outState, statesAmount)){
                fineOutState = false;
                automataState.setMetka("BO");
            }
            if(!isFineOutput(outString)){
                fineOutput = false;
                automataState.setMetka("BS'");
            }
            String errorString;
            if(!fineOutput){
                errorString = "Bad output value!";
                Automata.showMessageError("Error", "Output", errorString);
            }
            if(!fineOutState){
                errorString = "Bad out state value!";
                Automata.showMessageError("Error", "Output", errorString);
            }
            return fineOutput && fineOutState;
        }

        return fineOutState && fineOutput;
    }
    protected boolean isFineOutState(int outState, int statesAmount){
        if(outState >= 0 && outState < statesAmount){
            return true;
        }else{
            return false;
        }
    }
    protected boolean isFineOutput(String output){
        if(((AutomataStates)currentAutomata).isFineOutput(output)){
            return true;
        }else{
            if(AutomataStates.outIsEmpty(output)){
                return true;
            }else{
                return  false;
            }
        }
    }
}

