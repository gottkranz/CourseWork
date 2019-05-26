package sample.window.save;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.RecentAutomatonInitializerImpl;
import sample.math.automata.automaton.filer.reader.Reader;
import sample.math.automata.automaton.filer.reader.StatesReader;
import sample.math.automata.automaton.filer.writer.StatesWriter;
import sample.math.automata.automaton.filer.writer.Writer;

import java.util.List;


public class FXMLSaveDialogContoller {
    @FXML
    protected AnchorPane pane;

    @FXML
    protected TableView<Automaton> automatonTable;

    @FXML
    protected TableColumn nameColumn, betaColumn;

    @FXML
    public Button loadButton;

    private Reader reader;
    private Writer writer;

    private final String FILE_NAME;

    public FXMLSaveDialogContoller(AutomatonType automatonType, String fileName){
        switch (automatonType){
            case STATES:
                reader = new StatesReader();
                writer = new StatesWriter();
                break;
            case FUNCTION:
                reader = null;
                writer = null;
                break;
                default:
                    reader = null;
                    writer = null;
        }
        FILE_NAME = fileName;
    }

    @FXML
    public void initialize(){
        init();
    }

    @FXML
    protected void onLoadButton(){
        onCloseButton();
    }

    @FXML
    protected void onCloseButton(){
        ((Stage)pane.getScene().getWindow()).close();
    }

    @FXML
    protected void onDeleteButton(){
        Automaton selected = automatonTable.getSelectionModel().getSelectedItem();
        automatonTable.getItems().remove(selected);
        List<Automaton> list = automatonTable.getItems();
        writer.write(list, FILE_NAME, false);
    }

    public Automaton getSelected(){
        return automatonTable.getSelectionModel().getSelectedItem();
    }

    //INIT
    protected void init(){
        try{
            initTableListeners();
            initColumns();
            automatonTable.getItems().setAll(reader.readAutomatonList(FILE_NAME));
        }catch (Exception e){
            System.err.println("BAD AUTOMATON READ\n" + e );
        }
    }

    private void initTableListeners(){
        pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DELETE:
                        onDeleteButton();
                        break;
                    case ENTER:
                        onLoadButton();
                        break;
                }
            }
        });
    }

    private void initColumns(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        betaColumn.setCellValueFactory(new PropertyValueFactory<>("beta"));
    }
}
