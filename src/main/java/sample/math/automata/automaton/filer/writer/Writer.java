package sample.math.automata.automaton.filer.writer;

import sample.math.automata.automaton.Automaton;

import java.util.List;

public interface Writer {
    boolean write(List<Automaton> automatonList, String fileName, boolean append);
    boolean write(Automaton automaton, String fileName, boolean append);
}
