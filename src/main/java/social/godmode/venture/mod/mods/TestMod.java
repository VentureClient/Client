package social.godmode.venture.mod.mods;

import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.events.EventRender2D;
import social.godmode.venture.event.events.EventTick;
import social.godmode.venture.mod.Mod;
import social.godmode.venture.mod.data.ModInfo;
import social.godmode.venture.util.Chat;

@ModInfo(name = "TestMod", description = "For testing purposes only")
public class TestMod extends Mod {

    private int ticks;

    @EventInfo
    public void onTick(@NonNull EventTick e) {
        ticks++;
    }

    @EventInfo
    public void onRender(@NonNull EventRender2D e) {
        FontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);

//        String text = String.format("%s second%s has passed", ticks / 20, (ticks / 20) == 1 ? "" : "s");
//        fr.drawStringWithShadow(text, (sr.getScaledWidth() / 2f) - (fr.getStringWidth(text)/2f), sr.getScaledHeight() / 2f - (FontRenderer.FONT_HEIGHT/2f), 0xffffff);
    }

    @Override
    public void init() {
        setEnabled(true);
    }

}
