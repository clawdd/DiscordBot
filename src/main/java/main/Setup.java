package main;

import buttons.ButtonManager;
import commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import secret.BotStrings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static secret.BotStrings.jdbcUrl;
import static secret.BotStrings.username;

public class Setup {

    private final String VERSION = "Test_2.1";
    private Connection connection;
    private final JDA jda;

    public Setup() {

        MainBot.INSTANCE = this;
        jda = JDABuilder.create(BotStrings.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();

        jda.getPresence().setActivity(Activity.playing("in Version: " + VERSION));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

        addListeners();
        updateSlashCommands();
        establishSQLConnection();

        System.out.println("# # # Bot stared correctly # # #");
    }

    public void addListeners() {

        jda.addEventListener(new CommandManager());
        jda.addEventListener(new ButtonManager());

        System.out.println("Added listeners");
    }

    public void updateSlashCommands() {

        jda.upsertCommand("test", "testing only")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)).queue();
        jda.upsertCommand("bot-info", "all bot information you need").queue();
        jda.upsertCommand("set-bot-status", "sets bot status")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .setGuildOnly(true)
                .queue();
        jda.upsertCommand("command-info", "all command information you need").queue();
        jda.upsertCommand("set-reminder", "remind urself to do your stuff")
                .addOption(OptionType.STRING, "class", "Class name", true)
                .addOption(OptionType.STRING, "assignment_type", "Assignment type", true)
                .addOption(OptionType.INTEGER, "day", "Day", true)
                .addOption(OptionType.INTEGER, "month", "Month", true)
                .addOption(OptionType.INTEGER, "year", "Year", true)
                .addOption(OptionType.INTEGER, "hour", "Hour", true)
                .addOption(OptionType.INTEGER, "minute", "Minute", true)
                .queue();
        jda.upsertCommand("get-closest-assignment", "watch out its close!").queue();
        jda.upsertCommand("clean-up-reminder", "deletes all dates in the past")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .setGuildOnly(true)
                .queue();

        System.out.println("Updated Commands");
    }

    public void establishSQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, "");
            System.out.println("SQL connection established successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load MySQL JDBC driver.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to establish SQL connection.");
        }
    }

    public void shutDown() {
        jda.shutdown();
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

    public Connection getConnection() {
        return connection;
    }

    public void setOnlineStatus (OnlineStatus onlineStatus) {
        this.jda.getPresence().setStatus(onlineStatus);
    }

}