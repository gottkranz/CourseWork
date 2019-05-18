package sample.math.automata.automaton.states;

import sample.math.automata.automaton.states.state.State;
import sample.math.automata.automaton.states.state.StateOutputArc;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;

import java.util.List;
import java.util.Map;

public interface StatesAutomaton {
    Map<Integer, State> getStateMap();

    List<StateOutputArcImpl> getAllArcs();

    void setStateMap(Map<Integer, State> stateMap);

    boolean deleteLastState();
    boolean addNewState();
}
