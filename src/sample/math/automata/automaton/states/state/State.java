package sample.math.automata.automaton.states.state;

import java.util.Map;

public interface State {
    int getIndex();

    Map<Integer, StateOutputArcImpl> getCopyArcMap();
    boolean isFineOutput(String output);

    boolean isFineOutstate(int outstate);
    boolean updateStatesAmount(int statesAmount);

    boolean setCardinality(int newCardinality);


    boolean setArc(int input, String output, int outstate);
    boolean setArc(StateOutputArc arc);
    boolean setArcMap(Map<Integer, StateOutputArcImpl> arcsMap);

    boolean setStatesAmount(int statesAmount);
}
