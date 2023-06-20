package commands.types;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.text.ParseException;

public class CommandInfoCommand implements SlashCommands{

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) throws ParseException {

        String name = event.getName();

        if(!name.equals("command-info")) {
            throw new ParseException("command not found", 0);
        } else {

            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle("Command - Info", "https://github.com/clawdd/DiscordBot");
            eb.addField("Administrator", ":black_small_square: /set-bot-status \n :black_small_square: /clean-up-reminder", false);
            eb.addField("Bot Information", ":black_small_square: /bot-info \n:black_small_square: /command-info", false);
            eb.addField("Setter Commands", ":black_small_square: /set-reminder \n:black_small_square: /set-test \n:black_small_square: /set-todo", true);
            eb.addField("Getter Commands", ":black_small_square: /get-closest-assignment \n:black_small_square: /get-tests-information \n:black_small_square: /get-test-types \n:black_small_square: /get-todos ", true);
            eb.addField("Data Manipulation", ":black_small_square: /delete-todo", false);

            event.replyEmbeds(eb.build()).queue();
            System.out.println("Command executed with no error: " + name);
        }
    }
}
