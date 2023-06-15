package commands;

import commands.types.BotInfoCommand;
import commands.types.BotStatusCommand;
import commands.types.SlashCommands;
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


        if (MainBot.INSTANCE.getBotStatus() != OnlineStatus.ONLINE) {
            // # # # limited command use # # #
            for (Map.Entry<String, SlashCommands> entry : commands.entrySet()) {

                String commandName = entry.getKey();
                SlashCommands command = entry.getValue();


                if (commandName.equals(com) && commandName.equals("bot-info")) {

                    command.executeCommand(event);
                    return;
                }
            }

        } else if (MainBot.INSTANCE.getBotStatus() == OnlineStatus.ONLINE) {
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

        System.out.println("Unknown bot status");
    }
}
