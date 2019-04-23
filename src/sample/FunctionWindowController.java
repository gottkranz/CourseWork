package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.auxilliary.FunctionFiler;
import sample.auxilliary.MyCellFactory;
import sample.math.Parser;
import sample.math.automata.Automata;
import sample.math.automata.AutomataFunction;

import java.util.ArrayList;
import java.util.List;

public class FunctionWindowController extends MyController{
    @FXML
    private TableView<AutomataFunction> automataFunctionTableView;
    @FXML
    private TableColumn<AutomataFunction, String>  formulaColumn;
    @FXML
    private TextArea funcArea;

    //private List automataFunctionList = new ArrayList<>();

    public FunctionWindowController(){
        super(825, 305);
        filer = new FunctionFiler();
        //automataList = automataFunctionList;
    }
    @FXML
    public void initialize() {
        automataList = filer.getAutomatas();
        initCommon();
        getMainAutomataFromFile();
    }

    @FXML
    private void onFuncBtn(){
        String helpFuncTxt = "f - function\n\n" +
                "Logical operations can be used only when \nβ ⩽ 2.\n\n" +
                "Available signs:\n" +
                "& (AND), | (OR), ^ (XOR) \t" +
                "+ - * /";
        Automata.showMessageInfo("Info", "Function", helpFuncTxt);
    }

    //////////////////////
    //AUTOMATA WORKS
    //
    protected void getMainAutomataFromFile(){
        List<AutomataFunction> automataFunctionList = filer.getAutomatas("mainAutomata");
        AutomataFunction automataFunction  = automataFunctionList.get(0);
        if(automataFunction.getName().contains("function")){
            betaField.setText(automataFunction.getBeta());
            String name = automataFunction.getName().replace("function", "");
            automataFunction.setName(name);
            nameField.setText(name);
            funcArea.setText(automataFunction.getFormula());
            currentAutomata = automataFunction;
            System.out.println("Main automata is Function.");
        }

    }
    protected void initCurrentAutomataFromFields(){
        String beta = betaField.getText();
        String func = funcArea.getText();
        String name = nameField.getText();
        currentAutomata = new AutomataFunction(name, beta, func);
    }
    protected void bindAutomata(){
        betaField.setText(currentAutomata.getBeta());
        nameField.setText(currentAutomata.getName());
        funcArea.setText(((AutomataFunction)currentAutomata).getFormula());
    }

    //CHECK METHODS
    //
    protected boolean isFineSpecial(){
        String spaceLess = funcArea.getText().replace("\\s", "");
        if(spaceLess.equals("")){
            Automata.showMessageError("Error", "Formula", "Formula field must be filled in!");
            return false;
        }else{
            Parser parser = new Parser();
            try {
                spaceLess = spaceLess.replace("x", "0");
                double d = parser.evaluate(spaceLess);
                return true;
            }catch (Exception e){
                Automata.showMessageError("Error", "Formula", "Bad formula writing!");
                System.err.println("Error: isFineSpecial (func) + " + e);
                return false;
            }
        }
    }


    //INIT METHODS
    //
    protected void initTables(){
        tableView = automataFunctionTableView;
        formulaColumn.setCellValueFactory(new PropertyValueFactory<>("formula"));
        for(Automata automata : automataList){
            automataFunctionTableView.getItems().add((AutomataFunction)automata);
        }
        formulaColumn.setCellFactory(new MyCellFactory<>());
    }
    protected void initListeners(){
        String regex = "[x0-9\\&\\^\\|\\(\\)\\-\\+\\*\\s]*";
        int limit = 54;
        Joiner.setLimiter(funcArea, regex, limit);
    }
}
