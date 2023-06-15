package buttons;

import buttons.types.StatusButton;
import main.MainBot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;

public class ButtonManager extends ListenerAdapter {

    StatusButton buttons;

    public ButtonManager () {
        this.buttons = new StatusButton();
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
