package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.PointsHanlder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllTestTypesCommand implements SlashCommands {
    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            event.reply("The bot is under surgery. SQL features are disabled.").setEphemeral(true).queue();
            System.out.println("Command execution stopped. Bot not online!");
            return;
        }

        handleCommand(event);
    }

    private void handleCommand(SlashCommandInteractionEvent event) {
        PointsHanlder pointsHandler = new PointsHanlder();
        List<String> testTypes = pointsHandler.getAllTestTypes();
        List<MessageEmbed> embeds = new ArrayList<>();

        if (testTypes.isEmpty()) {
            event.reply("No test types found.").setEphemeral(true).queue();
        } else {
            for (String testType : testTypes) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(testType);
                embeds.add(eb.build());
            }

            event.replyEmbeds(embeds).queue();
        }
    }
}
