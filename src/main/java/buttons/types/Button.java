package buttons.types;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.text.ParseException;

public interface Button {

    public void executeButton(ButtonInteractionEvent event) throws ParseException;
}
