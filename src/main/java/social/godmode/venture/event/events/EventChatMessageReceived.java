package social.godmode.venture.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.IChatComponent;
import social.godmode.venture.event.Event;

@Getter @Setter
@AllArgsConstructor
public class EventChatMessageReceived extends Event {
    private IChatComponent message;
}