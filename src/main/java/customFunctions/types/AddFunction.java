package customFunctions.types;

import customFunctions.FuncInterface;
import customFunctions.expressions.FunctionExpression;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
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

            EmbedBuilder eb = createEmbeded(function, exp);
            event.getChannel().sendMessageEmbeds(eb.build()).setSuppressedNotifications(true).queue();
            System.out.println("New function: " + exp.getFuncName() + " was added!");
        }
    }

    @NotNull
    private static EmbedBuilder createEmbeded(String function, FunctionExpression exp) {
        EmbedBuilder eb = new EmbedBuilder();

        function = function.toUpperCase();
        int defIndex = function.indexOf("DEF");

        String funcDefPart = function.substring(0, defIndex + 3).toLowerCase();
        String expPart = function.substring(defIndex + 3).toLowerCase();

        String codeBlock = "```" + "\n" + funcDefPart + "\n" + expPart + "\n" + "```";
        eb.setDescription(codeBlock);
        eb.setFooter("Defined successfully");

        return eb;
    }

    public boolean isKeyword(String token) {

        switch (token) {
            case "ADD", "SUB", "MUL", "DIV", "MOD", "FUNC", "DEF", "IF", "THEN", "ELSE", "<", ">", "=", "<=", ">=" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
