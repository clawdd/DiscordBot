package commands.types;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.text.ParseException;

public interface SlashCommands {

    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException;

}