package buttons.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.text.ParseException;

public class Buttons implements Button{


    @Override
    public void executeButton(ButtonInteractionEvent event) throws ParseException {
        System.out.println("Button pressed: " + event.getComponentId());

        if(MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.ONLINE) ||
                MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.IDLE) ||
                MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {

            handleStatusButtons(event);
        } else if (MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.ONLINE)) {

        }
    }

    private static void handleStatusButtons(ButtonInteractionEvent event) throws ParseException {
        switch (event.getComponentId()) {
            case "online" -> {
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.ONLINE);

                EmbedBuilder eb = new EmbedBuilder();

                eb.addField("Status", "Bot status set to: " + MainBot.INSTANCE.getBotStatus() + " :white_check_mark:", false);
                eb.setColor(Color.GREEN);

                event.replyEmbeds(eb.build())
                        .setEphemeral(true)
                        .queue();
            }
            case "idle" -> {
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.IDLE);

                EmbedBuilder eb = new EmbedBuilder();

                eb.addField("Status", "Bot status set to: " + MainBot.INSTANCE.getBotStatus() + " :warning:", false);
                eb.setColor(Color.ORANGE);

                event.replyEmbeds(eb.build())
                        .setEphemeral(true)
                        .queue();
            }
            case "donotdisturb" -> {
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.DO_NOT_DISTURB);
                EmbedBuilder eb = new EmbedBuilder();

                eb.addField("Status", "Bot status set to: " + MainBot.INSTANCE.getBotStatus() + " :no_entry:", false);
                eb.setColor(Color.RED);

                event.replyEmbeds(eb.build())
                        .setEphemeral(true)
                        .queue();
            }
            default ->
                throw new ParseException("Button not found", 0);
        }
    }
}
