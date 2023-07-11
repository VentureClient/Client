package social.godmode.venture.mod.mods;

import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventTick;
import social.godmode.venture.mod.ModDraggable;
import social.godmode.venture.mod.data.Category;
import social.godmode.venture.mod.data.DraggableInfo;
import social.godmode.venture.mod.data.ModInfo;
import social.godmode.venture.util.minecraft.position.ScreenPosition;

@ModInfo(name = "ToggleSprint", category = Category.MECHANICS, description = "Toggles Sprinting")
@DraggableInfo(height = 9)
public class ToggleSprint extends ModDraggable {

    private String text;

    @Override
    public void init() {
        setEnabled(true);
    }

    @Override
    public void onRender(@NonNull ScreenPosition pos) {
        FontRenderer fr = mc.fontRendererObj;

        if(text != null) {
            fr.drawStringWithShadow(text, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
            width = fr.getStringWidth(text);
        }
    }

    @EventInfo
    public void onTick(EventTick e) {
        boolean held = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());

        if(mc.thePlayer.isSneaking()) text = "[Sneaking (Vanilla)]";
        else if(held) text = "[Sprinting (Vanilla)]";
        else text = "[Sprinting (Toggled)]";

        if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking()) {
            mc.gameSettings.keyBindSprint.pressed = true;
        }
    }

}
