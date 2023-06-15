package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import secret.BotStrings;

import java.awt.*;
import java.text.ParseException;

public class BotInfoCommand implements SlashCommands {

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {
        String name = event.getName();

        if(!name.equals("bot-info")) {
            throw new ParseException("parse error", 0);
        } else {

            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Bot - Information");
            eb.setUrl("https://github.com/clawdd/DiscordBot");

            eb.setColor(getColor());

            eb.addField("General", BotStrings.generalInfo, false);
            eb.addField("Version", "Bot Version is: " + "**" + MainBot.INSTANCE.getVERSION() + "**", true);
            eb.addField("Activity", "Bot Activity is: " + "**" + MainBot.INSTANCE.getBotStatus() + "**", true);

            event.replyEmbeds(eb.build()).queue();

            System.out.println("Command executed with no error: " + name);
        }
    }

    private Color getColor() {
        if (MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.ONLINE)) {
            return Color.GREEN;
        }
        if (MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.IDLE)) {
            return Color.ORANGE;
        }
        if (MainBot.INSTANCE.getBotStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {
            return Color.RED;
        }
        return Color.BLACK;
    }
}
