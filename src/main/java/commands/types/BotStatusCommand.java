package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.text.ParseException;

public class BotStatusCommand implements SlashCommands {

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();
        User user = event.getUser();
        if (!name.equals("set-bot-status")) {
            throw new ParseException("parse error", 0);
        } else {
            try {
                event.reply("The current Bot Version is: " + MainBot.INSTANCE.getVERSION())
                        .addActionRow(
                                Button.success("online", "Online"),
                                Button.secondary("idle", "Idle"),
                                Button.danger("donotdisturb", "Do not disturb")
                        )
                        .queue();
                System.out.println("Command executed with no error: " + name);
            } catch (Exception e) {
                throw  new ParseException("Event failed", 0);
            }
        }
    }

    @Override
    public void executeButtonTask(ButtonInteraction event) throws ParseException {
        System.out.println("Button pressed: " + event.getComponentId());
        switch (event.getComponentId()) {
            case "online" -> {
                event.reply("Bot status set to: online").queue();
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.ONLINE);
            }
            case "idle" -> {
                event.reply("Bot status set to: idle").queue();
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.IDLE);
            }
            case "donotdisturb" -> {
                event.reply("Bot status set to: do not disturb").queue();
                MainBot.INSTANCE.setOnlineStatus(OnlineStatus.DO_NOT_DISTURB);
            }
        }
    }
}
