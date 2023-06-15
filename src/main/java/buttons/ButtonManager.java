package buttons;

import buttons.types.PressedButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;

public class ButtonManager extends ListenerAdapter {

    PressedButton buttons;

    public ButtonManager () {
        this.buttons = new PressedButton();
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
