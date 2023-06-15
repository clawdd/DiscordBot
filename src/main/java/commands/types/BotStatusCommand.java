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
}
