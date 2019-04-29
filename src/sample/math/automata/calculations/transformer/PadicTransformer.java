package sample.math.automata.calculations;

import sample.math.calculations.Precision;

import java.util.LinkedList;
import java.util.List;

public class PadicTransformer implements Transformer {
    private final double beta;
    private final int MAX_PADIC_DIGIT;

    public PadicTransformer(double beta){
        this.beta = beta;
        MAX_PADIC_DIGIT = (int) Math.ceil(beta) - 1;
        //System.err.println(MAX_PADIC_DIGIT);
    }

    @Override
    public Integer[] getPadicNumber(double doubleNumber) {
        List<Integer> padicNumber = new LinkedList<>();

        double result = doubleNumber;
        double remainder;

        while(true){
            remainder = Precision.round(result, 8) % Precision.round(beta, 8);
            result /= beta;

            padicNumber.add(getPadicDigit(remainder));
            if(Precision.round(result, 8) < (Precision.round(beta, 8))){
                padicNumber.add(getPadicDigit(result));
                break;
            }
        }
        Integer[] ret = toArray(padicNumber);
        return ret;
    }

    @Override
    public double getDoubleNumber(int[] padicNumber) {
        double result = 0;
        for (int i = 0; i < padicNumber.length; i++) {
            int i1 = padicNumber[i];
            result += Math.pow(beta, i) * i1;
        }
        return result;
    }

    @Override
    public int getPadicDigit(double number) {
        return (int) Math.floor(number);
    }

    @Override
    public Integer[] toArray(List<Integer> list) {
        Integer[] ret = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Integer integer =  list.get(i);
            ret[i] = integer;
        }
        return ret;
    }

    @Override
    public String getPrintablePadicString(Integer[] padicNumber) {
        return null;
    }

    @Override
    public String getPrintablePadicString(List<Integer> padicNumber) {
        return null;
    }

    @Override
    public String getPadicString(Integer[] padicNumber) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < padicNumber.length ; i++) {
            Integer integer = padicNumber[i];
            stringBuffer.append(integer);
        }
        return stringBuffer.toString();
    }

    @Override
    public String getPadicString(List<Integer> padicNumber) {
        return null;
    }
}
