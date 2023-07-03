package customFunctions.types;

import customFunctions.FuncInterface;
import customFunctions.expressions.IfExpression;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Condition implements FuncInterface {

    //Debugging
    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException, SQLException {

        IfExpression exp = new IfExpression(tokenList);

        if (exp.validateIfExpression()) {
            String result = exp.evaluateIfExpression();
            event.getChannel().sendMessage("Result: " + result).queue();
            System.out.println("Condition is executed");
        }
    }
}
