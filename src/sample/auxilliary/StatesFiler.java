package sample.auxilliary;

import sample.math.automata.AutomataFunction;
import sample.math.automata.AutomataState;
import sample.math.automata.AutomataStates;

import java.util.ArrayList;
import java.util.List;

public class StatesFiler extends Filer {
    public ArrayList getAutomatas(){
        return getAutomatas("states");
    }
    public ArrayList getAutomatas(String fileName){
        String[] arr = readAutomataSet(fileName);
        ArrayList<AutomataStates> ret = new ArrayList<>();
        for(String str : arr){
            ret.add(getAutomata(str));
        }
        return ret;
    }
    public AutomataStates getAutomata(String str){
        String[] arr = readAutomata(str);
        AutomataStates as = new AutomataStates();
        try{
            as.setName(arr[0]);
            as.setBeta(arr[1]);
            String[] dataArr = arr[2].split("~~");
            as.transformData(dataArr);

        }catch (Exception e){
            System.out.println("Err: " + str);
            System.err.println("Error in making States:\n" + e);
        }
        return  as;
    }
    @Override
    protected String[][] formAutomataArray(List automataList) {
        String[][] ret = new String[automataList.size()][3];
        for(int i = 0; i < automataList.size(); i ++){
            AutomataStates as  = (AutomataStates) (automataList.get(i));
            ret[i][0] = as.getName();
            ret[i][1] = as.getBeta();
            ret[i][2] = as.getStatesAmount() + "";
            for(int q = 0; q < as.getStatesList().size(); q++){
                String num = ((AutomataState)as.getStatesList().get(q)).getNumber() + "~";
                String in = ((AutomataState)as.getStatesList().get(q)).getInput() + "~";
                String out = ((AutomataState)as.getStatesList().get(q)).getOutput();
                if(as.outIsEmpty(out)){
                    out = "-1";
                }
                out += "~";
                String outs = ((AutomataState)as.getStatesList().get(q)).getOutStateNumber() + "";
                ret[i][2] += "\r\n~~" + num + in + out + outs;
            }
        }
        return ret;
    }
    public void putAutomatas(List automataList){
        putAutomatas(automataList, "states");
    }

}
