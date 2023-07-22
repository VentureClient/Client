package social.godmode.venture.mod.mods;

import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventTick;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.data.Category;
import social.godmode.venture.mod.data.ModInfo;

@ModInfo(name = "1.7 Animations", category = Category.RENDER, description = "Brings back 1.7 Animations")
public class Animations extends Mod {

    @EventInfo
    public void onUpdate(EventTick e) {
        if(mc.thePlayer == null) return;

        boolean isLookingAtBlock = mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK,
                animationOver = !mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= mc.thePlayer.getArmSwingAnimationEnd() / 2 || mc.thePlayer.swingProgressInt < 0,
                rightClick = mc.gameSettings.keyBindAttack.isKeyDown() && mc.thePlayer.isUsingItem(),
                isHoldingItem = mc.thePlayer.getHeldItem() != null;

        if(isLookingAtBlock && rightClick && animationOver && isHoldingItem) {
            mc.thePlayer.swingProgressInt = -1;
            mc.thePlayer.isSwingInProgress = true;
        }
    }


}
