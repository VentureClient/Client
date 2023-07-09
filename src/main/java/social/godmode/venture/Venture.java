package social.godmode.venture;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventKey;
import social.godmode.venture.event.manager.EventManager;
import social.godmode.venture.gui.DraggableGUI;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.manager.ModManager;

@Getter
public enum Venture {
    INSTANCE;

    public static final String NAME = "Venture", VERSION = "1.0";

    private final Minecraft mc = Minecraft.getMinecraft();

    Venture() {
        ModManager.registerAllMods();
        EventManager.register(this);
    }

    @EventInfo
    public void onKey(@NonNull EventKey e) {
        int key = e.getKey();

        if(key == Keyboard.KEY_RCONTROL) {
            mc.displayGuiScreen(new DraggableGUI());
        }
    }
}
