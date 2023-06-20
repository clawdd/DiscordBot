package commands;

import commands.types.*;
import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends ListenerAdapter {

    private ConcurrentHashMap<String, SlashCommands> commands;

    public CommandManager() {

        this.commands = new ConcurrentHashMap<>();

        commands.put("bot-info", new BotInfoCommand());
        commands.put("set-bot-status", new BotStatusCommand());
        commands.put("command-info", new CommandInfoCommand());
        commands.put("set-reminder", new ReminderCommand());
        commands.put("get-closest-assignment", new ClosestAsignmentCommand());
        commands.put("clean-up-reminder", new CleanUpCommand());
        commands.put("insert-test", new InsertTestCommand());
        commands.put("tests-information", new TestsInfoCommand());
        commands.put("get-all-test-types", new AllTestTypesCommand());
        commands.put("todo", new TodoCommand());
        commands.put("get-todos", new GetTodoCommand());
        commands.put("delete-todo", new DeleteTodo());
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
            // # # # non-limited command use # ##
            for (Map.Entry<String, SlashCommands> entry : commands.entrySet()) {

                String commandName = entry.getKey();
                SlashCommands command = entry.getValue();


                if (commandName.equals(com)) {

                    command.executeCommand(event);
                    return;
                }
            }
    }
}
