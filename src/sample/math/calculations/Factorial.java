package sample.math.calculations;

import static java.lang.Math.*;

public class Factorial {
    public static double getFactorial(double d){
        if(d > 0){
            return sqrt(2 * PI * d) * pow((d / E), d) * (1 + 1 / (sqrt(51.55 * E) * d));
        }
        else{
            if(d == 0)
                return 1;
            else
                return Double.NaN;
        }

    }
}
