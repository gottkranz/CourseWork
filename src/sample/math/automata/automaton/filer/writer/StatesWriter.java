package sample.math.automata.automaton.filer.writer;

import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.states.StatesAutomaton;
import sample.math.automata.automaton.states.state.State;
import sample.math.automata.automaton.states.state.StateImpl;
import sample.math.automata.automaton.states.state.StateOutputArc;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;

import java.util.Map;

public class StatesWriter extends WriterImpl {
    public static final String DATA_DELIMITER = "#";
    public static final String IN_DATA_DELIMITER_0 = "`";
    public static final String IN_DATA_DELIMITER_1 = "``";

    @Override
    protected void addSpecial(Automaton automaton) {
        StatesAutomaton statesAutomaton = (StatesAutomaton) automaton;
        Map<Integer, State> stateMap = statesAutomaton.getStateMap();
        for(Map.Entry<Integer, State> stateEntry : stateMap.entrySet()){
            addState(stateEntry.getValue(), stateMap.size() - 1);
        }
    }

    private void addState(State state, int lastIndex){
        Map<Integer, StateOutputArcImpl> arcMap = state.getCopyArcMap();

        for(Map.Entry<Integer, StateOutputArcImpl> arcEntry : arcMap.entrySet()){
            addArc(arcEntry.getValue());
            writeString.append("\r\n" + IN_DATA_DELIMITER_1 + "\r\n");
        }
        if(state.getIndex() != lastIndex) writeString.append(DATA_DELIMITER + "\r\n");
    }

    private void addArc(StateOutputArc arc){
        writeString.append(arc.getIndex());

        writeString.append(IN_DATA_DELIMITER_0);
        writeString.append(arc.getInput());

        writeString.append(IN_DATA_DELIMITER_0);
        String output = arc.getOutput();

        if(StateImpl.isEmptyOutput(output)){
            writeString.append(-1);
        }else{
            writeString.append(output);
        }

        writeString.append(IN_DATA_DELIMITER_0);
        writeString.append(arc.getOutstateIndex());
    }
}
