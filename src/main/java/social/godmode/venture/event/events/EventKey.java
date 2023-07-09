package social.godmode.venture.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import social.godmode.venture.event.Event;

@Getter
@AllArgsConstructor
public class EventKey extends Event {
    private final int key;
}
