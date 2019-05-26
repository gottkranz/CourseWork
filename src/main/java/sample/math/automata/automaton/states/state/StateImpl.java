package sample.math.automata.automaton.states.state;

import sample.math.automata.tag.TaggableImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StateImpl implements State {
    private Map<Integer, StateOutputArcImpl> arcMap;
    private int index, cardinality;
    private int statesAmount;

    public StateImpl(int index, int cardinality, int statesAmount){
        arcMap = new HashMap<>();

        this.index = index;
        setCardinality(cardinality);
        this.statesAmount = statesAmount;

        initArcs();
    }
//GETTERS


    @Override
    public int getIndex() {
        return index;
    }

//ARCS
    @Override
    public boolean setArc(int input, String output, int outstate){
        StateOutputArc arc = arcMap.get(input);
        if(arc != null){
            if(isFineOutput(output) && isFineOutstate(outstate)){
                if(isEmptyOutput(output)){
                    arc.setOutput("");
                }else{
                    arc.setOutput(output);
                }
                arc.setOutstateIndex(outstate);
                ((TaggableImpl)arc).clearTags();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public boolean setArc(StateOutputArc arc) {
        return setArc(arc.getInput(), arc.getOutput(), arc.getOutstateIndex());
    }

    @Override
    public boolean setCardinality(int newCardinality) {
        if(newCardinality > 0 && newCardinality != cardinality){
            try{
                if(newCardinality > cardinality){
                    //CREATE NEW ARCS
                    for(int i = cardinality; i < newCardinality; i++){
                        StateOutputArcImpl newArc = new StateOutputArcImpl(index, i);
                        newArc.addTag("NI");
                        arcMap.put(i, newArc);
                    }
                }else{
                    //DELETE ARCS
                    for(int i = newCardinality; i < cardinality; i++){
                        arcMap.remove(i);
                    }
                }
                cardinality = newCardinality;
                return true;
            }catch (Exception e){
                System.err.println("ERROR AT changing state cardinality");
                return false;
            }
        }else{
            return false;
        }
    }

    private void initArcs(){
        for(int i = 0; i < cardinality; i++){
            StateOutputArcImpl newArc = new StateOutputArcImpl(index, i);
            newArc.addTag("NI");
            arcMap.put(i, newArc);
        }
    }

    @Override
    public boolean setStatesAmount(int statesAmount) {
        if(statesAmount < 1 || statesAmount < index) {
            return false;
        }else{
            this.statesAmount = statesAmount;
            return true;
        }
    }

    //OUTPUT
    @Override
    public boolean isFineOutput(String output) {
        if(isEmptyOutput(output)){
            return true;
        }

        try{
            Scanner scanner = new Scanner(output);
            while (scanner.hasNextInt()){
                int r = scanner.nextInt();
                if(!(r >= 0 && r < cardinality)){
                    return false;
                }
            }
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isEmptyOutput(String output) {
        if(output.replace(" ", "").equals("")
                || output.replace(" ", "").equals("-1"))
        {
            return true;
        }else{
            return false;
        }
    }

//STATES
    @Override
    public boolean isFineOutstate(int outstate) {
        return outstate >=0 && outstate < statesAmount;
    }

    @Override
    public boolean updateStatesAmount(int statesAmount) {
        if(statesAmount > 0){
            this.statesAmount = statesAmount;
            return true;
        }else{
            return false;
        }
    }

//ARCS
    @Override
    public Map<Integer, StateOutputArcImpl> getCopyArcMap() {
        return new HashMap<>(arcMap);
    }

    @Override
    public boolean setArcMap(Map<Integer, StateOutputArcImpl> arcMap) {
        Map<Integer, StateOutputArcImpl> tmpMap = getCopyArcMap();

        //CHECK
        if(arcMap.size() != this.arcMap.size()){
            return false;
        }

        //TRY TO REWRITE
        for(Map.Entry<Integer, StateOutputArcImpl> arcEntry : arcMap.entrySet()){
            if(!setArc(arcEntry.getValue())){
                this.arcMap = tmpMap;
                return false;
            }
        }

        //CHECK IF STILL "NOT INITIALIZED"
        for(Map.Entry<Integer, StateOutputArcImpl> arcEntry : arcMap.entrySet()){
            if(     !((TaggableImpl)arcEntry.getValue())
                    .getTags()
                    .equals(""))
            {
                this.arcMap = tmpMap;
                return false;
            }
        }
        return true;
    }

    //
    @Override
    public String toString(){
        return "[index =\t" + index +
                ";\tcardinality =\t" + cardinality +
                ";\tstatesAmount =\t" + statesAmount +
                "]";
    }


}
