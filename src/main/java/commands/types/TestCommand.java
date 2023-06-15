package commands.types;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.text.ParseException;

public class TestCommand implements SlashCommands{
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        event.reply("test").queue();
    }
}
