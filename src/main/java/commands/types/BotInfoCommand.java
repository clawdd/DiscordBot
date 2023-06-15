package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.text.ParseException;

public class BotInfoCommand implements SlashCommands {


    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();

        if(!name.equals("bot-info")) {
            throw new ParseException("parse error", 0);
        } else {

            event.reply("The current Bot Version is: " + MainBot.INSTANCE.getVERSION()).queue();
            System.out.println("Command executed with no error: " + name);
        }
    }
}
