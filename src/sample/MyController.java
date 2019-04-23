package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.auxilliary.Filer;
import sample.auxilliary.MyCellFactory;
import sample.math.automata.Automata;
import sample.math.automata.AutomataFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class MyController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    protected Button tableCloseBtn, /**/ tableSaveBtn, tableOpenBtn, tableDeleteBtn;
    @FXML
    protected TextField betaField, nameField;
    @FXML
    protected TableColumn<Automata, String> nameColumn, betaColumn;

    protected BooleanProperty boolTab;
    protected int widthMax, widthMin;

    protected Automata currentAutomata;
    protected List<Automata> automataList;
    protected Filer filer;
    protected TableView tableView;

    public MyController(int widthMax, int widthMin){
        this.widthMax = widthMax;
        this.widthMin = widthMin;
        boolTab = new SimpleBooleanProperty(false);
        automataList = new ArrayList<>();
    }
    @FXML
    protected void onBetaBtn(){
        String helpBetaTxt = "β : β > 1\n\n" +
                "Determines the radix and the cardinality of the alphabet:\n" +
                "\t\t\t\t|X| = ceiling(β)\n" +
                "Possible writing:\n" +
                "\t\t\t\td;\td^(d);\td^(d/d).";
        Automata.showMessageInfo("Info", "Beta", helpBetaTxt);
    }
    @FXML
    protected void onTableCloseBtn(){
        setTable();
    }
    @FXML
    protected void onSaveBtn(){
        if(areFineFields()){
            onAddCurrent();
        }
    }
    @FXML
    protected void onDeleteBtn(){
        Automata af = (Automata) tableView.getSelectionModel().getSelectedItem();
        automataList.remove(af);
        tableView.getItems().remove(af);
        filer.putAutomatas(automataList);
    }
    @FXML
    protected void onOpenBtn(){
        Automata automata = (Automata)tableView.getSelectionModel().getSelectedItem();
        currentAutomata = automata;
        bindAutomata();
    }
    @FXML
    protected void onApplyBtn(){
        onApply();
    }
    @FXML
    protected void onOkBtn(){
        if(onApply()){
            ((Stage)betaField.getScene().getWindow()).close();
        }
    }
    @FXML
    protected void onCancelBtn(){
        ((Stage)betaField.getScene().getWindow()).close();
    }

    //SAVE FILER WORKS
    //
    protected boolean onApply(){
        if(areFineFields()){
            initCurrentAutomataFromFields();
            Automata automata = currentAutomata;
            if(currentAutomata instanceof AutomataFunction){
                automata.setName("function\r\n" + automata.getName());
                System.out.println("Instance of Function");
            }else{
                automata.setName("states\r\n" + automata.getName());
                System.out.println("Instance of States");
            }
            List<Automata> list = new ArrayList<>();
            list.add(currentAutomata);
            filer.putAutomatas(list, "mainAutomata");
            String normalName = currentAutomata.getName().replace("function\r\n", "");
            currentAutomata.setName(normalName);
            return true;
        }else{
            return false;
        }
    }


    //AUTOMATA WORKS
    //
    protected void onAddCurrent(){
        automataList.add(currentAutomata);
        tableView.getItems().add(currentAutomata);
        filer.putAutomatas(automataList);
    }
    protected abstract void getMainAutomataFromFile();
    protected abstract void initCurrentAutomataFromFields();
    protected abstract void bindAutomata(); //BINDS AUTOMATA FIELDS from CURRENT AUTOMATA

    //INIT METHODS
    //
    protected void setTable(){
        Stage stage = (Stage)betaField.getScene().getWindow();
        if(boolTab.getValue()){
            boolTab.setValue(false);
            stage.setWidth(widthMin);
        }else{
            boolTab.setValue(true);
            stage.setWidth(widthMax);
        }
    }
    protected void initCommon(){
        initTables();
        initListeners();
        betaColumn.setCellValueFactory(new PropertyValueFactory<>("beta"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        String regex = "\\d*[\\.\\/]?\\d*\\^?\\(?\\d*[\\.\\/]?\\d*\\)?";
        int limit = 27;
        Joiner.setLimiter(betaField, regex, limit);
        regex = "[\\d\\D]*";
        Joiner.setLimiter(nameField, regex, limit);

        tableView.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                onOpenBtn();
            }else{
                if(event.getCode() == KeyCode.DELETE){
                    onDeleteBtn();
                }
            }
        });

        nameColumn.setCellFactory(new MyCellFactory<>());
        betaColumn.setCellFactory(new MyCellFactory<>());
        anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if(event.isControlDown()){
                    switch (event.getCode()){
                        case S:
                            onSaveBtn();
                            break;
                        case O:
                            setTable();
                            break;
                    }
                }
                switch (event.getCode()){
                    case ESCAPE:
                        onCancelBtn();
                        break;
                }
            }
        });
    }
    protected abstract void initTables();
    protected abstract void initListeners();


    //CHECK METHODS
    //
    protected boolean areFineFields(){
        boolean fineBeta = isFilledBeta() && Automata.isFineBeta(betaField.getText());
        boolean fineName = isFilledName();
        boolean fineSpecial = isFineSpecial();
        boolean fine = fineBeta && fineName && fineSpecial;
        return fine;
    }
    private boolean isFilledBeta(){
        String spaceLess = betaField.getText().replace("\\s", "");
        if(spaceLess.equals("")){
            Automata.showMessageError("Error", "Beta", "Beta field must be filled in!");
            return false;
        }else{
            return true;
        }
    }
    private boolean isFilledName(){
        String spaceLess = nameField.getText().replace("\\s", "");
        if(spaceLess.equals("")){
            Automata.showMessageError("Error", "Name", "Name field must be filled in!");
            return false;
        }else{
            return true;
        }
    }
    protected abstract boolean isFineSpecial();
}
