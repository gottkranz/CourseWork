package sample.math.automata.calculations;

import sample.math.calculations.Precision;

import java.util.LinkedList;
import java.util.List;

public class BetaTransformer implements Transformer {
    private final double beta;
    private final int MAX_PADIC_DIGIT;

    public BetaTransformer(double beta){
        this.beta = beta;
        MAX_PADIC_DIGIT = (int) Math.ceil(beta) - 1;
        //System.err.println(MAX_PADIC_DIGIT);
    }

    @Override
    public Integer[] getPadicNumber(double doubleNumber) {
        List<Integer> padicNumber = new LinkedList<>();

        double result = doubleNumber;
        int digit;
        double remainder;

        while(true){
            //remainder = Precision.round(result, 3) % Precision.round(beta, 3);
            remainder = Precision.round(result, 8) % Precision.round(beta, 8);
            //result -= remainder;
            result /= beta;

            padicNumber.add(getPadicDigit(remainder));
            if(Precision.round(result, 8) < (Precision.round(beta, 8))){
                padicNumber.add(getPadicDigit(result));
                /*if(Precision.round(result, 3) >= MAX_PADIC_DIGIT){
                    padicNumber.add(MAX_PADIC_DIGIT);
                }*/
                break;
            }
        }
        Integer[] ret = toArray(padicNumber);
        return ret;
    }

    @Override
    public double getDoubleNumber(int[] padicNumber) {
        return 0;
    }

    @Override
    public int getPadicDigit(double number) {
        //System.err.println("number = " + number + "; ceil = " + (int) Math.floor(number));
        return (int) Math.round(number);
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
        return null;
    }

    @Override
    public String getPadicString(List<Integer> padicNumber) {
        return null;
    }
}
