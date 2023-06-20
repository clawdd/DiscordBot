package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.PointsHanlder;

import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;

public class InsertTestCommand implements SlashCommands {
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
            return;
        }

        handleCommand(event);
        
    }

    private void handleCommand(SlashCommandInteractionEvent event)throws ParseException {
        String name = event.getName();
        if (!name.equals("insert-test")) {
            throw new ParseException("parse error", 0);
        }

        String userId = event.getUser().getId();

        String testType = Objects.requireNonNull(event.getOption("test-type")).getAsString();
        String testName = Objects.requireNonNull(event.getOption("test-name")).getAsString();
        int pointsAchieved = Objects.requireNonNull(event.getOption("points-achieved")).getAsInt();
        int pointsPossible = Objects.requireNonNull(event.getOption("points-possible")).getAsInt();
        int intValue = Objects.requireNonNull(event.getOption("percentage-to-pass")).getAsInt();
        double percentageToPass = intValue / 100.0;

        int numOfTests = Objects.requireNonNull(event.getOption("num-of-tests")).getAsInt();
        int pointsAdmission = Objects.requireNonNull(event.getOption("points-for-admission")).getAsInt();


        try {
            PointsHanlder pointsHanlder = new PointsHanlder();
            pointsHanlder.updatePointsTable(userId, testType, testName, pointsAchieved, pointsPossible, percentageToPass, numOfTests, pointsAdmission);

            Color color = createColor(pointsAchieved, pointsPossible);

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Test Inserted Successfully")
                    .addField("Test Type", testType, true)
                    .addField("Test name", testName, true)
                    .addField("Score", pointsAchieved + " / " + pointsPossible, true)
                    .addField("Percentage to Pass", String.valueOf(percentageToPass), true)
                    .addField("Number of Tests", String.valueOf(numOfTests), false)
                    .setColor(color);

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            System.out.println("Command executed with no error: " + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Color createColor(int pointsAchieved, int pointsPossible) {
        double percentage = (double) pointsAchieved / pointsPossible * 100;
        System.out.println(percentage);

        if (percentage <= 50.0) {
            return Color.RED;
        } else if (percentage > 50.0 && percentage <= 70.0) {
            return Color.ORANGE;
        }
        return Color.GREEN;
    }
}
