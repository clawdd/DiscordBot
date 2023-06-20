package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.ToDoHandler;

import java.text.ParseException;
import java.util.Objects;

public class TodoCommand implements SlashCommands {
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
        if (!name.equals("todo")) {
            throw new ParseException("parse error", 0);
        }

        String userid = event.getUser().getId();
        String text = Objects.requireNonNull(event.getOption("text")).getAsString();

        ToDoHandler toDoHandler = new ToDoHandler();
        toDoHandler.addTodoItem(userid, text);

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("New todo entry")
                .setDescription(text);

        event.replyEmbeds(eb.build()).setEphemeral(true).queue();

    }
}
