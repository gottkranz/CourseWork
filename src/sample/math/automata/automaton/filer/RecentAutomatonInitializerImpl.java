package sample.math.automata.automaton.filer;

import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;
import sample.math.automata.automaton.filer.reader.FunctionReader;
import sample.math.automata.automaton.filer.reader.Reader;
import sample.math.automata.automaton.filer.reader.StatesReader;

public class RecentAutomatonInitializerImpl implements RecentAutomatonInitializer{
    public static final String CURRENT_STATES_FILE_NAME = "current_states_automaton";
    public static final String CURRENT_FUNCTION_FILE_NAME = "current_function_automaton";


    @Override
    public Automaton getRecentAutomaton() {
        Automaton automaton;
        automaton = getRecentAutomaton(AutomatonType.STATES);
        if(automaton != null)
            return automaton;

        automaton = getRecentAutomaton(AutomatonType.FUNCTION);

        return automaton;
    }

    @Override
    public Automaton getRecentAutomaton(AutomatonType requiredAutomaton) {
        Automaton automaton = null;
        Reader reader;

        String fileName;

        switch (requiredAutomaton){
            case STATES:
                reader = new StatesReader();
                fileName = CURRENT_STATES_FILE_NAME;
                break;
            case FUNCTION:
                reader = new FunctionReader();
                fileName = CURRENT_FUNCTION_FILE_NAME;
                break;
                default:
                    System.err.println("ERROR AT: getRecentAutomaton: SWITCH");
                    return null;

        }

        try{
            automaton = reader.readAutomaton(fileName);
        }catch (Exception e){
            System.err.println("ERROR AT: getRecentAutomaton: " + e);
            return null;
        }
        return automaton;
    }


}
