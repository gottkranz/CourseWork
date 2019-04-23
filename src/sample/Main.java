package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.auxilliary.FunctionFiler;
import sample.auxilliary.StatesFiler;
import sample.math.automata.Automata;
import sample.math.automata.AutomataFunction;
import sample.math.Parser;
import sample.math.ParserException;
import sample.math.automata.AutomataState;
import sample.math.automata.AutomataStates;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    static boolean savedIsFunc;
    @Override
    public void start(Stage primaryStage) throws Exception{
        boolean bool = false;
        System.out.println(Automata.showMessageAgree("", "", ""));
        /*

        if(bool){
            Parent root = FXMLLoader.load(getClass().getResource("FunctionWindow.fxml"));
            primaryStage.setTitle("Function Automata");
            primaryStage.setScene(new Scene(root, 290, 441));
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("StateWindow2.fxml"));
            primaryStage.setTitle("State Automata");
            primaryStage.setScene(new Scene(root, 415, 561));
        }

        primaryStage.show();
        primaryStage.setResizable(false);*/
        //checkSaved();
    }
    private void test(){
        StatesFiler sf = new StatesFiler();

        List <AutomataStates> savedAutomataList = sf.getAutomatas();
        List<AutomataState> list = savedAutomataList.get(0).getStatesList();
        print(list);
    }

    public static void print(List<AutomataState> list){
        System.out.println("Printing\n");
        for(AutomataState state : list){
            String metka = state.getMetka() + "\t";
            String number = state.getNumber() + "\t";
            String input = state.getInput() + "\t";
            String output = state.getOutput() + "\t";
            String outState = state.getOutStateNumber() + "\t";
            System.out.println(metka + number + input + output + outState);
        }
    }
    private void parserTest(){
        Parser myParser = new Parser();

        boolean test = true;
        if(test){
            for(;;)
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Введите выражение для вычисления\n-> ");
                    String str = reader.readLine();
                    if(str.equals(""))
                        break;
                    int result = myParser.evaluate(str);

                    DecimalFormatSymbols s = new DecimalFormatSymbols();
                    s.setDecimalSeparator('.');
                    DecimalFormat f = new DecimalFormat("#,###.00", s);


                    System.out.printf("%s = %s%n", str, f.format(result));
                    System.out.printf("\n" + Integer.toBinaryString(result));
                }
                catch(ParserException e)
                {
                    System.out.println(e);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }

    public static void checkSaved(){
        File file = new File("currentAutomata.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
                String beta = "2";
                String func = "x - 1";
                String name = "function\r\n" + "Default";
                AutomataFunction af = new AutomataFunction(name, beta, func);
                FunctionFiler ff = new FunctionFiler();
                List list = new ArrayList<AutomataFunction>();
                list.add(af);
                ff.putAutomatas(list,"currentAutomata");
            }catch (Exception e){
                System.err.println("Error in checking saved:" + e);
            }
        }else{
            try{
                Scanner scanner = new Scanner(file);
                String str = "";
                if(scanner.hasNextLine()){
                    str = scanner.nextLine();
                }else{
                    System.out.println("Empty!!!");
                }
                if(str.equals("function")){
                    savedIsFunc = true;
                }else{
                    if(str.equals("state")){
                        savedIsFunc = false;
                    }else{
                        System.err.println("Bad saved data");
                        file.delete();
                        file.createNewFile();
                    }
                }
            }catch (Exception e){
                System.err.println("Error in checking saved:" + e);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
