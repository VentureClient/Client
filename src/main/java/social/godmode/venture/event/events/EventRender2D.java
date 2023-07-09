package social.godmode.venture.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import social.godmode.venture.event.Event;

@Getter
@AllArgsConstructor
public class EventRender2D extends Event {
    private final float partialTicks;
}
