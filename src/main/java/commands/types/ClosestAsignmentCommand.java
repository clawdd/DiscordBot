package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.Assignment;
import sql.ReminderHandler;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClosestAsignmentCommand implements SlashCommands {
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
            return;
        }

        handleCommand(event);
    }

    private void handleCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();
        String userId = event.getUser().getId();
        if (!name.equals("get-closest-assignment")) {
            throw new ParseException("parse error", 0);
        } else {

            ReminderHandler reminderHandler = new ReminderHandler();

            Assignment assignment;
            try {
                assignment = reminderHandler.findClosestAssignment(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (assignment != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date currentDate = new Date();

                long timeDifference = assignment.getDate().getTime() - currentDate.getTime();
                long daysLeft = timeDifference / (24 * 60 * 60 * 1000);
                long hoursLeft = (timeDifference % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
                long minutesLeft = (timeDifference % (60 * 60 * 1000)) / (60 * 1000);

                Color color = creatColor(daysLeft);

                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("Closest Assignment")
                        .setDescription("The closest assignment is due on:")
                        .addField("Due Date", dateFormat.format(assignment.getDate()), false)
                        .addField("Time Left", daysLeft + " days, " + hoursLeft + " hours, " + minutesLeft + " minutes", false)
                        .addField("Class", assignment.getClassName(), true)
                        .addField("Assignment", assignment.getAssigmentType(), true)
                        .setColor(color);

                event.replyEmbeds(eb.build()).setEphemeral(true).queue();
                System.out.println("Command executed with no error: " + name);
            } else {
                event.reply("No assignment found.").setEphemeral(true).queue();
            }
        }
    }

    private Color creatColor(long daysLeft) {

        if (daysLeft <= 1) {
            return Color.RED;
        }
        if (daysLeft <= 3) {
            return Color.ORANGE;
        }
        return Color.GREEN;
    }
}
