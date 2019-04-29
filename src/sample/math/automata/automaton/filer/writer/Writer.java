package sample.math.automata.automaton.filer;

import sample.math.automata.automaton.Automaton;

import java.util.List;

public interface Writer {
    boolean write(List<Automaton> automatonList, String fileName);
    boolean write(Automaton automaton, String fileName);

    /*String formDataBlock(Automaton automaton);
    String[] formData(String[] dataBlockArray);

    void writeData(String[] dataArray, String fileName);*/
}
