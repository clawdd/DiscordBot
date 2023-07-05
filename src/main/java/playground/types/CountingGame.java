package playground.types;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import playground.MessageInterface;

import static java.lang.Character.isDigit;

public class CountingGame implements MessageInterface {


    @Override
    public void executeMessageEvent(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();

        if (!checkNumber(message)) {
            System.out.println("Message is not a number");
            return;
        }

        int response = buildNumber(message) + 1;
        event.getChannel().sendMessage(String.valueOf(response)).setSuppressedNotifications(true).queue();

        System.out.println("Executed counting game event successfully");
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

    private int buildNumber (String str) {

        int length = str.length();
        char[] charArray = str.toCharArray();
        int totalNumber = 0;

        for (char c : charArray) {

            int digit = ((int)c) - 48;
            totalNumber = (10 * totalNumber + digit);

        }
        return totalNumber;
    }
}
