package customFunctions.types;

import customFunctions.FuncInterface;
import customFunctions.expressions.FunctionExpression;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import sql.FunctionHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AddFunction implements FuncInterface {
    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException, SQLException {

        if (!tokenList.get(0).equals("FUNC")) {
            throw new ParseException("Wrong token parsed inside function: " + tokenList.get(0), 0);
        }

        FunctionExpression exp = new FunctionExpression(tokenList);

        if (exp.validateFunctionExpression()) {

            String userid = event.getAuthor().getId();
            String functionName = exp.getFuncName();
            String function = event.getMessage().getContentRaw();

            FunctionHandler functionHandler = new FunctionHandler();

            functionHandler.insertFunction(userid, functionName, function);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Function: " + exp.getFuncName());
            eb.setFooter("defined successfully");
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
        }
    }
}
