package sample.math.automata.automaton.filer.writer;

import sample.math.automata.automaton.Automaton;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public abstract class WriterImpl implements Writer{
    protected StringBuffer writeString;
    public static final String BLOCKS_DELIMITER = "###";
    public static final String IN_BLOCK_DELIMITER = "##";

//METHODS
    @Override
    public boolean write(List<Automaton> automatonList, String fileName, boolean append){
        writeString = new StringBuffer();
        for (int i = 0; i < automatonList.size(); i++) {
            Automaton automaton =  automatonList.get(i);

            if(automaton != null){
                addDataBlock(automaton);
            }
        }

        try{
            write(fileName, append);
        }catch (Exception e){
            System.err.println("ERROR AT: writing Automaton:\t" + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean write(Automaton automaton, String fileName, boolean append) {
        List<Automaton> list = new LinkedList<>();
        list.add(automaton);
        return write(list, fileName, append);
    }


    //WRITING
    private void write(String fileName, boolean append)throws Exception{
        FileWriter fileWriter = new FileWriter(fileName + ".txt", append);
        fileWriter.write(writeString.toString());
        fileWriter.close();
    }

//FORMING
    private void addDataBlock(Automaton automaton){
        addCommon(automaton);

        addSpecial(automaton);

        writeString.append(BLOCKS_DELIMITER + "\r\n");
    }

    private void addCommon(Automaton automaton){
        writeString.append(automaton.getName());

        writeString.append("\r\n" + IN_BLOCK_DELIMITER + "\r\n");
        writeString.append(automaton.getBetaString());
        writeString.append("\r\n" + IN_BLOCK_DELIMITER + "\r\n");
    }

    protected abstract void addSpecial(Automaton automaton);
}
