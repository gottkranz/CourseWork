package sample.auxilliary;

import sample.math.automata.Automata;
import sample.math.automata.AutomataFunction;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Filer {
    //SPLIT IN AUTOMATAS
    protected String[] readAutomataSet(String sourceName){
        File source = new File(sourceName + ".txt");
        if(!source.exists()){
            String[] ret = {"Default"};
            return ret;
        }else{
            try{
                Scanner scanner = new Scanner(source);
                String str = "";
                while(scanner.hasNextLine()){
                    str += scanner.nextLine();
                }
                scanner.close();
                String[] ret = str.split("##");
                return ret;
            }catch (Exception e){
                System.err.println("Error in reading file!!!");
                return null;
            }
        }
    }
    //SPLIT IN: NAME,  BETA,  DATA
    protected String[] readAutomata(String automataString){
        String[] ret = {};
        try{
            ret = automataString.split("#");
        }catch (Exception e){
            System.err.println("Error: Filer.readAutomata() + " + e);
        }finally {
            return ret;
        }
    }


    //WRITE IN: NAME,   BETA,   DATA
    protected void writeAutomatas(List automataList, String sourceName){
        File file = new File(sourceName + ".txt");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception e){
                System.err.println("Exception in creating new file!!!" + e);
            }
        }
        String[][] arr = formAutomataArray(automataList);
        writeString("", file);
        for(int i = 0; i < automataList.size(); i ++){
            writeAutomata(arr[i][0], arr[i][1], arr[i][2], file);
            //System.out.println(arr[i][0] + " " + arr[i][1] + " " + arr[i][2]);
        }
    }
    //FORMS n x 3 MATRIX
    protected abstract String[][] formAutomataArray(List automataList);
    public abstract ArrayList getAutomatas();
    public abstract ArrayList getAutomatas(String fileName);
    //public abstract AutomataFunction getAutomata(String str);
    public abstract void putAutomatas(List automataList);
    public void putAutomatas(List automataList, String fileName){
        writeAutomatas(automataList, fileName);
    }

    protected void writeAutomata(String name, String beta, String data, File file){
        String str = name + "\r\n#\r\n" + beta + "\r\n#\r\n" + data + "\r\n##\r\n";
        appendString(str, file);
    }
    protected void writeString(String text, File file){
        try{
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error in writing file!!!");
        }
    }
    protected void appendString(String text, File file){
        try{
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(text);
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error in writing file!!!");
        }
    }
}
