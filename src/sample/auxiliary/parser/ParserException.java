package main.parser;

public class ParserException extends Exception {
    private String errorString;

    public ParserException(Error error){
        super();
        this.errorString = error.name();
    }

    public String toString(){
        return errorString;
    }
}
