package customFunctions.expressions;

import Exceptions.UndefinedSymbolException;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

public class FunctionExpression {

    private List<String> tokenList;
    private String funcName;
    private List<String> funcVars;
    private List<String> expression;
    private final String LESS = "<";
    private final String GREATER = ">";
    private final String EQUAL = "=";
    private final String LESSEQUAL = "<=";
    private final String GREATEREQUAL = ">=";
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public FunctionExpression (List<String> tokenList) {
        this.tokenList = tokenList;
        this.funcVars = new ArrayList<>();
        this.expression = new ArrayList<>();
    }

    public boolean validateFunctionExpression() {
        if (tokenList.isEmpty()) {
            return false;
        }

        int funcIndex = tokenList.indexOf("FUNC");
        int defIndex = tokenList.indexOf("DEF");

        if (funcIndex != -1 && defIndex != -1 && defIndex > funcIndex) {
            List<String> firstPart = tokenList.subList(funcIndex + 1, defIndex);
            List<String> secondPart = tokenList.subList(defIndex + 1, tokenList.size());

            System.out.println("First Part: " + firstPart);
            System.out.println("Second Part: " + secondPart);

            try {
                if (!validateFirstPart(firstPart) || !validateSecondPart(secondPart)) {
                    return false;
                }
            } catch (UndefinedSymbolException e) {
                System.err.println(e);
                return false;
            }

            expression = secondPart;

            System.out.println("New function was added correctly");
            return true;

        } else {
            System.out.println("Invalid token list.");
        }
        return false;
    }

    public boolean validateFirstPart(List<String> firstPart) {

        if (firstPart.size() < 1) {
            return false;
        }

        funcName = firstPart.get(0);

        for (int currentIndex = 1; currentIndex < firstPart.size(); currentIndex++) {
            this.funcVars.add(firstPart.get(currentIndex));
        }

        if (hasDuplicates(funcVars)) {
            System.out.println("Variable definition occurs twice or more: " + funcVars);
            return false;
        }

        System.out.println("Function with name: " + funcName + " and params: " + funcVars);

        return true;
    }

    public boolean validateSecondPart(List<String> secondPart) throws UndefinedSymbolException {

        int currentIndex = 0;
        String token = secondPart.get(currentIndex);

        if (!checkAbstractParam(secondPart)) {
            return false;
        }

        if (isArithmeticOperator(token)) {

            ArithmeticExpression arithmeticExpression = new ArithmeticExpression(secondPart);
            return arithmeticExpression.isValidArithmeticExpression();

        } else if (token.equals("IF")) {
            IfExpression ifExpression = new IfExpression(secondPart);
            return ifExpression.validateIfExpression();
        }
        return false;
    }

    private boolean checkAbstractParam(List<String> secondPart) throws UndefinedSymbolException {

        for (String token : secondPart) {

            if (!(isNumeric(token) || isKeyword(token) || funcVars.contains(token))) {
                throw new UndefinedSymbolException(token);
            }
        }
        return true;
    }

    public boolean isKeyword(String token) {

        switch (token) {
            case "ADD", "SUB", "MUL", "DIV", "MOD", "FUNC", "DEF", "IF", "THEN", "ELSE", "<", ">", "=", "<=", ">=" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public boolean isNumeric(String token) {
        if (token == null) {
            return false;
        }
        return pattern.matcher(token).matches();
    }

    private boolean isArithmeticOperator(String token) {
        return token.equals("ADD") || token.equals("SUB") || token.equals("MUL") || token.equals("DIV") || token.equals("MOD");
    }

    private boolean hasDuplicates(List<String> list) {
        Set<String> seenElements = new HashSet<>();

        for (String element : list) {
            if (seenElements.contains(element)) {
                return true; // Duplicate found
            }
            seenElements.add(element);
        }

        return false; // No duplicates found
    }

    private List<String> insertValues(List<String> expression, List<String> funcVars, List<String> values) {
        for (int i = 0; i < expression.size(); i++) {
            String token = expression.get(i);

            if (funcVars.contains(token)) {
                int index = funcVars.indexOf(token);
                String value = values.get(index);
                expression.set(i, value);
            }
        }

        System.out.println("Modified expression: " + expression);
        return expression;
    }

    public String evaluateFunctionExpression(List<String> values) {
        if (values.size() < funcVars.size()) {
            throw new IllegalArgumentException("Too few / many arguments: variables");
        }

        expression = insertValues(expression, funcVars, values);

        switch (expression.get(0)) {
            case "ADD", "SUB", "MUL", "DIV", "MOD" -> {
                ArithmeticExpression aexp = new ArithmeticExpression(expression);

                if (aexp.isValidArithmeticExpression()) {
                    return aexp.evaluateArithmeticExpression();
                }
            }
            case "IF" -> {
                IfExpression ifexp = new IfExpression(expression);

                if (ifexp.validateIfExpression()) {
                    return ifexp.evaluateIfExpression();
                }
            }
            default -> throw new IllegalArgumentException("Invalid type of expression");
        }
        return null;
    }

    public List<String> getTokenList() {
        return tokenList;
    }

    public String getFuncName() {
        return funcName;
    }

    public List<String> getFuncVars() {
        return funcVars;
    }

    public List<String> getExpression() {
        return expression;
    }
}