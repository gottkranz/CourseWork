package sample.window.states;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.auxiliary.Messager;
import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.RecentAutomatonInitializer;
import sample.math.automata.automaton.filer.RecentAutomatonInitializerImpl;
import sample.math.automata.automaton.filer.reader.StatesReader;
import sample.math.automata.automaton.filer.writer.StatesWriter;
import sample.math.automata.automaton.states.StatesAutomaton;
import sample.math.automata.automaton.states.StatesAutomatonImpl;
import sample.math.automata.automaton.states.state.StateOutputArc;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;
import sample.window.Controller;

import java.util.List;

public class FXMLStatesAutomaton_Controller extends Controller {
//VALUES
//FXML
    @FXML
    protected Label filterIndicatorLabel;

    @FXML
    protected TableView<StateOutputArcImpl> statesTable;

    @FXML
    protected TableColumn tagColumn, stateColumn, inputColumn, outputColumn, outstateColumn;


//WORK



//CONSTRUCTOR
    public FXMLStatesAutomaton_Controller(){
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

    }
}
