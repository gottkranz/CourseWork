package sample.math.parser;

import java.util.ArrayList;
import java.util.List;

public class Delimiter {
    private List<String> monoDelimiters;

    public static final int MAX_DELIMITER_LENGTH = 1;

    public Delimiter(){
        initMonoDelimiters();
    }

    public String getDelimiter(String checkString){
        String ret = getMonoDelimiter(checkString);
        return ret;
    }

    private String getMonoDelimiter(String checkString){
        return getDelimiter(checkString, monoDelimiters, 1);
    }

    private String getDelimiter(String checkString, List<String> delimiterList, int delimiterLength){
        if(checkString.length() < delimiterLength)
            return "";
        String str = checkString.substring(0, delimiterLength);
        str = str.toLowerCase();
        if(delimiterList.contains(str))
            return str;
        else
            return "";
    }

    //INIT
    private void initMonoDelimiters(){
        monoDelimiters = new ArrayList<String>();

        monoDelimiters.add("+");
        monoDelimiters.add("-");

        monoDelimiters.add("*");
        monoDelimiters.add("/");

        monoDelimiters.add("(");
        monoDelimiters.add(")");

        monoDelimiters.add("!");
        monoDelimiters.add("^");

        monoDelimiters.add("%");
    }
}
