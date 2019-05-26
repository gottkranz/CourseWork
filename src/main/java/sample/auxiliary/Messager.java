package sample.auxiliary;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Messager {
    public static void showMessageInfo(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static void showMessageError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static boolean showMessageAgree(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == null){
            return false;
        }else{
            return option.get() == ButtonType.OK;
        }
    }

    public static void showMessageInfo(String header, String content){
        showMessageInfo("INFO", header, content);
    }
    public static void showMessageError(String header, String content){
        showMessageError("ERROR", header, content);
    }
    public static boolean showMessageAgree(String header, String content){
        return showMessageAgree("Are you sure?", header, content);
    }

    public static String getCurrentMethod(){
        return  Thread.currentThread().getStackTrace()[2].getMethodName();
    }
    public static void printErrorAtMethod(Exception e){
        System.err.println("ERROR AT METHOD:\n" +
                getCurrentMethod() +
                e);
    }
}
