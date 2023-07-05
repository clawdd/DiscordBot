package customFunctions.expressions;

import java.util.List;
import java.util.Objects;

public class IfExpression {

    private List<String> tokenList;
    private List<String> cond;

    List<String> leftSide;
    private String op;

    List<String> rightSide;
    private List<String> primCase;
    private List<String> secCase;

    private final String LESS = "<";
    private final String GREATER = ">";
    private final String EQUAL = "=";
    private final String LESSEQUAL = "<=";
    private final String GREATEREQUAL = ">=";
    private final String NOTEQUAL = "!=";

    public IfExpression(List<String> tokenList) {
        this.tokenList = tokenList;
    }

    public boolean validateIfExpression() {
        if (tokenList.isEmpty()) {
            System.out.println("Error: The token list is empty.");
            return false;
        }

        int condIndex = tokenList.indexOf("IF");
        int primIndex = tokenList.indexOf("THEN");
        int secIndex = tokenList.indexOf("ELSE");

        if (condIndex == -1 || primIndex == -1 || secIndex == -1 || secIndex <= primIndex || primIndex <= condIndex) {
            System.out.println("Error: Invalid IF expression format. Make sure 'IF', 'THEN', and 'ELSE' are correctly placed.");
            return false;
        }

        cond = tokenList.subList(condIndex + 1, primIndex);
        primCase = tokenList.subList(primIndex + 1, secIndex);
        secCase = tokenList.subList(secIndex + 1, tokenList.size());

        System.out.println("Condition: " + cond);
        System.out.println("Primary Case: " + primCase);
        System.out.println("Secondary Case: " + secCase);

        return validateCondition(cond) && validateExpression(primCase) && validateExpression(secCase);
    }

    public boolean validateCondition(List<String> tokenList) {
        if (tokenList.isEmpty()) {
            System.out.println("Error: The condition token list is empty.");
            return false;
        }

        int operatorIndex = -1;
        for (int i = 1; i < tokenList.size(); i += 2) {
            String operator = tokenList.get(i);
            if (operator.equals(LESS) || operator.equals(GREATER) || operator.equals(EQUAL)
                    || operator.equals(LESSEQUAL) || operator.equals(GREATEREQUAL) || operator.equals(NOTEQUAL)) {
                operatorIndex = i;
                break;
            }
        }

        if (operatorIndex == -1 || operatorIndex == 0 || operatorIndex == tokenList.size() - 1) {
            System.out.println("Error: Invalid condition. Operator is missing or wrongly placed.");
            return false;
        }

        leftSide = tokenList.subList(0, operatorIndex);
        List<String> operator = tokenList.subList(operatorIndex, operatorIndex + 1);
        rightSide = tokenList.subList(operatorIndex + 1, tokenList.size());

        op = operator.get(0);

        System.out.println("Left side: " + leftSide);
        System.out.println("Operator: " + operator);
        System.out.println("Right side: " + rightSide);

        if (validateExpression(leftSide) && validateExpression(rightSide)) {
            return true;
        }
        System.out.println("Condition is wrongly build");
        return false;
    }

    private boolean validateExpression(List<String> side) {

        int currentIndex = 0;

        if (side.isEmpty()) {
            return false;
        }

        if (side.size() == 1
                && !side.get(currentIndex).equals("ADD")
                && !side.get(currentIndex).equals("SUB")
                && !side.get(currentIndex).equals("MUL")
                && !side.get(currentIndex).equals("DIV")
                && !side.get(currentIndex).equals("MOD")) {
            return true;
        }

        ArithmeticExpression exp = new ArithmeticExpression(side);
        return exp.isValidArithmeticExpression();
    }

    public String evaluateIfExpression() {
        if (cond.size() != 1) {
            String result;

            ArithmeticExpression leftExp = new ArithmeticExpression(leftSide);
            ArithmeticExpression rightExp = new ArithmeticExpression(rightSide);

            int valueLeft = Integer.parseInt(leftExp.evaluateArithmeticExpression());
            int valueRight = Integer.parseInt(rightExp.evaluateArithmeticExpression());

            switch (op) {
                case LESS:
                    if (valueLeft < valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                case GREATER:
                    if (valueLeft > valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                case EQUAL:
                    if (valueLeft == valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                case LESSEQUAL:
                    if (valueLeft <= valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                case GREATEREQUAL:
                    if (valueLeft >= valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                case NOTEQUAL:
                    if (valueLeft != valueRight) {
                        result = "True";
                    } else {
                        result = "False";
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Operator does not exist");
            }

            if (result.equals("True")) {
                ArithmeticExpression exp = new ArithmeticExpression(primCase);
                return exp.evaluateArithmeticExpression();
            } else {
                ArithmeticExpression exp = new ArithmeticExpression(secCase);
                return exp.evaluateArithmeticExpression();
            }
        }

        if (Objects.equals(cond.get(0), "0")) {
            // 0 equals false
            ArithmeticExpression exp = new ArithmeticExpression(secCase);
            return exp.evaluateArithmeticExpression();
        }
        //otherwise true
        ArithmeticExpression exp = new ArithmeticExpression(primCase);
        return exp.evaluateArithmeticExpression();
    }
}
