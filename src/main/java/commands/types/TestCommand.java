package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.text.ParseException;

public class TestCommand implements SlashCommands{
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        if(MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("Command usage seems to be limited right now").setEphemeral(true).queue();
        }
        event.reply("test").queue();
    }
}
