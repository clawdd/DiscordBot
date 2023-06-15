package main;

import commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import secret.BotToken;

public class Setup {

    private final String VERSION = "Test_0.1";
    private JDA jda;

    public Setup() {

        MainBot.INSTANCE = this;
        jda = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();

        jda.getPresence().setActivity(Activity.playing("in Version: " + VERSION));
        jda.getPresence().setStatus(OnlineStatus.IDLE);

        addListeners();
        updateSlashCommands();

        System.out.println("# # # Bot stared correctly # # #");
    }

    public void addListeners() {

        jda.addEventListener(new CommandManager());

        System.out.println("Added listeners");
    }

    public void updateSlashCommands() {

        jda.upsertCommand("bot-info", "returns the bot information").setGuildOnly(true).queue();

        System.out.println("Updated Commands");
    }

    public String getVERSION() {
        return VERSION;
    }
    public JDA getJda () {
        return jda;
    }

}
