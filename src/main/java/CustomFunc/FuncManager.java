package CustomFunc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Character.isDigit;

public class FuncManager extends ListenerAdapter {

    private final ConcurrentHashMap<String, FuncInterface> functions;

    public FuncManager() {

        this.functions = new ConcurrentHashMap<>();
        functions.put("ADDI", new InfiniteAdditionFunc());

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String msg = event.getMessage().getContentRaw();
        System.out.println("Message received: " + msg);

        try {
            parseMessage(msg, event);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void parseMessage(String msg, MessageReceivedEvent event) throws ParseException {

        List<String> tokenList = new ArrayList<>();
        int startIndex = 0;

        while (startIndex < msg.length()) {

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

        callFunction(tokenList, event);
    }

    private String createToken(String subString, int startIndex) throws ParseException {
        String token = "";

        if (subString.equalsIgnoreCase("ADDI") || subString.equalsIgnoreCase("ADD") || subString.equalsIgnoreCase("SUB") || checkNumber(subString)) {
            token = subString.toUpperCase();
        } else {
            throw new ParseException("The function: " + subString + "was not found", startIndex);
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

    private void callFunction(List<String>tokenList, MessageReceivedEvent event) throws ParseException {

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
