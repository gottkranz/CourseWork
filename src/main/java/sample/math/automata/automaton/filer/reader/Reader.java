package sample.math.automata.automaton.filer.reader;

import sample.math.automata.automaton.Automaton;

import java.util.List;

public interface Reader {
    List<Automaton> readAutomatonList(String fileName) throws Exception;
    Automaton readAutomaton(String fileName) throws Exception;
}
