package sample.math.automata.automaton.states;

import sample.math.automata.automaton.AutomatonImpl;
import sample.math.automata.automaton.states.state.State;
import sample.math.automata.automaton.states.state.StateImpl;
import sample.math.automata.automaton.states.state.StateOutputArc;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;

import java.util.*;

public class StatesAutomatonImpl extends AutomatonImpl implements StatesAutomaton {
    protected Map<Integer, State> stateMap;

    public StatesAutomatonImpl(String name, String beta){
        super(name, beta, "states_automaton");
        stateMap = new HashMap<>();
    }

    public StatesAutomatonImpl(String name, String beta, Map<Integer, State> stateMap){
        super(name, beta, "states_automaton");
        this.stateMap = new HashMap<>(stateMap);
    }


//MAP
    @Override
    public Map<Integer, State> getStateMap() {
        return stateMap;
    }

    @Override
    public void setStateMap(Map<Integer, State> stateMap) {
        this.stateMap = stateMap;
    }


//STATE
    @Override
    public boolean deleteLastState() {
        int lastIndex = stateMap.size() - 1;
        if(lastIndex == 0){
            return false;
        }else{
            stateMap.remove(lastIndex);
            return true;
        }
    }

    @Override
    public boolean addNewState() {
        int index = stateMap.size();
        State state = new StateImpl(index, getCardinality(), index);
        if(stateMap.put(index, state) != null){
            return false;
        }else{
            return true;
        }

    }


//ARC
    @Override
    public List<StateOutputArcImpl> getAllArcs() {
        List<StateOutputArcImpl> ret = new LinkedList<>();

        for(Map.Entry<Integer, State> stateEntry : stateMap.entrySet()){
            Map<Integer, StateOutputArcImpl> add = stateEntry.getValue().getCopyArcMap();
            ret.addAll(add.values());
        }

        return ret;
    }
}
