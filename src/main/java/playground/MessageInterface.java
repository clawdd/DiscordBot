package playground;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface MessageInterface {

    public void executeMessageEvent(MessageReceivedEvent event);

}
