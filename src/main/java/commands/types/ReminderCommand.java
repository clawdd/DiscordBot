package commands.types;

import sql.ReminderHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ReminderCommand implements SlashCommands {

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();
        if (!name.equals("set-reminder")) {
            throw new ParseException("parse error", 0);
        } else {
            String className = event.getOption("class").getAsString();
            String assignmentType = event.getOption("assignment_type").getAsString();
            int day = Objects.requireNonNull(event.getOption("day")).getAsInt();
            int month = Objects.requireNonNull(event.getOption("month")).getAsInt();
            int year = Objects.requireNonNull(event.getOption("year")).getAsInt();
            int hour = Objects.requireNonNull(event.getOption("hour")).getAsInt();
            int minute = Objects.requireNonNull(event.getOption("minute")).getAsInt();


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = dateFormat.parse(String.format("%02d/%02d/%04d %02d:%02d", day, month, year, hour, minute));

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Reminder Set")
                    .setDescription("Your reminder has been set successfully.")
                    .addField("Class", className, true)
                    .addField("Assignment Type", assignmentType, true)
                    .addField("Due Date", dateFormat.format(date), true)
                    .setColor(Color.GREEN);

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();

            ReminderHandler sqlHandler = new ReminderHandler();
            String userId = event.getUser().getId();
            try {
                sqlHandler.updateReminderTable(className, assignmentType, date, userId);
                System.out.println("Command executed with no error: " + name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
