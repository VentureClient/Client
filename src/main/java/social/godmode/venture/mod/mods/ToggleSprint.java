package social.godmode.venture.mod.mods;

import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.potion.Potion;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventTick;
import social.godmode.venture.mod.ModDraggable;
import social.godmode.venture.mod.data.DraggableInfo;
import social.godmode.venture.mod.data.ModInfo;
import social.godmode.venture.util.minecraft.position.ScreenPosition;

@ModInfo(name = "ToggleSprint", description = "Toggles Sprinting")
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
        if(mc.gameSettings.keyBindSprint.isKeyDown()) text = "[Sprinting (Vanilla)]";
        else text = "[Sprinting (Toggled)]";

        if (shouldSprint()) {
            mc.thePlayer.setSprinting(true);
        }

    }

    private boolean shouldSprint() {
        boolean movingForward = mc.thePlayer.movementInput.moveForward > 0;
        boolean sprintDisabledEffects = mc.thePlayer.isPotionActive(Potion.moveSlowdown)
                || mc.thePlayer.isPotionActive(Potion.digSlowdown)
                || mc.thePlayer.isPotionActive(Potion.blindness)
                || mc.thePlayer.isSprinting() && (mc.thePlayer.isCollidedHorizontally);
        boolean isHungry = mc.thePlayer.getFoodStats().getFoodLevel() < 6.0F;

        return movingForward && !sprintDisabledEffects && !mc.thePlayer.isSneaking() && !isHungry;
    }

}
