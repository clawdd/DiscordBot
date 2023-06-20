package commands.types;

import main.MainBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import sql.ToDoHandler;

import java.awt.*;
import java.text.ParseException;
import java.util.Objects;

public class DeleteTodo implements SlashCommands {
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
        if (!name.equals("delete-todo")) {
            throw new ParseException("parse error", 0);
        }

        int idtodo = Objects.requireNonNull(event.getOption("id")).getAsInt();

        ToDoHandler toDoHandler = new ToDoHandler();
        toDoHandler.deleteTodoItem(idtodo);

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Deleted Todo")
                .addField("ID: ", String.valueOf(idtodo), false)
                .setColor(Color.RED);

        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
    }
}



