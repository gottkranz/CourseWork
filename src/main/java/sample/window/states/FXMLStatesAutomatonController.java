package sample.window.states;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.auxiliary.Messager;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.writer.StatesWriter;
import sample.math.automata.automaton.states.StatesAutomaton;
import sample.math.automata.automaton.states.StatesAutomatonImpl;
import sample.math.automata.automaton.states.state.State;
import sample.math.automata.automaton.states.state.StateImpl;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;
import sample.window.Controller;

import java.util.List;
import java.util.Map;

public class FXMLStatesAutomatonController extends Controller {
//VALUES
//FXML
    @FXML
    protected TableView<StateOutputArcImpl> statesTable;

    @FXML
    protected TableColumn tagColumn, stateColumn, inputColumn, outputColumn, outstateColumn;

    @FXML
    protected TextField indexField, inputField, outputField, outstateField;

//WORK
    private StateOutputArcImpl selectedArc;


//CONSTRUCTOR
    public FXMLStatesAutomatonController(){
        super(AutomatonType.STATES, new StatesWriter(), "automaton_states");
    }


//METHODS

//ON BUTTONS
    //STATE
    @FXML
    protected void onAddState(){
        ((StatesAutomaton) currentAutomaton).addNewState();
        bindAutomaton();
    }

    @FXML
    protected void onDeleteLastState(){
        String text = "Some of the states will have to be changed.\n" +
                "Do you really want to delete last state?";
        if(Messager.showMessageAgree("Delete state", text)){
            ((StatesAutomaton) currentAutomaton).deleteLastState();
            bindAutomaton();
        }
    }

    //ARC
    @FXML
    protected void onApplyArcButton(){
        if(selectedArc != null){
            Map<Integer, State> map = ((StatesAutomatonImpl) currentAutomaton).getStateMap();
            StateImpl state = (StateImpl)map.get(selectedArc.getIndex());
            int outstate = Integer.parseInt(outstateField.getText());
            if(!state.setArc(selectedArc.getInput(), outputField.getText(), outstate)){
                Messager.showMessageError("Bad arc!", "The output value or the output state is bad!");
            }
            bindSpecial();
        }
    }

    @FXML
    protected void onCancelArcButton(){
        onSelectedArcPrint();
    }


    //INIT
    @FXML
    public void initialize(){
        init();
        bindAutomaton();
    }

    @Override
    protected void bindSpecial() {
        List<StateOutputArcImpl> arcs = ((StatesAutomaton)currentAutomaton).getAllArcs();
        statesTable.getItems().setAll(arcs);
    }

    @Override
    protected void initSpecial() {
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));

        stateColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        inputColumn.setCellValueFactory(new PropertyValueFactory<>("input"));

        outputColumn.setCellValueFactory(new PropertyValueFactory<>("output"));
        outstateColumn.setCellValueFactory(new PropertyValueFactory<>("outstateIndex"));

        ChangeListener<StateOutputArcImpl> listener = (obs, oldValue, newValue) -> {
            try{
                if(oldValue != newValue){
                    selectedArc = statesTable.getSelectionModel().getSelectedItem();
                    onSelectedArcPrint();
                }
            }catch (Exception e){
                System.err.println("ERROR AT: initSpecial listener: " + e);
            }
        };
        statesTable.getSelectionModel().selectedItemProperty().addListener(listener);

        String regex = "[\\d\\s]*";
        int limit = 7;
        addListener(outputField, regex, limit);
        addListener(outstateField, regex, limit);
    }

    private void onSelectedArcPrint(){
        indexField.setText(selectedArc.getIndex() + "");
        inputField.setText(selectedArc.getInput() + "");

        outputField.setText(selectedArc.getOutput());
        outstateField.setText(selectedArc.getOutstateIndex() + "");
    }
}
