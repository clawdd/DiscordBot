package commands.types;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.text.ParseException;

public interface SlashCommands {
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException;

    void executeButtonTask(ButtonInteraction event) throws ParseException;
}