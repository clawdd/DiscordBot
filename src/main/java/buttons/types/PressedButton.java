package buttons.types;

import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.text.ParseException;

public class PressedButton implements Button{


    @Override
    public void executeButton(ButtonInteractionEvent event) throws ParseException {
        System.out.println("Button pressed: " + event.getComponentId());
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
            default -> {
                event.reply("Button not found")
                        .setEphemeral(true)
                        .queue();
                throw new ParseException("Button not found", 0);
            }
        }
    }
}
