package social.godmode.venture.mod.mods;

import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventChatMessageReceived;
import social.godmode.venture.event.events.EventTick;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.data.ModInfo;
import social.godmode.venture.mod.setting.value.NumberValue;

import java.util.Arrays;

@ModInfo(name = "AutoGG", description = "Automatically says GG in the chat when you when a Hypixel game")
public class AutoGG extends Mod {

    NumberValue<Integer> delay = new NumberValue<>("Tick Delay", 2, 0, 10);

    int currentDelay = -1;

    String[] endGameMessages = {
            "1st Killer - ",
            "1st Place - ",
            " - Damage Dealt - ",
            "Winning Team: ",
            "1st - ",
            "Winners: ",
            "Winner: ",
            " won the game!",
            "Top Seeker: ",
            "1st Place: ",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners - ",
            "Sumo Duel - ",
    };

    @EventInfo
    public void onChat(EventChatMessageReceived event) {
        if(!isOnHypixel()) return;

        String message = event.getMessage().getUnformattedText();

        if(!message.startsWith(" ")) return;

        Arrays.stream(endGameMessages).filter(message::contains).findFirst().ifPresent(s -> currentDelay = delay.getValue().intValue());
    }

    @EventInfo
    public void onTick(EventTick e) {
        if(!isOnHypixel()) return;

        if(currentDelay > 0) {
            currentDelay--;
        }

        if(currentDelay == 0) {
            currentDelay = -1;

            mc.thePlayer.sendChatMessage("gg");
        }
    }

    public boolean isOnHypixel() {
        return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
    }

}