package social.godmode.venture;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventKey;
import social.godmode.venture.event.events.EventRender2D;
import social.godmode.venture.event.manager.EventManager;
import social.godmode.venture.gui.ClickGui;
import social.godmode.venture.hud.HUDManager;
import social.godmode.venture.mod.manager.ModManager;
import social.godmode.venture.mod.manager.SettingManager;

@Getter
public enum Venture {
    INSTANCE;

    public static final String NAME = "Venture", VERSION = "1.0";

    private final Minecraft mc = Minecraft.getMinecraft();

    Venture() {
        ModManager.registerAllMods();
        SettingManager.registerAllSettings();

        EventManager.register(this);
    }

    @EventInfo
    public void onKey(@NonNull EventKey e) {
        int key = e.getKey();

        if(key == Keyboard.KEY_RCONTROL) HUDManager.openConfigScreen();
        else if(key == Keyboard.KEY_RSHIFT) ClickGui.display();
    }

    @EventInfo
    public void onRender(EventRender2D e) {
        if((mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) && !mc.gameSettings.showDebugInfo) {
            HUDManager.getRegisteredRenderers().forEach(HUDManager::callRenderer);
        }
    }

}
