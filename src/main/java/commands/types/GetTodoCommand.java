package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.ToDoHandler;

import java.text.ParseException;
import java.util.*;

public class GetTodoCommand implements SlashCommands {
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
        if (!name.equals("get-todos")) {
            throw new ParseException("parse error", 0);
        }

        String userId = event.getUser().getId();

        ToDoHandler toDoHandler = new ToDoHandler();
        Map<Integer, String> entries = toDoHandler.getTodoEntries(userId);
        List<MessageEmbed> embeds = new ArrayList<>();

        if (entries.isEmpty()) {
            event.reply("No todo entries found.").setEphemeral(true).queue();
        } else {

            for (Map.Entry<Integer, String> entry : entries.entrySet()) {
                int id = entry.getKey();
                String todo = entry.getValue();

                EmbedBuilder eb = new EmbedBuilder();
                eb.setDescription(todo)
                .setFooter("Todo Entry ID: " + id);

                embeds.add(eb.build());
            }

            event.replyEmbeds(embeds).setEphemeral(true).queue();
        }
    }
}
