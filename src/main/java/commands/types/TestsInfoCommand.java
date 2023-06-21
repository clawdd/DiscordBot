package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.PointsHanlder;
import sql.TestTypeInformation;

import java.awt.*;
import java.text.ParseException;
import java.util.Objects;

public class TestsInfoCommand implements SlashCommands {
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
            return;
        }

        hanldeCommand(event);

    }

    private void hanldeCommand(SlashCommandInteractionEvent event) throws ParseException {

        String name = event.getName();
        if (!name.equals("get-tests-information")) {
            throw new ParseException("parse error", 0);
        }

        String userId = event.getUser().getId();
        String testType = Objects.requireNonNull(event.getOption("test-type")).getAsString();

        PointsHanlder pointsHandler = new PointsHanlder();
        TestTypeInformation testTypeInformation = pointsHandler.getTestTypeInformation(userId, testType);


        if (testTypeInformation == null) {

            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Error occurred")
                    .addField("Reason-1", "Test type: " + testType + " was not found", true)
                    .addField("Reason-2", "Test type: " + testType + " was not stored under you UserID", true)
                    .addField("Solutions", "Check for correct spelling / store a test under the desired type / contact the developer", false)
                    .setColor(Color.RED);

            System.out.println("Command could not be executed: " + name);
            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            return;
        }

        int totalPoints = pointsHandler.getTotalPoints(userId, testType);

        int overallPointsPossible = pointsHandler.getTotalPointsPossible(userId, testType);
        int pointsAdmission = testTypeInformation.getPointsAdmission();

        String passedOrNot = checkPassedString(totalPoints, pointsAdmission);
        Color color = checkPassedColor(totalPoints, pointsAdmission);

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Information: " + testType)
                .addField("Overall Points", totalPoints + " / " + overallPointsPossible, true)
                .addField("Percentage to pass", String.valueOf(testTypeInformation.getPercentageToPass()), true)
                .addField("Admission Points", totalPoints + " / " + pointsAdmission, false)
                .addField("Status", passedOrNot, false)
                .setColor(color);

        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
        System.out.println("Command executed with no error: " + name);
    }

    private String checkPassedString(int totalPoints, int pointsAdmission) {

        if (totalPoints >= pointsAdmission) {
            return "Passed :white_check_mark:";
        } else {
            return "Not passed :x:";
        }
    }

    private Color checkPassedColor(int totalPoints, int pointsAdmission) {

        if (totalPoints >= pointsAdmission) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }
}
