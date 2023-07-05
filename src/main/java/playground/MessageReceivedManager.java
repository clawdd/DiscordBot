package playground;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import playground.types.CountingGame;

import java.util.HashSet;

import static secret.BotStrings.*;

public class MessageReceivedManager extends ListenerAdapter {

    private final HashSet<String> countingGameChannelIDs;
    private String eventString;

    public MessageReceivedManager() {

        this.countingGameChannelIDs = new HashSet<>();
        this.eventString = "";

        countingGameChannelIDs.add(CountingChannelID);
        countingGameChannelIDs.add(CountingChannelID2);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String msg = event.getMessage().getContentRaw();
        String channelID = event.getChannel().getId();

        if (event.getAuthor().getId().equals(BotID)) {
            System.out.println("Message from bot (ignore) - Message Received Manager");
            return;
        }

        if (countingGameChannelIDs.contains(channelID)) {

            System.out.println("Message received: " + msg);


            System.out.println("Event inside a counting game channel.");
            eventString = "c-game";
            callMessageEvent(event);
        }
    }

    private void callMessageEvent(MessageReceivedEvent event) {

        switch (eventString) {

            case "c-game" -> {
                CountingGame c = new CountingGame();
                System.out.println("Executing counting game event.");
                c.executeMessageEvent(event);
            }
            default -> System.out.println("Event not found");
        }
    }
}
