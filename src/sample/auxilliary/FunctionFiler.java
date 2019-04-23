package sample.auxilliary;

import sample.math.automata.AutomataFunction;

import java.util.ArrayList;
import java.util.List;

public class FunctionFiler extends Filer{
    public ArrayList getAutomatas(){
        return getAutomatas("functions");
    }
    public ArrayList getAutomatas(String fileName){
        String[] arr = readAutomataSet(fileName);
        ArrayList<AutomataFunction> ret = new ArrayList<>();
        for(String str : arr){
            ret.add(getAutomata(str));
        }
        return ret;
    }
    public AutomataFunction getAutomata(String str){
        String[] arr = readAutomata(str);
        AutomataFunction af = new AutomataFunction();
        try{
            String name = arr[0];
            String beta = arr[1];
            String formula = arr[2];
            af.setName(name);
            af.setBeta(beta);
            af.setFormula(formula);
        }catch (Exception e){
            System.err.println("Array length: " + arr.length);
            System.err.println("Error: FunctionFiler.getAutomata(String str) + " + e);
        }
        finally {
            return af;
        }
    }
    public void putAutomatas(List automataList){
        putAutomatas(automataList, "functions");
    }

    @Override
    protected String[][] formAutomataArray(List automataList) {
        String[][] ret = new String[automataList.size()][3];
        for(int i = 0; i < automataList.size(); i ++){
            AutomataFunction af  = (AutomataFunction)(automataList.get(i));
            ret[i][0] = af.getName();
            ret[i][1] = af.getBeta();
            ret[i][2] = af.getFormula();
        }
        return ret;
    }
}
