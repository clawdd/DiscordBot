package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.PointsHanlder;

import java.awt.*;
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

    private void handleCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();
        if (!name.equals("insert-test")) {
            throw new ParseException("parse error", 0);
        }

        String userId = event.getUser().getId();

        String testType = Objects.requireNonNull(event.getOption("test-type")).getAsString();
        String testName = Objects.requireNonNull(event.getOption("test-name")).getAsString();
        int pointsAchieved = Objects.requireNonNull(event.getOption("points-achieved")).getAsInt();
        int pointsPossible = Objects.requireNonNull(event.getOption("points-possible")).getAsInt();
        double percentageToPass = Objects.requireNonNull(event.getOption("percentage-to-pass")).getAsDouble() / 100.0;
        int numOfTests = Objects.requireNonNull(event.getOption("num-of-tests")).getAsInt();
        int pointsAdmission = Objects.requireNonNull(event.getOption("points-for-admission")).getAsInt();

        if (!allValuesValid(pointsAchieved, pointsPossible, percentageToPass, numOfTests, pointsAdmission, event)) {
            System.out.println("Invalid values for test");
            return;
        }

        try {
            PointsHanlder pointsHanlder = new PointsHanlder();
            pointsHanlder.updatePointsTable(userId, testType, testName, pointsAchieved, pointsPossible, percentageToPass, numOfTests, pointsAdmission);

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Test Inserted Successfully")
                    .addField("Test Type", testType, true)
                    .addField("Test name", testName, true)
                    .addField("Score", pointsAchieved + " / " + pointsPossible, true)
                    .addField("Percentage to Pass", String.valueOf(percentageToPass), true)
                    .addField("Number of Tests", String.valueOf(numOfTests), false);

            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            System.out.println("Command executed with no error: " + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean allValuesValid(int pointsAchieved, int pointsPossible, double percentageToPass, int numOfTests, int pointsAdmission, SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder().setColor(Color.RED);

        if (pointsAchieved < 0) {
            eb.setTitle("Wrong values").setDescription("Points Achieved must be greater than or equal '0': " + pointsAchieved);
        } else if (pointsPossible <= 0) {
            eb.setTitle("Wrong values").setDescription("Points Possible must be greater than '0': " + pointsPossible);
        } else if (pointsPossible < pointsAchieved) {
            eb.setTitle("Wrong values").setDescription("Points Achieved cannot be greater than Points Possible: " + pointsAchieved + " > " + pointsPossible);
        } else if (percentageToPass < 0) {
            eb.setTitle("Wrong values").setDescription("Percentage to Pass must be greater than '0': " + percentageToPass);
        } else if (numOfTests <= 0) {
            eb.setTitle("Wrong values").setDescription("Number of Tests must be greater than '0': " + numOfTests);
        } else if (pointsAdmission < 0) {
            eb.setTitle("Wrong values").setDescription("Points Admission must be greater than or equal to '0': " + pointsAdmission);
        } else {
            return true;
        }

        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        return false;
    }
}