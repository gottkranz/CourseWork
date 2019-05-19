package sample.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowLoader {
    public void openWindow(WindowType windowType, Stage stage) {
        String path = "";
        String title = "";

        int width = 500, height = 500;

        switch (windowType) {
            case STATES_WORK:
                path = "/sample/window/states/FXMLStatesAutomaton.fxml";
                title = "States Automaton Settings";
                width = 490;
                height = 560;
                break;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));

            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
