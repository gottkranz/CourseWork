package sample.math.automata;

import javafx.beans.property.IntegerProperty;
import sample.math.DoubleParser;
import sample.math.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutomataStates extends Automata{
    private String[][][] statesArray;
    private List<AutomataState> statesList;
    private int statesAmount;

    public int getStatesAmount(){
        return statesAmount;
    }

    public String[][][] getStatesArray(){
        return statesArray;
    }
    public List<AutomataState> getStatesList(){
        return statesList;
    }
    public void setStatesList(List<AutomataState> list){
        statesList = list;
    }

    public AutomataStates(){
        super("", "");
    }
    public AutomataStates(String name, String beta, List<AutomataState> list){
        super(name, beta);
        setStatesList(list);
        Parser parser = new Parser();
        try{
            statesAmount = (int) Math.ceil(list.size() / parser.evaluate(beta));
        }catch (Exception e){

        }
    }


    public void transformData(String[] data){
        readStatesAmount(data);
        initData();
        for(int i = 1; i < statesAmount * getCardinality() + 1; i++){
            readData(data, i);
        }
    }
    private void readStatesAmount(String[] data){
        try{
            int ret = Integer.valueOf(data[0]);
            if(ret < 1){
                statesAmount = 1;
            }else{
                statesAmount = ret;
            }
        }catch (Exception e){
            System.err.println("Error in reading states amount.");
            statesAmount = 1;
        }
    }
    private void initData(){
        statesArray = new String[statesAmount][getCardinality()][2];
        statesList = new ArrayList<>();
        for(int i = 0; i < statesAmount; i++){
            for(int j = 0; j < getCardinality(); j++){
                statesList.add(new AutomataState(i, j));
            }
        }
    }
    private void readData(String[] data, int ind){
        String metka;
        if(ind < data.length){
            String[] state = data[ind].split("~");
            try{
                int num = Integer.valueOf(state[0]);
                int input = Integer.valueOf(state[1]);
                if(input >= 0 && input < getCardinality()){
                    num = (num * 2)  + input;
                    if(num >= 0 && num < statesAmount * getCardinality()){
                        if(input >= 0 && input < getCardinality()){
                            statesList.get(num).setInput(input);
                            int outState = Integer.valueOf(state[3]);
                            if(outState >= 0 && outState < statesAmount){
                                statesList.get(num).setOutStateNumber(outState);
                                String output = state[2];
                                if(output.equals("-1")){
                                    statesList.get(num).setOutput("");
                                    statesList.get(num).setMetka("");
                                }else{
                                    if(isFineOutput(output)){
                                        statesList.get(num).setOutput(output);
                                        statesList.get(num).setMetka("");
                                    }else{
                                        metka = "BO";
                                        statesList.get(num).setMetka(metka);
                                    }
                                }
                            }else{
                                metka = "BS'";
                                statesList.get(num).setMetka(metka);
                            }
                        }
                    }
                }
            }catch (Exception e){
                System.err.println("Exception in reading data: " + e);
            }
        }
    }

    public void printStates(){
        for (int j = 0; j < statesList.size(); j++) {
            AutomataState automataState =  statesList.get(j);
            String input = automataState.getInput();
            String state = automataState.getNumber();
            String output = automataState.getOutput();
            String outState = automataState.getOutStateNumber();
            System.out.print(state + "\t" + input + "\t" + output + "\t" + outState + "\n");
        }
    }

    public boolean isFineOutput(String output){
        try{
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextInt()){
                int r = scanner.nextInt();
                if(!(r >= 0 && r < getCardinality())){
                    if(r == -1){
                        return true;
                    }
                    return false;
                }
            }
            return  true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean isFineOutState(String outState){
        return true;
    }
    public static boolean isFineOutput(String output, String beta){
        DoubleParser parser = new DoubleParser();
        int alphabetStrength = 2;
        try{
            alphabetStrength = (int) Math.ceil(parser.evaluate(beta));

            //System.out.println(parser.evaluate(beta.getValue()));
        }catch (Exception e){
            System.err.println("Error in automata to beta: " + e);
        }
        try{
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextInt()){
                int r = scanner.nextInt();
                if(!(r >= 0 && r < alphabetStrength)){
                    if(r == -1){
                        return true;
                    }
                    return false;
                }
            }
            return  true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean outIsEmpty(String out){
        try{
            Scanner scanner = new Scanner(out);
            if(scanner.hasNextInt()){
                //System.err.println("not empty: " + out);
                return false;
            }else{
                //System.err.println("empty: " + out);
                return true;
            }
        }catch (Exception e){
            System.err.println("Error in checking empty out: " + e);
            return true;
        }
    }
}
