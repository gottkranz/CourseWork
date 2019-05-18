package sample.math.automata.calculations.transformer;

import java.util.List;

public class PadicStringTransformer {
    public String getPadicString(Integer[] padicNumber){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < padicNumber.length ; i++) {
            Integer integer = padicNumber[i];
            stringBuffer.append(integer);
        }
        return stringBuffer.toString();
    }

    public String getPadicString(List<Integer> padicNumber){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < padicNumber.size() ; i++) {
            Integer integer = padicNumber.get(i);
            stringBuffer.append(integer);
        }
        return stringBuffer.toString();
    }
}
