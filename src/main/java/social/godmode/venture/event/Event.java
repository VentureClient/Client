package social.godmode.venture.event;

import lombok.Getter;
import lombok.Setter;
import social.godmode.venture.event.manager.EventManager;

/**
 * The Event class
 * the base of every event
 *
 * @author Nick
 * */
@Getter @Setter
public class Event {

    /* cancelled variable **/
    public boolean cancelled;

    /**
     * Calls the event
     * */
    public void call() {
        EventManager.runEvent(this);
    }
}
