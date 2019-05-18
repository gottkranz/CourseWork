package sample.math.automata.calculations.transformer;

import java.util.List;

public interface Transformer {
    Integer[] getPadicNumber(double doubleNumber);
    double getDoubleNumber(int[] padicNumber);

    int getPadicDigit(double number);
    Integer[] toArray(List<Integer> list);
}
