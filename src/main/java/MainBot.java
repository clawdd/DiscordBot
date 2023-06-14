import net.dv8tion.jda.api.JDABuilder;
import secret.BotToken;

import javax.security.auth.login.LoginException;

public class MainBot {

    public static void main(String[] args) throws LoginException {

        JDABuilder jdaBuilder = JDABuilder.createDefault(BotToken.token);

        jdaBuilder.build();
    }

}
