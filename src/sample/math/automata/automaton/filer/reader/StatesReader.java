package sample.math.automata.automaton.filer.reader;

import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.filer.writer.StatesWriter;
import sample.math.automata.automaton.states.StatesAutomatonImpl;
import sample.math.automata.automaton.states.state.State;
import sample.math.automata.automaton.states.state.StateImpl;
import sample.math.automata.automaton.states.state.StateOutputArc;
import sample.math.automata.automaton.states.state.StateOutputArcImpl;

import java.util.HashMap;
import java.util.Map;

public class StatesReader extends ReaderImpl {
    private int currentCardinality;
    private int currentStatesAmount;

    @Override
    protected Automaton readDataBlock(String[] dataBlock) throws DamagedDataException{
        String name = dataBlock[0];
        String beta = dataBlock[1];
        Automaton automaton = new StatesAutomatonImpl(name, beta);

        currentCardinality = automaton.getCardinality();

        ((StatesAutomatonImpl) automaton).setStateMap(readStatesMap(dataBlock[2]));

        return automaton;
    }

    private Map<Integer, State> readStatesMap(String data) throws DamagedDataException{
        Map<Integer, State> stateMap = new HashMap<>();

        //SPLITS DATA AT SECTORS: STATES
        String[] stringStateArray = data.split(StatesWriter.DATA_DELIMITER);

        //SENDS THE PARTS OF SPLIT DATA
        currentStatesAmount = stringStateArray.length;
        for (int i = 0; i < currentStatesAmount; i++) {
            String s = stringStateArray[i];

            State state = readState(s, i);

            if(stateMap.put(i, state) != null){
                throw new DamagedDataException();
            }

        }
        return stateMap;
    }

    private State readState(String stateStr, int index) throws DamagedDataException{
        //SPLITS DATA AT ARC LINES
        String[] stateElements = stateStr.split(StatesWriter.IN_DATA_DELIMITER_1);

        State ret;

        Map<Integer, StateOutputArcImpl> arcMap = new HashMap<>();


        //SENDS LINES
        for (int i = 0; i < currentCardinality; i++) {
            String stateElement = stateElements[i];

            StateOutputArcImpl arc = readArc(stateElement);

            if(arcMap.put(arc.getInput(), arc) != null){
                throw new DamagedDataException();
            }
            if(arc.getIndex() != index){
                throw new DamagedDataException();
            }
        }

        //
        ret = new StateImpl(index, currentCardinality, currentStatesAmount);

        if(!ret.setArcMap(arcMap)){
            throw new DamagedDataException();
        }

        return ret;
    }

    private StateOutputArcImpl readArc(String arcStr) throws DamagedDataException{
        String[] arcElements = arcStr.split(StatesWriter.IN_DATA_DELIMITER_0);

        String index = arcElements[0];
        String input = arcElements[1];
        String ouptut = arcElements[2];
        String outstate = arcElements[3];

        int indexInt = Integer.parseInt(index);
        int inputInt = Integer.parseInt(input);
        int outstateInt = Integer.parseInt(outstate);

        StateOutputArcImpl ret = new StateOutputArcImpl(indexInt, inputInt, ouptut, outstateInt);

        return ret;
    }
}
