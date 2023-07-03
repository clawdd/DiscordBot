package customFunctions.types;

import customFunctions.FuncInterface;
import customFunctions.expressions.FunctionExpression;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import sql.FunctionHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class CallFunction implements FuncInterface{


    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException, SQLException {

        int currentToken = 0;
        FunctionHandler functionHandler = new FunctionHandler();
        String userid = event.getAuthor().getId();

        if (!tokenList.get(currentToken).equals("CALL")) {
            throw new ParseException("Wrong token parsed inside function: " + tokenList.get(0), 0);
        } else if (tokenList.size() < 2) {
            throw new IllegalArgumentException("Too few arguments: no function name");
        }

        currentToken++;

        String functionName = tokenList.get(currentToken);
        String function = functionHandler.getFunctionString(userid, functionName);

        currentToken++;

        System.out.println("Function retrieved: " + function);

        List<String> functionList = parseMessage(function);
        FunctionExpression exp = new FunctionExpression(functionList);

        if (exp.validateFunctionExpression()) {


            List<String> values = getFunctionCallValues(tokenList, currentToken);
            String result = exp.evaluateFunctionExpression(values);


            System.out.println("The result is: " + result);

            event.getChannel().sendMessage("Result: " + result).queue();
        } else {
            throw new ParseException("Invalid function", 0);
        }
    }

    private List<String> insertValues(List<String> aExpression, List<String> funcVars, List<String> values) {

        for (int i = 0; i < aExpression.size(); i++) {

            String token = aExpression.get(i);

            if (funcVars.contains(token)) {

                int index = funcVars.indexOf(token);
                String value = values.get(index);
                aExpression.set(i, value);
            }
        }

        System.out.println("New arithmetic expression: " + aExpression);
        return aExpression;
    }

    private List<String> getFunctionCallValues(List<String> tokenList, int currentToken) {

        List<String> values = new ArrayList<>();

        while (currentToken < tokenList.size()) {
            values.add(tokenList.get(currentToken));
            currentToken++;
        }
        System.out.println("Values retrieved: " + values);
        return values;
    }

    private List<String> parseMessage(String msg) throws ParseException {

        msg = msg.replace("(", "");
        msg = msg.replace(")", "");
        msg = msg.replace("  ", " ");

        List<String> tokenList = new ArrayList<>();
        int startIndex = 0;

        while (startIndex < msg.length()) {

            char currentChar = msg.charAt(startIndex);

            // Check for characters to skip: spaces and newline characters
            if (currentChar == '\n') {
                startIndex++;
                continue;
            }

            //check for keywords
            int endIndex = msg.indexOf(' ', startIndex);

            if (endIndex == -1) {
                // If there are no more spaces, set endIndex to the end of the string
                endIndex = msg.length();
            }

            String toBeChecked = msg.substring(startIndex, endIndex);
            String token = createToken(toBeChecked, startIndex);
            System.out.println("Token created: "+ token);

            tokenList.add(token);

            startIndex = endIndex + 1;
        }

        System.out.println("Token List: " + tokenList);
        return tokenList;
    }

    private String createToken(String subString, int startIndex) throws ParseException {
        String token = "";

        if (subString.equalsIgnoreCase("ADDI")
                || subString.equalsIgnoreCase("ADD")
                || subString.equalsIgnoreCase("SUB")
                || subString.equalsIgnoreCase("MUL")
                || subString.equalsIgnoreCase("DIV")
                || subString.equalsIgnoreCase("FUNC")
                || subString.equalsIgnoreCase("DEF")
                || subString.equalsIgnoreCase("CALL")
                || subString.equalsIgnoreCase("IF")
                || subString.equalsIgnoreCase("THEN")
                || subString.equalsIgnoreCase("ELSE")
                || subString.equalsIgnoreCase("<")
                || subString.equalsIgnoreCase(">")
                || subString.equalsIgnoreCase("=")
                || subString.equalsIgnoreCase("<=")
                || subString.equalsIgnoreCase(">=")
                || checkNumber(subString)) {

            token = subString.toUpperCase();

        }else if (subString.matches("[A-Za-z]+")){
            token = subString.toUpperCase();
        } else {
            throw new ParseException("Parse exception at: " + startIndex, startIndex);
        }
        return token;
    }

    private boolean checkNumber(String msg) {

        char[] charArray = msg.toCharArray();

        for (char c : charArray) {

            if (!isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
