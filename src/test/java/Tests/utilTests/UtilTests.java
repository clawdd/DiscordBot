package Tests;

import exceptions.UndefinedSymbolException;
import customFunctions.expressions.ArithmeticExpression;
import customFunctions.expressions.FunctionExpression;
import customFunctions.expressions.IfExpression;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static java.lang.Character.isDigit;
import static org.junit.Assert.*;

public class UtilTests {

    private boolean checkNumber(String msg) {

        char[] charArray = msg.toCharArray();

        for (char c : charArray) {

            if (!isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testCheckNumber () {

        assertTrue(checkNumber("2754"));

    }
}
