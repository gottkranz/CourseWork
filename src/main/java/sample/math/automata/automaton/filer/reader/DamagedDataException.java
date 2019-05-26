package sample.math.automata.automaton.filer.reader;

public class DamagedDataException extends Exception{
    String errorString;

    public DamagedDataException(){
        super();
        this.errorString = "THE DATA IN THE FILE IS DAMAGED!";
    }

    public String toString(){
        return errorString;
    }
}
