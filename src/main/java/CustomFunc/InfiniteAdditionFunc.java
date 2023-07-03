package CustomFunc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.util.List;

import static java.lang.Character.isDigit;

public class InfiniteAdditionFunc implements FuncInterface{


    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException {

        if (!tokenList.get(0).equals("ADDI")) {
            throw new ParseException("Wrong token parsed inside function: " + tokenList.get(0), 0);
        }

        int result = 0;

        for (int i = 1; i < tokenList.size(); i++) {

            if (!checkNumber((tokenList.get(i)))) {

                System.out.println("lolol");

            }

            result += buildNumber(tokenList.get(i));

        }

        event.getChannel().sendMessage("The result of your addition is: " + result).queue();
    }

    private int buildNumber(String token) {

        char[] charArray = token.toCharArray();
        int totalNumber = 0;

        for (char c : charArray) {
            int digit = ((int)c) - 48;
            totalNumber = (10 * totalNumber + digit);
        }
        return totalNumber;
    }

    private boolean checkNumber(String token) {

        char[] charArray = token.toCharArray();

        for (char c : charArray) {

            if (!isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
