package sample.auxiliary.parser;

import sample.math.calculations.Factorial;

import static java.lang.Math.pow;

public class DoubleParser {
    private static boolean test = false;

    public static final String EOF = "\0";

    private Delimiter delimiter = new Delimiter();

    private String expression;
    private int expressionId;

    private String tokenString;
    private Token tokenType;

    public double calculate(String expressionStr) throws ParserException {
        double result;

        this.expression = expressionStr;
        expressionId = 0;
        expression = expression.replace(" ", "");
        expressionStr.toLowerCase();

        getToken();

        if(tokenString.equals(EOF))
            handleError(Error.EXPRESSION_MISSING);

        result = addOrSubtract();

        return result;
    }

    private void getToken(){
        tokenType = Token.NONE;
        tokenString = "";

        if(expressionId == expression.length()){
            tokenString = EOF;
            return;
        }
        if(test) System.out.println(expression.length() + "\t" + expressionId);

        String checkStr = getStringToCheck();
        String delimiterCheckStr = delimiter.getDelimiter(checkStr);

        if(!delimiterCheckStr.equals("")){
            tokenString += delimiterCheckStr;
            expressionId += delimiterCheckStr.length();
            tokenType = Token.DELIMITER;
        }else{
            char charAt = expression.charAt(expressionId);
            if(Character.isLetter(charAt) || Character.isDigit(charAt)){
                while(delimiterCheckStr.equals("")){
                    charAt = expression.charAt(expressionId);

                    tokenString += charAt;
                    expressionId++;
                    if(expressionId >= expression.length())
                        break;

                    if(test) System.out.println(charAt);
                    checkStr = getStringToCheck();
                    delimiterCheckStr = delimiter.getDelimiter(checkStr);
                }

                if(Character.isLetter(charAt)){
                    tokenType = Token.VARIABLE;
                }else{
                    tokenType = Token.NUMBER;
                }
            }else{
                tokenString = EOF;
            }
        }



    }

    private String getStringToCheck(){
        int counter = 0;
        String ret = "";
        for(int i = expressionId; i < expression.length()
                && counter < Delimiter.MAX_DELIMITER_LENGTH; i++){
            ret += expression.charAt(i);
            counter++;
        }
        return ret;
    }

    //MATH
    //ARITHMETICAL
    private double addOrSubtract() throws ParserException {
        if(test) System.err.println("addOrSubtract\t" + tokenType.name() + "\t" + tokenString);
        char sign;
        double result;
        double partialResult;
        result = multiplyOrDivide();

        while((sign = tokenString.charAt(0)) == '+'
                || sign == '-'){
            getToken();
            partialResult = multiplyOrDivide();
            if(sign == '+'){
                result += partialResult;
            }else{
                result -= partialResult;
            }
        }
        return result;
    }

    private double multiplyOrDivide() throws ParserException {
        if(test) System.err.println("multiplyOrDivide\t" + tokenType.name() + "\t" + tokenString);
        char sign;
        double result;
        double partialResult;
        result = raiseToPower();

        while((sign = tokenString.charAt(0)) == '*'
                || sign == '/' || sign == '%'){
            getToken();
            partialResult = raiseToPower();
            switch (sign){
                case '*':
                    result *= partialResult;
                    break;
                case '/':
                    if(partialResult == 0)
                        handleError(Error.DIVIDE_BY_ZERO);
                    result /= partialResult;
                    break;
                case '%':
                    if(partialResult == 0)
                        handleError(Error.DIVIDE_BY_ZERO);
                    result %= partialResult;
                    break;
            }
        }
        return result;
    }

    private double raiseToPower() throws ParserException {
        if(test) System.err.println("raiseToPower\t" + tokenType.name() + "\t" + tokenString);

        double result;
        double partialResult;
        result = monoPlusMinus();
        if(tokenString.charAt(0) == '^'){
            getToken();
            partialResult = multiplyOrDivide();
            result = pow(result, partialResult);
        }
        return result;
    }

    private double monoPlusMinus() throws ParserException{
        double result;

        char sign = '+';
        if(tokenType == Token.DELIMITER && tokenString.equals("+")
                || tokenString.equals("-")){
            sign = tokenString.charAt(0);
            getToken();
        }
        result = factorial();
        if(sign == '-'){
            result = -result;
        }
        return result;
    }

    private double factorial() throws ParserException{
        double result;
        result = finalAndBrackets();
        if(tokenString.charAt(0) == '!'){
            if(result < 0)
                handleError(Error.NEGATIVE_FACTORIAL);
            result = Factorial.getFactorial(result);
        }
        return result;
    }

    private double finalAndBrackets() throws ParserException{
        if(test) System.err.println("finalAndBrackets\t" + tokenType.name() + "\t" + tokenString);

        double result;
        if(tokenString.equals("(")){
            getToken();
            result = addOrSubtract();
            if(!tokenString.equals(")"))
                handleError(Error.BRACKETS_DISMATCH);
            getToken();
        }else{
            result = getNumber();
        }
        return result;
    }

    private double getNumber() throws ParserException{
        double result = 0.0;
        switch (tokenType){
            case NUMBER:
                try {
                    result = Double.parseDouble(tokenString);
                }catch (NumberFormatException e){
                    if(test) System.err.println("CANT PARSE DOUBLE");
                    handleError(Error.SYNTAX_MISTAKE);
                }
                getToken();
                break;
                default:
                    if(test) System.err.println("NOT A NUMBER SWITCH");
                    handleError(Error.SYNTAX_MISTAKE);
        }
        return result;
    }

    private void handleError(Error error) throws ParserException {
        throw new ParserException(error);
    }
}
