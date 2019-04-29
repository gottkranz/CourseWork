package sample.math.automata.calculations;

import java.util.List;

public interface Transformer {
    Integer[] getPadicNumber(double doubleNumber);
    double getDoubleNumber(int[] padicNumber);

    int getPadicDigit(double number);
    Integer[] toArray(List<Integer> list);

    String getPrintablePadicString(Integer[] padicNumber);
    String getPrintablePadicString(List<Integer> padicNumber);

    String getPadicString(Integer[] padicNumber);
    String getPadicString(List<Integer> padicNumber);
}
