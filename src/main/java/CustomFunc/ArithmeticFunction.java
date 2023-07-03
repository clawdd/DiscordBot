package CustomFunc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.util.List;

import static java.lang.Character.isDigit;

class ExpressionNode {
    String operator;
    ExpressionNode left;
    ExpressionNode right;
    int value;

    public ExpressionNode(String operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public ExpressionNode(int value) {
        this.value = value;
    }

    public boolean isOperator() {
        return operator != null;
    }
}

public class ArithmeticFunction implements FuncInterface {

    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException {
        if (isValidArithmeticExpression(tokenList)) {
            ExpressionNode root = parseExpression(tokenList);
            int result = evaluateArithmeticExpression(root);
            event.getChannel().sendMessage("Result: " + result).queue();
        } else {
            throw new ParseException("Invalid arithmetic expression.", 0);
        }
    }

    private boolean isValidArithmeticExpression(List<String> tokenList) {
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
            try {
                int value = Integer.parseInt(token);
                return new ExpressionNode(value);
            } catch (NumberFormatException e) {
                return null; // Invalid number
            }
        }
    }

    private int evaluateArithmeticExpression(ExpressionNode root) {
        if (root == null) {
            throw new IllegalArgumentException("ExpressionNode root cannot be null.");
        }

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