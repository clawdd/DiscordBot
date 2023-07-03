package Tests;

import Exceptions.UndefinedSymbolException;
import customFunctions.Expressions.ArithmeticExpression;
import customFunctions.expressions.FunctionExpression;
import customFunctions.expressions.IfExpression;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ExpressionTests {

    @Test
    public void functionExpression () throws UndefinedSymbolException {

        List<String> tokenList = Arrays.asList("FUNC", "funcName", "var1", "var2", "DEF", "IF", "var1", "<", "var2", "THEN", "var1", "ELSE", "var2");
        FunctionExpression functionExpression = new FunctionExpression(tokenList);

        /*
        List<String> tokenFirst = Arrays.asList("funcName", "var1", "var2");
        boolean firstPart = functionExpression.validateFirstPart(tokenFirst);

        assertTrue(firstPart);

        //List<String> tokenSecond = Arrays.asList("ADD", "var1", "var2");
        List<String> tokenSecond = Arrays.asList("IF", "var1", "<", "var2", "THEN", "var1", "ELSE", "var2");
        boolean secondPart = functionExpression.validateSecondPart(tokenSecond);

        assertTrue(secondPart);*/

        boolean endresult = functionExpression.validateFunctionExpression();
        assertTrue(endresult);
    }

    @Test
    public void ifExpression () {

        List<String> tokenList = Arrays.asList("IF", "ADD", "1", "3", "<", "var2", "THEN", "var1", "ELSE", "var2");
        IfExpression ifExpression = new IfExpression(tokenList);

        boolean result = ifExpression.validateIfExpression();
        assertTrue(result);
    }

    @Test
    public void arithmeticExpression () {

        List<String> tokenList = Arrays.asList("ADD", "1", "3");
        customFunctions.Expressions.ArithmeticExpression arithmeticExpression = new ArithmeticExpression(tokenList);

        boolean result = arithmeticExpression.isValidArithmeticExpression();
        assertTrue(result);
    }
}
