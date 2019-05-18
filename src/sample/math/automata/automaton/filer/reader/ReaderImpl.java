package sample.math.automata.automaton.filer.reader;

import sample.math.automata.automaton.Automaton;
import sample.math.automata.automaton.filer.writer.WriterImpl;

import java.io.File;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public abstract class ReaderImpl implements Reader{
    @Override
    public List<Automaton> readAutomatonList(String fileName) throws Exception{
        List<Automaton> ret = new LinkedList<>();
        String read = readData(fileName);
        String[][] fracturedData = formData(read);

        for (int i = 0; i < fracturedData.length; i++) {
            String[] fracturedDatum = fracturedData[i];
            Automaton automaton = readDataBlock(fracturedDatum);
            ret.add(automaton);
        }
        return ret;
    }

    @Override
    public Automaton readAutomaton(String fileName) throws Exception {
        return readAutomatonList(fileName).get(0);
    }

    private String readData(String fileName) throws Exception{
        File source = new File(fileName + ".txt");
        if(!source.exists()){
            throw new Exception("THE FILE DOESN'T EXIST!");
        }else{
            Scanner scanner = new Scanner(source);
            StringBuffer str = new StringBuffer();
            while(scanner.hasNextLine()){
                str.append(scanner.nextLine());
            }
            scanner.close();

            if(str.toString().replace(" ", "").equals("")){
                throw new Exception("THE FILE IS EMPTY!");
            }

            return str.toString();
        }
    }

    private String[][] formData(String fileString) throws Exception{
        String[] dataBlocks = fileString.split(WriterImpl.BLOCKS_DELIMITER);
        String[][] ret = new String[dataBlocks.length][3];

        try{
            for (int i = 0; i < dataBlocks.length; i++) {
                String block = dataBlocks[i];
                String[] fracturedData = block.split(WriterImpl.IN_BLOCK_DELIMITER);
                ret[i][0] = fracturedData[0];
                ret[i][1] = fracturedData[1];
                ret[i][2] = fracturedData[2];
            }
        }catch (Exception e){
            throw new DamagedDataException();
        }

        return ret;
    }

    protected abstract Automaton readDataBlock(String[] dataBlock) throws Exception;

}
