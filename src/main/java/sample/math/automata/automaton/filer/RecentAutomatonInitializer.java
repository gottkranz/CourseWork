package sample.math.automata.automaton.filer;

import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.AutomatonType;

public interface RecentAutomatonInitializer {
    Automaton getRecentAutomaton();
    Automaton getRecentAutomaton(AutomatonType requiredAutomaton);
}
