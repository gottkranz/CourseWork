package sample.math.automata.automaton.filer;

import sample.math.automata.automaton.Automaton;

public interface Reader {
    String readData(String fileName);
    String[] formData(String fileString);
    Automaton readDataBlock(String[] dataBlock);
}
