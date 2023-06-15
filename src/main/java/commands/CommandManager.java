package commands;

import commands.types.BotInfoCommand;
import commands.types.SlashCommands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends ListenerAdapter {

    ConcurrentHashMap<String, SlashCommands> commands;

    public CommandManager() {

        this.commands = new ConcurrentHashMap<>();

        commands.put("bot-info", new BotInfoCommand());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String com = event.getName();
        System.out.println("Command to be executed: " + com);

        try {
            startCommand(com, event);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void startCommand(String com, SlashCommandInteractionEvent event) throws ParseException {
        for (Map.Entry<String, SlashCommands> entry : commands.entrySet()) {

            String commandName = entry.getKey();
            SlashCommands command = entry.getValue();

            if (commandName.equals(com)) {

                command.executeCommand(event);
                return;
            }
        }
        System.out.println("Command was not found");
    }
}
