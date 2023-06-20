package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.awt.*;
import java.text.ParseException;

import static secret.BotStrings.AdminUSERID;

public class BotStatusCommand implements SlashCommands {

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        String userId = event.getUser().getId();

        if (!userId.equals(AdminUSERID)) {

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Not for you!")
                    .setDescription("This command is only for the Admin")
                    .setColor(Color.RED);

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            System.out.println("Command couldn't be executed: NOT Admin");
            return;
        }

        handleCommand(event);
    }

    private static void handleCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();
        if (!name.equals("set-bot-status")) {
            throw new ParseException("parse error", 0);
        } else {
            try {
                event.reply("Set the bot activity status")
                        .addActionRow(
                                Button.success("online", "Online"),
                                Button.secondary("idle", "Idle"),
                                Button.danger("donotdisturb", "Do not disturb")
                        )
                        .setEphemeral(true)
                        .queue();
                System.out.println("Command executed with no error: " + name);
            } catch (Exception e) {
                throw  new ParseException("Event failed", 0);
            }
        }
    }
}
