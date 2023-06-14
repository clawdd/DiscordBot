
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import secret.BotToken;

import java.util.Set;


public class MainBot {

    public static Setup INSTANCE;

    public static void main(String[] args) {

        INSTANCE = new Setup();

    }
}
