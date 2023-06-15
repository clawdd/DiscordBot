package main;

import buttons.ButtonManager;
import commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.requests.GatewayIntent;
import secret.BotStrings;

public class Setup {

    private final String VERSION = "Test_1.1";
    private final JDA jda;

    public Setup() {

        MainBot.INSTANCE = this;
        jda = JDABuilder.create(BotStrings.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();

        jda.getPresence().setActivity(Activity.playing("in Version: " + VERSION));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

        addListeners();
        updateSlashCommands();

        System.out.println("# # # Bot stared correctly # # #");
    }

    public void addListeners() {

        jda.addEventListener(new CommandManager());
        jda.addEventListener(new ButtonManager());

        System.out.println("Added listeners");
    }

    public void updateSlashCommands() {

        jda.upsertCommand("test", "testing only")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .setGuildOnly(true).queue();
        jda.upsertCommand("bot-info", "all bot information you need").setGuildOnly(true).queue();
        //System.out.println("Command bot-info");
        jda.upsertCommand("set-bot-status", "sets bot status")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .setGuildOnly(true)
                .queue();
        jda.upsertCommand("command-info", "all command information you need").setGuildOnly(true).queue();
        //System.out.println("Command set-bot-status");

        System.out.println("Updated Commands");
    }

    public String getVERSION() {
        return VERSION;
    }
    public JDA getJda () {
        return jda;
    }

    public OnlineStatus getBotStatus () {
        return jda.getPresence().getStatus();
    }

    public void setOnlineStatus (OnlineStatus onlineStatus) {
        this.jda.getPresence().setStatus(onlineStatus);
    }

}