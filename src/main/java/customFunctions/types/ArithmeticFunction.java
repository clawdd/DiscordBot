package customFunctions.types;

import customFunctions.FuncInterface;
import customFunctions.expressions.ArithmeticExpression;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.util.List;

import static java.lang.Character.isDigit;



public class ArithmeticFunction implements FuncInterface {

    @Override
    public void executeFunction(MessageReceivedEvent event, List<String> tokenList) throws ParseException {

        ArithmeticExpression exp = new ArithmeticExpression(tokenList);

        if (exp.isValidArithmeticExpression()) {
            String result = exp.evaluateArithmeticExpression();
            event.getChannel().sendMessage("Result: " + result).queue();
        } else {
            throw new ParseException("Invalid arithmetic expression.", 0);
        }
    }
}