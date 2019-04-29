package sample.math.automata.automaton.filer;

import sample.math.automata.automaton.Automaton;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public abstract class WriterImpl implements Writer{
    protected StringBuffer writeString;
    protected final String BLOCKS_DELIMITER = "###";
    protected final String IN_BLOCK_DELIMITER = "##";
    protected final String DATA_DELIMITER = "#";


//METHODS
    @Override
    public boolean write(List<Automaton> automatonList, String fileName){
        writeString = new StringBuffer();
        for (int i = 0; i < automatonList.size(); i++) {
            Automaton automaton =  automatonList.get(i);

            addDataBlock(automaton);
        }

        try{
            write(fileName);
        }catch (Exception e){
            System.err.println("ERROR AT: writing Automaton:\t" + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean write(Automaton automaton, String fileName) {
        List<Automaton> list = new LinkedList<>();
        list.add(automaton);
        return write(list, fileName);
    }

//WRITING
    private void write(String fileName)throws Exception{
        FileWriter fileWriter = new FileWriter(fileName + ".txt");
        fileWriter.write(writeString.toString());
        fileWriter.close();
    }

//FORMING
    private void addDataBlock(Automaton automaton){
        addCommon(automaton);

        addSpecial();

        writeString.append(BLOCKS_DELIMITER);
    }

    private void addCommon(Automaton automaton){
        writeString.append(automaton.getName());

        writeString.append(IN_BLOCK_DELIMITER);
        writeString.append(automaton.getBetaString());
        writeString.append(IN_BLOCK_DELIMITER);
    }

    protected abstract void addSpecial();
}
