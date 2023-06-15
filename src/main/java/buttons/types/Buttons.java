package buttons.types;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.text.ParseException;

public class Buttons implements Button{
    @Override
    public void executeButton(ButtonInteractionEvent event) throws ParseException {
        switch (event.getComponentId()) {
            default -> {
                event.reply("Button not found")
                        .setEphemeral(true)
                        .queue();
                throw new ParseException("Button not found", 0);
            }
        }
    }
}
