package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.ReminderHandler;

import java.sql.SQLException;
import java.text.ParseException;

public class CleanUpCommand implements SlashCommands {


    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
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
            reminderHandler.deletePastDates();

            event.reply("Past dates have been successfully deleted.").setEphemeral(true).queue();
            System.out.println("Command executed with no error: " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
