package CustomFunc;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.util.List;

public interface FuncInterface {

    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException;

}
