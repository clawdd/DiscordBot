package buttons;

import buttons.types.Buttons;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;

public class ButtonManager extends ListenerAdapter {

    Buttons buttons;

    public ButtonManager () {
        this.buttons = new Buttons();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        try {
            buttons.executeButton(event);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
