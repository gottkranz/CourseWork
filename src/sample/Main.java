package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.window.WindowType;
import sample.window.WindowLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Map<Integer, State> stateMap = new HashMap<>();
        stateMap.put(0, new StateImpl(0, 2, 2));
        stateMap.put(1, new StateImpl(1, 2, 2));

        Automaton automaton = new StatesAutomatonImpl("Try", "2", stateMap);

        Writer statesWriter = new StatesWriter();
        statesWriter.write(automaton, "test file");*/

        WindowLoader windowLoader = new WindowLoader();
        windowLoader.openWindow(WindowType.STATES_WORK, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


