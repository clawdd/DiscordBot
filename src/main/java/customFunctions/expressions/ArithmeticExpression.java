package customFunctions.expressions;

import java.util.List;

class ExpressionNode {
    String operator;
    ExpressionNode left;
    ExpressionNode right;
    int value; // For numeric values
    String variable; // For variables
    boolean isNumeric; // Flag to indicate if it's a numeric value or variable

    public ExpressionNode(String operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public ExpressionNode(int value) {
        this.value = value;
        this.isNumeric = true;
    }

    public ExpressionNode(String variable) {
        this.variable = variable;
        this.isNumeric = false;
    }

    public boolean isOperator() {
        return operator != null;
    }
}

public class ArithmeticExpression {

    private List<String> tokenList;

    public ArithmeticExpression(List<String> tokenList) {
        this.tokenList = tokenList;
    }

    public boolean isValidArithmeticExpression() {
        if (tokenList.isEmpty()) {
            return false;
        }

        int[] currentIndex = {0};
        ExpressionNode root = parseExpression(tokenList, currentIndex);

        return root != null && currentIndex[0] == tokenList.size();
    }

    private ExpressionNode parseExpression(List<String> tokenList) {
        int[] currentIndex = {0};
        return parseExpression(tokenList, currentIndex);
    }

    private ExpressionNode parseExpression(List<String> tokenList, int[] currentIndex) {
        if (currentIndex[0] >= tokenList.size()) {
            return null; // Reached the end of token list
        }

        String token = tokenList.get(currentIndex[0]);
        currentIndex[0]++;

        if (token.equals("ADD") || token.equals("SUB") || token.equals("MUL") || token.equals("DIV")) {
            ExpressionNode left = parseExpression(tokenList, currentIndex);
            ExpressionNode right = parseExpression(tokenList, currentIndex);
            if (left == null || right == null) {
                return null; // Invalid expression
            }
            return new ExpressionNode(token, left, right);
        } else {
            // Check if the token is a variable (e.g., "a", "b", etc.)
            if (Character.isLetter(token.charAt(0))) {
                return new ExpressionNode(token); // Variable node
            } else {
                try {
                    int value = Integer.parseInt(token);
                    return new ExpressionNode(value); // Numeric value node
                } catch (NumberFormatException e) {
                    return null; // Invalid number
                }
            }
        }
    }

    public String evaluateArithmeticExpression() {
        ExpressionNode root = parseExpression(tokenList);
        if (root == null) {
            throw new IllegalArgumentException("Expression is not valid.");
        }
        return String.valueOf(evaluateArithmeticExpression(root));
    }

    private int evaluateArithmeticExpression(ExpressionNode root) {
        if (root.isOperator()) {
            int leftValue = evaluateArithmeticExpression(root.left);
            int rightValue = evaluateArithmeticExpression(root.right);

            switch (root.operator) {
                case "ADD" -> {
                    return leftValue + rightValue;
                }
                case "SUB" -> {
                    return leftValue - rightValue;
                }
                case "MUL" -> {
                    return leftValue * rightValue;
                }
                case "DIV" -> {
                    if (rightValue == 0) {
                        throw new IllegalArgumentException("Division by zero.");
                    }
                    return leftValue / rightValue;
                }
                default -> throw new IllegalArgumentException("Invalid operator: " + root.operator);
            }
        } else {
            return root.value;
        }
    }
}