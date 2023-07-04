package customFunctions;

import customFunctions.types.ArithmeticFunction;
import customFunctions.types.AddFunction;
import customFunctions.types.CallFunction;
import customFunctions.types.Condition;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Character.isDigit;
import static secret.BotStrings.BotID;

public class FuncManager extends ListenerAdapter {

    private final ConcurrentHashMap<String, FuncInterface> functions;

    public FuncManager() {

        this.functions = new ConcurrentHashMap<>();
        functions.put("ADDI", new InfiniteAdditionFunc());

        functions.put("ADD", new ArithmeticFunction());
        functions.put("SUB", new ArithmeticFunction());
        functions.put("MUL", new ArithmeticFunction());
        functions.put("DIV", new ArithmeticFunction());
        functions.put("MOD", new ArithmeticFunction());

        functions.put("FUNC", new AddFunction());

        functions.put("CALL", new CallFunction());

        functions.put("IF", new Condition());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String msg = event.getMessage().getContentRaw();
        System.out.println("Message received: " + msg);

        try {

            if (event.getAuthor().getId().equals(BotID)) {
                return;
            }

            parseMessage(msg, event);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void parseMessage(String msg, MessageReceivedEvent event) throws ParseException, SQLException {

        msg = msg.replace("(", "");
        msg = msg.replace(")", "");

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
        callFunction(tokenList, event);
    }

    private String createToken(String subString, int startIndex) throws ParseException {
        String token = "";

        if (subString.equalsIgnoreCase("ADDI")
                || subString.equalsIgnoreCase("ADD")
                || subString.equalsIgnoreCase("SUB")
                || subString.equalsIgnoreCase("MUL")
                || subString.equalsIgnoreCase("DIV")
                || subString.equalsIgnoreCase("MOD")
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
    private void callFunction(List<String>tokenList, MessageReceivedEvent event) throws ParseException, SQLException {


        String funcDef = tokenList.get(0);

        for (Map.Entry<String, FuncInterface> entry : functions.entrySet()) {

            String functionName = entry.getKey();
            FuncInterface function = entry.getValue();

            if (functionName.equals(funcDef)) {
                function.executeFunction(event, tokenList);
            }
        }
    }
}
