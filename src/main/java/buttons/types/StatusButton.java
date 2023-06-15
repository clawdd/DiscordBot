package buttons.types;

import com.sun.tools.javac.Main;
import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.text.ParseException;

public class StatusButton implements Button{


    @Override
    public void executeButton(ButtonInteractionEvent event) throws ParseException {
        System.out.println("Button pressed: " + event.getComponentId());

        if(MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.ONLINE) ||
                MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.IDLE) ||
                MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {

            switch (event.getComponentId()) {
                case "online" -> {
                    event.reply("Bot status set to: online")
                            .setEphemeral(true)
                            .queue();
                    MainBot.INSTANCE.setOnlineStatus(OnlineStatus.ONLINE);
                }
                case "idle" -> {
                    event.reply("Bot status set to: idle")
                            .setEphemeral(true)
                            .queue();
                    MainBot.INSTANCE.setOnlineStatus(OnlineStatus.IDLE);
                }
                case "donotdisturb" -> {
                    event.reply("Bot status set to: do not disturb")
                            .setEphemeral(true)
                            .queue();
                    MainBot.INSTANCE.setOnlineStatus(OnlineStatus.DO_NOT_DISTURB);
                }
                default ->
                    throw new ParseException("Button not found", 0);
            }
        } else if (MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.ONLINE)) {

        }

    }
}
