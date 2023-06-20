package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.ReminderHandler;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;

import static secret.BotStrings.AdminUSERID;

public class CleanUpCommand implements SlashCommands {


    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
            return;
        }

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
        if (!name.equals("clean-up-reminder")) {
            throw new ParseException("parse error", 0);
        }

        try {
            ReminderHandler reminderHandler = new ReminderHandler();
            int affectedDates = reminderHandler.deletePastDates();

            event.reply( affectedDates + " Date / s have been deleted").setEphemeral(true).queue();
            System.out.println("Command executed with no error: " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
