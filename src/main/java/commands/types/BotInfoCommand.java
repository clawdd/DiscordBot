package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.text.ParseException;

public class BotInfoCommand implements SlashCommands {

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();

        if(!name.equals("bot-info")) {
            throw new ParseException("parse error", 0);
        } else {

            event.reply("The current Bot Version is: " + MainBot.INSTANCE.getVERSION())
                    .addActionRow(Button.link("https://github.com/clawdd/DiscordBot", "Git Repo"))
                    .queue();
            System.out.println("Command executed with no error: " + name);
        }
    }

    @Override
    public void executeButtonTask(ButtonInteraction event) throws ParseException {

    }
}
