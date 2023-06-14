import com.sun.tools.javac.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import secret.BotToken;

public class Setup {

    private final String VERSION = "Test_0.1";
    private JDA jda;

    public Setup() {

        MainBot.INSTANCE = this;
        jda = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();

        jda.getPresence().setActivity(Activity.playing("in Version: " + VERSION));

        addListeners();
        updateSlashCommands();

        System.out.println("# # # Bot stared correctly # # #");
    }

    public void addListeners() {

        System.out.println("Added listeners");
    }

    public void updateSlashCommands() {

        System.out.println("Updated Commands");
    }


}
